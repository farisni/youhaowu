package com.youhaowu.member.service;

import com.youhaowu.common.utils.PageData;
import com.youhaowu.member.dto.MemberQueryDTO;
import com.youhaowu.member.exception.PhoneException;
import com.youhaowu.member.exception.UsernameException;
import com.youhaowu.member.vo.MemberUserRegisterVO;
import com.youhaowu.member.vo.MemberVO;
import com.youhaowu.member.vo.SocialUser;
import com.youhaowu.common.vo.auth.UserLoginVO;

import java.util.List;

/**
 * 会员服务
 */
public interface MemberService {

    //  标准 CRUD
    PageData<MemberVO> page(MemberQueryDTO query);
    MemberVO getById(Long id);
    Integer save(MemberVO vo);
    Integer saveBatch(List<MemberVO> list);
    Integer updateById(Long id, MemberVO vo);
    Integer removeById(Long id);
    Integer removeByIds(List<Long> ids);

    //  会员业务
    Integer register(MemberUserRegisterVO user);
    void checkPhoneUnique(String phone) throws PhoneException;
    void checkUserNameUnique(String userName) throws UsernameException;
    MemberVO login(UserLoginVO user);
    MemberVO login(SocialUser socialUser) throws Exception;
}
