package com.cvte.waimai.utils;

import com.cvte.waimai.exception.AppException;
import com.cvte.waimai.exception.AppExceptionCodeMsg;

import java.io.Serializable;

/**
 * 通用的返回类
 */
public class MsgUtils<T> implements Serializable{
    private int code;

    private String msg;

    private T data;

    public static <T> MsgUtils build(int code , String msg , T data){
        return new MsgUtils(code,msg,data);
    }

    public static MsgUtils build(int code , String msg) {
        return new MsgUtils(code,msg);
    }

    public static MsgUtils success(){
        MsgUtils result = build(200, "success");
        return result;
    }

    public static <T> MsgUtils success(T data) {
        MsgUtils result = build(200, "success", data);
        return result;
    }

    public static MsgUtils fail(){
        MsgUtils result = build(500, "fails");
        return result;
    }

    public static MsgUtils fail(AppExceptionCodeMsg appExceptionCodeMsg) {
        MsgUtils result = build(appExceptionCodeMsg.getCode(), appExceptionCodeMsg.getMsg());
        return result;
    }

    public static MsgUtils fail(int code, String msg){
        MsgUtils result = build(code, msg);
        return result;
    }

    public static <T> MsgUtils fail(int code, String msg, T data) {
        MsgUtils result = build(code, msg, data);
        return result;
    }

    public MsgUtils() {
    }

    public MsgUtils(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public MsgUtils(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

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

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
