package com.koko.exception;

import com.koko.enums.AppHttpCodeEnum;

/**
 * @author koko
 * @Email c_wkoko@qq.com
 * @creat 2023-03-07-19:13
 */
public class SystemException extends RuntimeException{
    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }
}
