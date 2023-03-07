package com.arthur.project.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;


@Component
public class ValidatorImpl implements InitializingBean {

    private Validator validator;

    //实验校验方法并返回校验结果
    public ValidationResult validate(Object bean){
        ValidationResult validationResult = new ValidationResult();
        Set<ConstraintViolation<Object>> constraintViolationSet =  validator.validate(bean);

        if (!constraintViolationSet.isEmpty()){
            //有错误
            validationResult.setHasError(true);
            constraintViolationSet.forEach(constraintViolation->{
                String errorMsg = constraintViolation.getMessage();
                String propertyName = constraintViolation.getPropertyPath().toString();
                validationResult.getErrorMsgMap().put(propertyName, errorMsg);
            });
        }

        return validationResult;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //通过工厂的初始化方式将其实例化
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
}
