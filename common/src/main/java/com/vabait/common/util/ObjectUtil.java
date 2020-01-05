package com.vabait.common.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author susean
 * @createTime 2020/1/4 下午6:07
 * @description ObjectUtil,i am not know how to use it
 */
public class ObjectUtil {
    public static void copyPropertiesNotNull(Object src, Object target) {
        BeanUtil.copyProperties(src, target, CopyOptions.create().setIgnoreNullValue(true));
    }

    public static <T> T convertValue(Object fromValue, Class<T> toValueType) {
        ObjectMapper mapper = new ObjectMapper();
        T toValue = mapper.convertValue(fromValue, toValueType);
        return toValue;
    }
}
