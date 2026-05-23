package com.wheatmall.member.controller;

import com.wheatmall.common.constant.MemberServiceUris;
import com.wheatmall.common.enums.BizCodeEnum;
import com.wheatmall.common.utils.PageData;
import com.wheatmall.common.utils.R;
import com.wheatmall.member.dto.MemberQueryDTO;
import com.wheatmall.member.exception.PhoneException;
import com.wheatmall.member.exception.UsernameException;
import com.wheatmall.member.remote.CouponRemoteService;
import com.wheatmall.member.service.MemberService;
import com.wheatmall.member.vo.MemberUserRegisterVO;
import com.wheatmall.member.vo.MemberVO;
import com.wheatmall.member.vo.SocialUser;
import com.wheatmall.common.vo.auth.UserLoginVO;
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
    public R<PageData<MemberVO>> page(MemberQueryDTO query) {
        return R.ok(memberService.page(query));
    }

    /**
     * 根据ID查询
     */
    @GetMapping(MemberServiceUris.Member.INFO)
    public R<MemberVO> info(@PathVariable Long id) {
        return R.ok(memberService.getById(id));
    }

    /**
     * 保存
     */
    @PostMapping(MemberServiceUris.Member.SAVE)
    public R<Integer> save(@RequestBody MemberVO vo) {
        return R.ok(memberService.save(vo));
    }

    /**
     * 批量保存
     */
    @PostMapping(MemberServiceUris.Member.SAVE_BATCH)
    public R<Integer> saveBatch(@RequestBody List<MemberVO> list) {
        return R.ok(memberService.saveBatch(list));
    }

    /**
     * 更新
     */
    @PutMapping(MemberServiceUris.Member.UPDATE)
    public R<Integer> update(@PathVariable Long id, @RequestBody MemberVO vo) {
        return R.ok(memberService.updateById(id, vo));
    }

    /**
     * 删除
     */
    @DeleteMapping(MemberServiceUris.Member.DELETE)
    public R<Integer> delete(@PathVariable Long id) {
        return R.ok(memberService.removeById(id));
    }

    /**
     * 批量删除
     */
    @DeleteMapping(MemberServiceUris.Member.DELETE_BATCH)
    public R<Integer> deleteBatch(@RequestBody List<Long> ids) {
        return R.ok(memberService.removeByIds(ids));
    }

    //  ==================== 会员业务 ====================

    /**
     * 注册
     */
    @PostMapping(MemberServiceUris.Member.REGISTER)
    public R<String> register(@RequestBody MemberUserRegisterVO user) {
        try {
            memberService.register(user);
            return R.ok();
        } catch (PhoneException ex) {
            return R.fail(BizCodeEnum.PHONE_EXIST_EXCEPTION.getCode(),
                    BizCodeEnum.PHONE_EXIST_EXCEPTION.getMessage());
        } catch (UsernameException ex) {
            return R.fail(BizCodeEnum.USER_EXIST_EXCEPTION.getCode(),
                    BizCodeEnum.USER_EXIST_EXCEPTION.getMessage());
        } catch (Exception ex) {
            return R.fail(ex.getMessage());
        }
    }

    /**
     * 登录
     */
    @PostMapping(MemberServiceUris.Member.LOGIN)
    public R<MemberVO> login(@RequestBody UserLoginVO userLoginVO) {
        MemberVO vo = memberService.login(userLoginVO);
        if (vo != null) {
            return R.ok(vo);
        } else {
            return R.fail(BizCodeEnum.LOGINACCT_PASSWORD_EXCEPTION.getCode(),
                    BizCodeEnum.LOGINACCT_PASSWORD_EXCEPTION.getMessage());
        }
    }

    /**
     * Gitee OAuth 登录
     */
    @PostMapping(MemberServiceUris.Member.OAUTH_LOGIN)
    public R<MemberVO> oauthLogin(@RequestBody SocialUser socialUser) throws Exception {
        MemberVO vo = memberService.login(socialUser);
        if (vo != null) {
            return R.ok(vo);
        } else {
            return R.fail(BizCodeEnum.LOGINACCT_PASSWORD_EXCEPTION.getCode(),
                    BizCodeEnum.LOGINACCT_PASSWORD_EXCEPTION.getMessage());
        }
    }

    /**
     * Feign 联调测试（Coupon 远程调用）
     */
    @GetMapping(MemberServiceUris.Member.COUPONS)
    public R<?> testCoupon() {
        R coupons = couponRemoteService.memberCoupons();
        return R.ok(coupons);
    }
}
