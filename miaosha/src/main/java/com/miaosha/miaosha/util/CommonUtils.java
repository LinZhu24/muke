package com.miaosha.miaosha.util;

import com.miaosha.miaosha.error.BusinessErrorEnum;
import com.miaosha.miaosha.error.BusinessException;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: LinXueRui
 * @Date: 2019/8/6 23:42
 * @Desc: 常用的工具包
 */
public class CommonUtils {

    /**
     * List中去掉某些元素
     *
     * @param originList 原List
     * @param remove     需要被去掉的List
     * @param <T>
     * @return
     * @throws BusinessException
     */
    public static <T> List<T> removeList(List<T> originList, List<T> remove) throws BusinessException {
        if (CollectionUtils.isEmpty(originList)) {
            throw new BusinessException(BusinessErrorEnum.UNKNOWN_ERROR, "集合为空");
        }
        if (CollectionUtils.isEmpty(remove)) {
            return originList;
        }
        List<T> list = new ArrayList<>();
        for (T obj : originList) {
            if (!remove.contains(obj)) {
                list.add(obj);
            }
        }
        return list;
    }

    /**
     * 比较数组内的元素是否完全一致
     *
     * @param array
     * @return
     */
    public static <T> boolean isSame(T[] array) {
        T t;
        for (int i = 0; i < array.length; i++) {
            t = array[0];
            if (!array[i].equals(t)) {
                return false;
            }
        }
        return true;
    }

}
