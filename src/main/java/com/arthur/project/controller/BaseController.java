package com.arthur.project.controller;

import com.arthur.project.error.EnumBaseError;
import com.arthur.project.error.ProjectException;
import com.arthur.project.response.CommonReturnType;
import com.arthur.project.validator.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.validation.Validator;
import java.util.HashMap;
import java.util.Map;

public class BaseController {

    @Autowired
    public HttpServletRequest httpServletRequest;

    @Autowired
    public HttpServletResponse httpServletResponse;

    @Autowired
    public ValidatorImpl validator;


    public static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handlerException(HttpServletRequest request, Exception e){
        Map<String, Object> responseData = new HashMap<>();
        if (e instanceof ProjectException){
            ProjectException projectException = (ProjectException)e;
            responseData.put("errCode", projectException.getErrorCode());
            responseData.put("errMsg", projectException.getMessage());
        }else {
            responseData.put("errCode", EnumBaseError.UNKNOWN_ERROR.getErrorCode());
            responseData.put("errMsg", EnumBaseError.UNKNOWN_ERROR.getMessage());
        }
        return CommonReturnType.create(responseData, "fail");
    }

    /**
     * 对session进行设置，使它能在跨域的情况下实现共享
     */
    @Transactional
    public void setSession(){
        ResponseCookie responseCookie = ResponseCookie.from("JSESSIONID",httpServletRequest.getSession().getId())
                .httpOnly(true)
                .secure(true)
                .domain("localhost")
                .path("/")
                //.maxAge(3600)
                .sameSite("None")
                .build()
                ;
        httpServletResponse.setHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
    }

}
