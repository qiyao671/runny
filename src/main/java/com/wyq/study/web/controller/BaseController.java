/**
 * Olymtech.com Inc.
 * Copyright (c) 2002-2015 All Rights Reserved.
 */
package com.wyq.study.web.controller;


import com.wyq.study.pojo.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gavin on 2015-09-14 15:50.
 */
public class BaseController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected Callback callback = new Callback();
    protected Map<String, Object> callbackMap = new HashMap<String, Object>();
    protected String callBackMsg;
    protected Boolean result;
    protected Object responseContext;
    @Resource
    protected HttpServletRequest request;
    protected HttpServletResponse response;


    public Callback returnCallback(Boolean result, Object responseContext, String msg) {
        callback.setResult(result);
        callback.setResponseContext(responseContext);
        callback.setMsg(msg);
        return callback;
    }

//    public UserInfo getLoginUser(){
//        UserInfo userInfo = (UserInfo) getRequest().getAttribute(Constants.CURRENT_USER);
//        return userInfo;
//    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public Callback getCallback() {
        return callback;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public String getCallBackMsg() {
        return callBackMsg;
    }

    public void setCallBackMsg(String callBackMsg) {
        this.callBackMsg = callBackMsg;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public Object getResponseContext() {
        return responseContext;
    }

    public void setResponseContext(Object responseContext) {
        this.responseContext = responseContext;
    }

    public Map<String, Object> getCallbackMap() {
        return callbackMap;
    }

    public void setCallbackMap(Map<String, Object> callbackMap) {
        this.callbackMap = callbackMap;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
//        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"), true));
//        webDataBinder.registerCustomEditor(int.class, new IntegerEditor());
//        webDataBinder.registerCustomEditor(long.class, new LongEditor());
//        webDataBinder.registerCustomEditor(double.class, new DoubleEditor());
//        webDataBinder.registerCustomEditor(float.class, new FloatEditor());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}
