package com.hjhl.customer.handler;

import com.hjhl.customer.util.ResultVOUtil;
import com.hjhl.customer.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;
import java.util.Set;

/**
 * 创建人: Hjx
 * Date: 2019/2/25
 * Description:
 */
@ControllerAdvice
@Slf4j
public class RestExceptionHandler {




    /**
     * 拦截捕捉自定义异常 ConstraintViolationException.class
     * 表单验证
     */
    @ResponseBody
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResultVO ConstraintViolationExceptionHandler(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator();
        ConstraintViolation<?> cvl = iterator.next();
        String msg = cvl.getMessageTemplate();
        return ResultVOUtil.Error(HttpStatus.BAD_REQUEST.value(), msg);

    }
}
