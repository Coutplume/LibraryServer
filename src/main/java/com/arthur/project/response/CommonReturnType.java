package com.arthur.project.response;

public class CommonReturnType {
    //status表明对应请求的返回处理结果
    //data表明返回的数据
    private  String status;
    private  Object data;
    //若status为success，则data返回前端需要的JSON数据
    //若status为fail，则data返回通用的错误码

    //定义一个通用的创建方法
    public static CommonReturnType create(Object result){
        //如果不带参数，默认为成功
        return CommonReturnType.create(result, "success");
    }
    public static CommonReturnType create(Object result, String status){
        CommonReturnType type = new CommonReturnType();
        type.setStatus(status);
        type.setData(result);
        return type;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
