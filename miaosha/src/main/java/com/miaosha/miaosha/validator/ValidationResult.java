package com.miaosha.miaosha.validator;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: LinXueRui
 * @Date: 2019/8/15 20:11
 * @Desc:
 */
@Data
public class ValidationResult {
    /**
     * 检验结果是否有错
     */
    private boolean hasErrors = false;

    /**
     * 存放错误信息的map
     */
    private Map<String,String> errorMsgMap = new HashMap<>();

    /**
     * 实现通用的通过格式化字符串信息获取错误结果的getMsg方法
     * @return
     */
    public String getErrorMsg(){
        StringUtils.join(errorMsgMap.values().toArray(),",");
        return StringUtils.join(errorMsgMap.values().toArray(),",");
    }
}
