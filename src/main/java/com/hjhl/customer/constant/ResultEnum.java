package com.hjhl.customer.constant;

import lombok.Getter;

/**
 * 创建人: Hjx
 * Date: 2019/2/25
 * Description:
 */
@Getter
public enum ResultEnum {

    HANDLE_SUCCESS(200, "操作成功"),
    HANDLE_ERROR(400, "操作失败"),
    SYS_ERROR(500, "系统错误"),

    LUCK_DRAW_COUNT_ERROR(400, "已达到每日活动抽奖次数上限~谢谢参与"),
    MEMBER_EMPTY(404, "会员不存在"),
    LUCK_ORDER_EMPTY(404, "订单不存在"),
    LUCK_PRIZE_EMPTY(404, "奖品未设置"),
    LUCK_DRAW_EXPIRE_EMPTY(404, "本次活动已结束，请下次积极参与~"),
    ANS_SCORE_ERROR(404, "未设置抽奖参与分数");


    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
