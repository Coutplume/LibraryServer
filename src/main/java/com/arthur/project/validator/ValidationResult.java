package com.arthur.project.validator;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;


/**
 * 该类用于程序和错误校验类之间的对接，将错误返回出去
 * @Arthur coutplume
 */
public class ValidationResult {
    //检查是否有错误
    private boolean hasError;
    //存放错误信息
    private Map<String, String> errorMsgMap;

    public ValidationResult(){
        this.hasError = false;
        this.errorMsgMap = new HashMap<>();
    }

    //实现通用的格式化字符串信息，获取错误的方法
    public String  getErrorMsg(){
        return StringUtils.join(errorMsgMap.values().toArray(),",");
    }


    /**
     * getter and setter
     * @return
     */

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public Map<String, String> getErrorMsgMap() {
        return errorMsgMap;
    }

    public void setErrorMsgMap(Map<String, String> errorMsgMap) {
        this.errorMsgMap = errorMsgMap;
    }
}
