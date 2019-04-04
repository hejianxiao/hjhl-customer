package com.hjhl.customer.modal.luck;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.hjhl.customer.modal.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 创建人: Hjx
 * Date: 2019/3/21
 * Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LuckOrder extends BaseEntity<LuckOrder> {

    /** 主键. */
    @TableId(type = IdType.UUID)
    private String id;

    /** 活动id. */
    private String luckDrawId;
    
    /** 用户id. */
    private String memberId;
    
    /** 奖品id. */
    private String luckPrizeId;
    
    /** 创建时间. */
    private Date createTime;
    
    /** 是否奖品. */
    private String orPrize;
    
    

}
