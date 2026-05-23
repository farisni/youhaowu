package com.wheatmall.member.mapper;

import com.wheatmall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author Nidazhong
 * @email 535704264@qq.com
 * @date 2022-12-28 23:28:17
 */
@Mapper
public interface MemberMapper extends BaseMapper<MemberEntity> {
	
}
