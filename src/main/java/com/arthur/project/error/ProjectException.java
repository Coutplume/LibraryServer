package com.arthur.project.error;


/**
 * 包装器业务异常类实现
 */

public class ProjectException extends Exception implements CommonError{
    private  CommonError commonError;

    //直接接收EnumError类的数据，用于构造业务异常
    public ProjectException(CommonError commonError){
        super();
        this.commonError = commonError;
    }
    //自定也业务异常信息
    public ProjectException(CommonError commonError, String errMsg){
        super();
        this.commonError = commonError;
        this.commonError.setErrorMessage(errMsg);
    }

    @Override
    public  String getMessage(){
        return this.commonError.getMessage();
    }

    @Override
    public int getErrorCode() {
        return commonError.getErrorCode();
    }

    @Override
    public CommonError setErrorMessage(String errMsg) {
        this.setErrorMessage(errMsg);
        return this;
    }
}
