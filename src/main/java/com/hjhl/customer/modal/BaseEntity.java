package com.hjhl.customer.modal;

import com.baomidou.mybatisplus.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 创建人: Hjx
 * Date: 2018/6/13
 * Description:
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseEntity<T extends Model> extends Model<T>{

    /** 主键. */
    private String id;

    @Override
    protected Serializable pkVal() {
        return null;
    }
}
