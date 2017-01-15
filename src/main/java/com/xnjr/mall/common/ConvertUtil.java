package com.xnjr.mall.common;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConvertUtil {
    private static final Logger logger = LoggerFactory
        .getLogger(ConvertUtil.class);

    /**
     * List转化成Map
     * @param keyName
     * @param list
     * @return 
     * @create: 2017年1月12日 下午8:55:53 xieyj
     * @history:
     */
    public static <T> Map<String, T> listToMap(String keyName, List<T> list) {
        Map<String, T> m = new HashMap<String, T>();
        try {
            for (T t : list) {
                PropertyDescriptor pd = new PropertyDescriptor(keyName,
                    t.getClass());
                Method getMethod = pd.getReadMethod();// 获得get方法
                Object o = getMethod.invoke(t);// 执行get方法返回一个Object
                m.put(o.toString(), t);
            }
            return m;
        } catch (Exception e) {
            logger.error("Convert List to Map failed");
            e.printStackTrace();
        }
        return null;
    }

}
