package com.hjhl.customer.modal.luck;

import com.hjhl.customer.modal.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 创建人: Hjx
 * Date: 2019/3/19
 * Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LuckPrize extends BaseEntity<LuckPrize> {
    /** 抽奖活动id. */
    private String luckDrawId;

    /** 奖品名称. */
    private String prizeName;

    /** 奖品类型. */
    private String prizeType;

    /** 奖品数量. */
    private Integer prizeAmount;

    /** 奖品权重. */
    private Double prizeWeight;

    /** 奖品图片链接. */
    private String prizePic;

}
