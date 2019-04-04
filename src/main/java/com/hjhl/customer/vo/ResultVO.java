package com.hjhl.customer.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * 创建人: Hjx
 * Date: 2018/5/9
 * Description:
 * 返回封装.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultVO<T> implements Serializable {

    /** 状态码. */
    private Integer code;

    /** 提示信息. */
    private String msg;

    /** 结果集. */
    private T data;

}
