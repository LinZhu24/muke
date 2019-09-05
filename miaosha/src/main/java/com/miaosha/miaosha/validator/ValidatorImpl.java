package com.miaosha.miaosha.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @Author: LinXueRui
 * @Date: 2019/8/15 20:20
 * @Desc:
 */
@Component
public class ValidatorImpl implements InitializingBean {
    private Validator validator;

    /**
     * 实现校验方法，并返回校验结果
     * @param bean
     * @return
     */
    public ValidationResult validate(Object bean){
        ValidationResult result = new ValidationResult();
        Set<ConstraintViolation<Object>> violationSet = validator.validate(bean);
        if (violationSet.size() > 0){
            result.setHasErrors(true);
            violationSet.forEach(violation->{
                String errorMsg = violation.getMessage();
                String propertyName = violation.getPropertyPath().toString();
                result.getErrorMsgMap().put(propertyName,errorMsg);
            });
        }
        return result;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //将hibernate  validator通过工厂的初始化方式进行实例化
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
}
