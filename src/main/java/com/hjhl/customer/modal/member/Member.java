package com.hjhl.customer.modal.member;

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
public class Member extends BaseEntity<Member> {

    /** openid. */
    private String openid;

    /** 创建时间. */
    private Date createTime;

    public Member() {
    }

    public Member(String openid) {
        this.openid = openid;
    }

    public Member(String openid, Date createTime) {
        this.openid = openid;
        this.createTime = createTime;
    }
}
