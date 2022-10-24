package com.cvte.waimai.exception;

import com.cvte.waimai.utils.MsgUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public <T> MsgUtils<T> exceptionHandler(Exception e) {
        e.printStackTrace();
        if (e instanceof AppException) {
            AppException appException = (AppException) e;
            return MsgUtils.fail(appException.getCode(), appException.getMsg());
        } else if (e instanceof SocketException) {
            return MsgUtils.fail(AppExceptionCodeMsg.NETWORK_BUSY);
        }
        return MsgUtils.fail(500, "服务器异常", e.getMessage());
    }

}
