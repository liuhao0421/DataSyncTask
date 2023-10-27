package com.liuhao.datasynctask.util;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Component
public class ConvertUtil {
    /**
     * dxg
     * 属性相同的两个类的转换,object源类，Class<T> clazz目标类
     *
     * @param object
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T ConvertClass(Object object, Class<T> clazz) {
        T t = null;
        if (clazz != null && object != null) {
            try {
                //创建新对象
                t = clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            //获得某个类的所有声明的字段，即包括public、private和proteced
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                //如果 accessible 标志被设置为true，那么反射对象在使用的时候，不会去检查Java语言权限控制（private之类的）
                field.setAccessible(true);
                String key = field.getName();
                try {
                    //获取传入的object类的声明的字段
                    Field field1 = object.getClass().getDeclaredField(key);
                    field1.setAccessible(true);
                    //获取声明字段的值
                    Object val = field1.get(object);
                    field.set(t, val);
                } catch (Exception e) {
                    //System.out.println(object.getClass().getName() + "没有该属性: " + key);
                }
            }
        }
        return t;
    }
 
    /**
     * dxg
     * 属性不同的两个类传值，需要传对应关系，
     * convertMap.put("userId":"managerId"),其中，key是源类的属性，value是目标类的属性，只转化map中的属性，不传则不转
     *
     * @param object
     * @param clazz
     * @param convertMap
     * @param <T>
     * @return
     */
    public static <T> T ConvertClass(Object object, Class<T> clazz, ConcurrentHashMap<String, String> convertMap) {
        T t = null;
        if (clazz != null && object != null) {
            try {
                //创建新对象
                t = clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            //遍历Map的key和value
            Iterator entrys = convertMap.entrySet().iterator();
            try {
                while (entrys.hasNext()) {
                    Map.Entry entry = (Map.Entry) entrys.next();
                    String key = (String) entry.getKey();
                    //获取传入的object类的声明的字段
                    Field field1 = object.getClass().getDeclaredField(key);
                    field1.setAccessible(true);
                    //源数据的value
                    Object val = field1.get(object);
                    //目标数据的key
                    String value = (String) entry.getValue();
                    //目标数据field
                    Field fieldTo = clazz.getDeclaredField(value);
                    fieldTo.setAccessible(true);
                    //目标数据塞值
                    fieldTo.set(t, val);
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return t;
    }
}