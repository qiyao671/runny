/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.wyq.study.pojo;

/**
 * Created by Gavin on 2015-09-14 15:56.
 */
public class Callback {
    /* 请求返回 状态的信息 */
    private Boolean result;
    /* 请求返回 具体内容*/
    private Object responseContext;
    /*返回请求的msg*/
    private String msg;

    /**
     * default
     */
    private int code = 200;

    public Callback() {
        super();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Callback(Boolean result, Object responseContext, String msg) {
        super();
        this.result = result;
        this.responseContext = responseContext;
        this.msg = msg;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public void setCallbackMsg(Boolean result) {
        this.result = result;
    }

    public Object getResponseContext() {
        return responseContext;
    }

    public void setResponseContext(Object responseContext) {
        this.responseContext = responseContext;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
