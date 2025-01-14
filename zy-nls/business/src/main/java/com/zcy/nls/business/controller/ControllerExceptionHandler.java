package com.zcy.nls.business.controller;

import com.zcy.nls.business.exception.BusinessException;
import com.zcy.nls.business.resp.CommonResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理，数据预处理等
 */
@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    /**
     * 统一异常处理
     *
     * @param e
     * @return
     * @throws
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public CommonResp<Object> exceptionHandler(Exception e) {
        CommonResp<Object> objectCommonResp = new CommonResp<>();
        log.error("系统异常", e);
        objectCommonResp.setSuccess(false);
        objectCommonResp.setMessage("系统出现异常，请联系管理员");
        return objectCommonResp;
    }

    /**
     * 统一业务异常处理
     *
     * @param e
     * @return
     * @throws
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public CommonResp<Object> businessExceptionHandler(BusinessException e) {
        CommonResp<Object> objectCommonResp = new CommonResp<>();
        log.error("系统异常", e);
        objectCommonResp.setSuccess(false);
        objectCommonResp.setMessage(e.getE().getDesc());
        return objectCommonResp;
    }

    /**
     * 校验异常统一处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public CommonResp<Object> validExceptionHandler(BindException e) {
        CommonResp<Object> commonResp = new CommonResp<>();
        log.warn("参数校验失败：{}", e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        commonResp.setSuccess(false);
        commonResp.setMessage(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return commonResp;
    }
}