package com.youhaowu.member.service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.common.utils.HttpUtils;
import com.youhaowu.member.entity.MemberEntity;
import com.youhaowu.member.entity.MemberLevelEntity;
import com.youhaowu.member.mapper.MemberMapper;
import com.youhaowu.member.mapper.MemberLevelMapper;
import com.youhaowu.member.dto.MemberQueryDTO;
import com.youhaowu.member.exception.PhoneException;
import com.youhaowu.member.exception.UsernameException;
import com.youhaowu.member.service.MemberService;
import com.youhaowu.member.vo.GiteeUser;
import com.youhaowu.member.vo.MemberUserRegisterVO;
import com.youhaowu.member.vo.MemberVO;
import com.youhaowu.member.vo.SocialUser;
import com.youhaowu.common.utils.PageUtils;
import com.youhaowu.common.vo.auth.UserLoginVO;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional(rollbackFor = Exception.class)
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private MemberLevelMapper memberLevelMapper;

    //  ==================== 标准 CRUD ====================

    @Override
    public PageData<MemberVO> page(MemberQueryDTO query) {
        LambdaQueryWrapper<MemberEntity> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getKeyword())) {
            wrapper.like(MemberEntity::getUsername, query.getKeyword())
                   .or().like(MemberEntity::getNickname, query.getKeyword());
        }
        return PageUtils.selectPage(memberMapper, wrapper, query, this::entityToVO);
    }

    @Override
    public MemberVO getById(Long id) {
        MemberEntity e = memberMapper.selectById(id);
        return entityToVO(e);
    }

    @Override
    public Integer save(MemberVO vo) {
        MemberEntity e = new MemberEntity();
        BeanUtil.copyProperties(vo, e);
        return memberMapper.insert(e);
    }

    @Override
    public Integer saveBatch(List<MemberVO> list) {
        List<MemberEntity> entities = list.stream().map(vo -> {
            MemberEntity e = new MemberEntity();
            BeanUtil.copyProperties(vo, e);
            return e;
        }).collect(Collectors.toList());
        return memberMapper.insert(entities).size();
    }

    @Override
    public Integer updateById(Long id, MemberVO vo) {
        MemberEntity e = new MemberEntity();
        BeanUtil.copyProperties(vo, e);
        e.setId(id);
        return memberMapper.updateById(e);
    }

    @Override
    public Integer removeById(Long id) {
        return memberMapper.deleteById(id);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return memberMapper.deleteBatchIds(ids);
    }

    //  ==================== 会员业务 ====================

    @Override
    public Integer register(MemberUserRegisterVO user) {
        //  1. 校验手机号唯一、用户名唯一
        checkPhoneUnique(user.getPhone());
        checkUserNameUnique(user.getUserName());

        //  2. 封装保存
        MemberEntity entity = new MemberEntity();
        entity.setUsername(user.getUserName());
        entity.setMobile(user.getPhone());
        entity.setNickname(user.getUserName());

        //  3. 默认等级
        LambdaQueryWrapper<MemberLevelEntity> lvWrp = new LambdaQueryWrapper<>();
        lvWrp.orderByAsc(MemberLevelEntity::getDefaultStatus);
        lvWrp.last("LIMIT 1");
        MemberLevelEntity level = memberLevelMapper.selectOne(lvWrp);
        if (level != null) {
            entity.setLevelId(level.getId());
        }

        //  4. 密码加密存储
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        entity.setPassword(passwordEncoder.encode(user.getPassword()));
        entity.setCreateTime(new Date());

        return memberMapper.insert(entity);
    }

    @Override
    public void checkPhoneUnique(String phone) throws PhoneException {
        Long count = memberMapper.selectCount(
                new LambdaQueryWrapper<MemberEntity>().eq(MemberEntity::getMobile, phone));
        if (count > 0) {
            throw new PhoneException();
        }
    }

    @Override
    public void checkUserNameUnique(String userName) throws UsernameException {
        Long count = memberMapper.selectCount(
                new LambdaQueryWrapper<MemberEntity>().eq(MemberEntity::getUsername, userName));
        if (count > 0) {
            throw new UsernameException();
        }
    }

    @Override
    public MemberVO login(UserLoginVO user) {
        String loginacct = user.getLoginacct();
        String password = user.getPassword();

        MemberEntity entity = memberMapper.selectOne(
                new LambdaQueryWrapper<MemberEntity>()
                        .eq(MemberEntity::getUsername, loginacct)
                        .or().eq(MemberEntity::getMobile, loginacct));

        if (entity == null) {
            return null;
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean matches = passwordEncoder.matches(password, entity.getPassword());
        return matches ? entityToVO(entity) : null;
    }

    @Override
    public MemberVO login(SocialUser socialUser) throws Exception {
        //  获取 Gitee 用户信息
        Map<String, String> query = new HashMap<>();
        query.put("access_token", socialUser.getAccess_token());

        GiteeUser giteeUser = null;
        ResponseEntity<String> response = HttpUtils.doGet("https://gitee.com", "/api/v5/user",
                "get", new HashMap<>(), query);
        if (response.getStatusCode().is2xxSuccessful()) {
            giteeUser = JSON.parseObject(response.getBody(), GiteeUser.class);
        }

        Assert.isTrue(giteeUser != null && giteeUser.getId() != null, "Gitee获取用户信息失败");

        String id = giteeUser.getId();

        //  判断是否已注册
        MemberEntity memberEntity = memberMapper.selectOne(
                new LambdaQueryWrapper<MemberEntity>().eq(MemberEntity::getSocialUid, id));

        if (memberEntity != null) {
            //  已注册：更新 access_token
            MemberEntity update = new MemberEntity();
            update.setId(memberEntity.getId());
            update.setAccessToken(socialUser.getAccess_token());
            update.setExpiresIn(socialUser.getExpires_in());
            memberMapper.updateById(update);

            memberEntity.setAccessToken(socialUser.getAccess_token());
            memberEntity.setExpiresIn(socialUser.getExpires_in());
            return entityToVO(memberEntity);
        } else {
            //  新用户：注册
            MemberEntity register = new MemberEntity();
            register.setNickname(giteeUser.getName());
            register.setHeader(giteeUser.getAvatarUrl());
            register.setCreateTime(new Date());
            register.setSocialUid(giteeUser.getId());
            register.setAccessToken(socialUser.getAccess_token());
            register.setExpiresIn(socialUser.getExpires_in());
            memberMapper.insert(register);
            return entityToVO(register);
        }
    }

    private MemberVO entityToVO(MemberEntity e) {
        MemberVO v = new MemberVO();
        BeanUtil.copyProperties(e, v);
        return v;
    }
}
