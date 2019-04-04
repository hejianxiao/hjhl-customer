package com.hjhl.customer.modal.luck;

import com.hjhl.customer.modal.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 创建人: Hjx
 * Date: 2019/3/19
 * Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LuckDraw extends BaseEntity<LuckDraw> {


    /** 活动代码. */
    private String actCode;

    /** 活动名称. */
    private String actName;

    /** 抽奖次数 次/天. */
    private Integer drawCount;

    /** 每人最多抽中次数. */
    private Integer maxCount;

    /** 每人每天最多抽中. */
    private Integer dayMaxCount;

    /** 活动开始时间. */
    private String startTime;

    /** 活动结束时间. */
    private String endTime;

    /** 创建时间. */
    private Date createTime;

    /** 领奖信息. */
    private String receiveInfo;

    public LuckDraw() {
    }

    public LuckDraw(String actCode) {
        this.actCode = actCode;
    }
}
