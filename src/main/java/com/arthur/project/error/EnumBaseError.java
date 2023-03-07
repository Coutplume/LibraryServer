package com.arthur.project.error;

public enum EnumBaseError implements CommonError{
    //通用错误类型10000
    PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),
    UNKNOWN_ERROR(10002,"未知错误"),

    //20000为用户相关错误定义
    INSERT_FAIL(20001, "保存数据失败！"),
    PRIVILEGE_ERROR(20002,"用户权限不详"),
    DUPLICATE_ERROR(20003,"用户学号或手机号已被注册"),
    OTP_ERROR(20004,"验证码错误"),

    // 30000为操作相关错误
    BOOK_NUM_MAX(30001,"用户借书数目已达最大值"),
    BOOK_HAVE_LEND(30002, "书籍已被借出"),
    BOOK_HAVENT_LEND(30003, "书籍未被借出"),
    BOOK_NOT_MATCH_USER(30004,"用户和书不匹配"),
    BOOK_HAVE_LEND_BY_USER(30005,"用户已将此书借出"),
    BOOK_OR_USER_NOT_IN_STORE(30006,"用户或书籍不存在"),
    BOOK_ID_HAVE_USED(30007,"书号已被使用")
    ;

    private EnumBaseError(int errCode, String errMsg){
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    private int errCode;
    private String errMsg;
    @Override
    public int getErrorCode() {
        return this.errCode;
    }

    @Override
    public String getMessage() {
        return this.errMsg;
    }


    //该方法是为了通用错误码下自定义错误信息，减少代码冗余和工作量
    @Override
    public CommonError setErrorMessage(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
