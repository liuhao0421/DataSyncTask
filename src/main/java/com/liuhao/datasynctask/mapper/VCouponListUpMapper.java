package com.liuhao.datasynctask.mapper;

import com.liuhao.datasynctask.entity.MemberCardEntity;
import com.liuhao.datasynctask.entity.VCouponListUpEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liuhao
 * @since 2023-11-05
 */
@Mapper
public interface VCouponListUpMapper extends BaseMapper<VCouponListUpEntity> {
    @Select("select TOP (1000) * from v_Coupon_List_Up where (sync_flag is null OR sync_flag = 0) and (sync_time is null or sync_time < updatedate)")
    List<VCouponListUpEntity> getData();

}
