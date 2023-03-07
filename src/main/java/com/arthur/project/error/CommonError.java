package com.arthur.project.error;

public interface CommonError {

    // 定义接口，是为了统一三个业务相关逻辑类的类型，可以让程序员方便地进行数据设置
    int getErrorCode();
    String getErrorMessage();
    CommonError setErrorMessage(String errorMessage);
}