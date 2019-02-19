package com.lingb.couponplus.passbook.advice;

import com.lingb.couponplus.passbook.vo.ErrorVO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 *
 * @author lingb
 * @date 2018.11.19 10:14
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ErrorVO errorHandler(HttpServletRequest request, Exception e) throws Exception {

        ErrorVO error = new ErrorVO();

        error.setCode(ErrorVO.ERROR);
        error.setMsg(e.getMessage());
        error.setData("沒有响应的数据。");
        error.setUrl(request.getRequestURL().toString());

        return error;
    }
}
