package com.cvte.waimai.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

public enum AppExceptionCodeMsg {

    ILLEGAL_ORDER(10000, "不合法订单"),
    ORDER_NOT_FOUND(10001, "找不到订单"),
    ILLEGAL_DISH(10002, "不合法菜品"),
    DISH_NOT_FOUND(10003, "找不到菜品"),

    ;

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    AppExceptionCodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
