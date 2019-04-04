package com.hjhl.customer.modal.luck;

import com.hjhl.customer.modal.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 创建人: Hjx
 * Date: 2019/3/21
 * Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LuckReceive extends BaseEntity<LuckReceive> {

    /** 抽奖订单id. */
    private String luckOrderId;
    
    /** 领奖信息名称. */
    private String receiveName;
    
    /** 领奖信息详情. */
    private String receiveValue;
    
}
