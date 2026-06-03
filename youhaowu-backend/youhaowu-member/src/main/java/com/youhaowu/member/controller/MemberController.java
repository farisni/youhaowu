package com.youhaowu.member.controller;

import com.youhaowu.common.constant.MemberServiceUris;
import com.youhaowu.common.enums.BizCodeEnum;
import com.youhaowu.common.utils.PageData;
import com.youhaowu.member.dto.MemberQueryDTO;
import com.youhaowu.member.exception.PhoneException;
import com.youhaowu.member.exception.UsernameException;
import com.youhaowu.member.remote.CouponRemoteService;
import com.youhaowu.member.service.MemberService;
import com.youhaowu.member.vo.MemberUserRegisterVO;
import com.youhaowu.member.vo.MemberVO;
import com.youhaowu.member.vo.SocialUser;
import com.youhaowu.common.vo.auth.UserLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 会员控制器
 */
@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private CouponRemoteService couponRemoteService;

    /**
     * 分页查询
     */
    @GetMapping(MemberServiceUris.Member.PAGE)
    public PageData<MemberVO> page(MemberQueryDTO query) {
        return memberService.page(query);
    }

    /**
     * 根据ID查询
     */
    @GetMapping(MemberServiceUris.Member.INFO)
    public MemberVO info(@PathVariable Long id) {
        return memberService.getById(id);
    }

    /**
     * 保存
     */
    @PostMapping(MemberServiceUris.Member.SAVE)
    public Integer save(@RequestBody MemberVO vo) {
        return memberService.save(vo);
    }

    /**
     * 批量保存
     */
    @PostMapping(MemberServiceUris.Member.SAVE_BATCH)
    public Integer saveBatch(@RequestBody List<MemberVO> list) {
        return memberService.saveBatch(list);
    }

    /**
     * 更新
     */
    @PutMapping(MemberServiceUris.Member.UPDATE)
    public Integer update(@PathVariable Long id, @RequestBody MemberVO vo) {
        return memberService.updateById(id, vo);
    }

    /**
     * 删除
     */
    @DeleteMapping(MemberServiceUris.Member.DELETE)
    public Integer delete(@PathVariable Long id) {
        return memberService.removeById(id);
    }

    /**
     * 批量删除
     */
    @DeleteMapping(MemberServiceUris.Member.DELETE_BATCH)
    public Integer deleteBatch(@RequestBody List<Long> ids) {
        return memberService.removeByIds(ids);
    }

    //  ==================== 会员业务 ====================

    /**
     * 注册
     */
    @PostMapping(MemberServiceUris.Member.REGISTER)
    public Integer register(@RequestBody MemberUserRegisterVO user) {
        try {
            return memberService.register(user);

            
        } catch (PhoneException ex) {
            throw new RuntimeException(BizCodeEnum.PHONE_EXIST_EXCEPTION.getMessage());
        } catch (UsernameException ex) {
            throw new RuntimeException(BizCodeEnum.USER_EXIST_EXCEPTION.getMessage());
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * 登录
     */
    @PostMapping(MemberServiceUris.Member.LOGIN)
    public MemberVO login(@RequestBody UserLoginVO userLoginVO) {
        MemberVO vo = memberService.login(userLoginVO);
        if (vo != null) {
            return vo;
        } else {
            throw new RuntimeException(BizCodeEnum.LOGINACCT_PASSWORD_EXCEPTION.getMessage());
        }
    }

    /**
     * Gitee OAuth 登录
     */
    @PostMapping(MemberServiceUris.Member.OAUTH_LOGIN)
    public MemberVO oauthLogin(@RequestBody SocialUser socialUser) throws Exception {
        MemberVO vo = memberService.login(socialUser);
        if (vo != null) {
            return vo;
        } else {
            throw new RuntimeException(BizCodeEnum.LOGINACCT_PASSWORD_EXCEPTION.getMessage());
        }
    }

    /**
     * Feign 联调测试（Coupon 远程调用）
     */
    @GetMapping(MemberServiceUris.Member.COUPONS)
    public Object testCoupon() {
        return couponRemoteService.memberCoupons();
    }
}
