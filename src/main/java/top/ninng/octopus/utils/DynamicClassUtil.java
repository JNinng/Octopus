package top.ninng.octopus.utils;

import com.google.common.collect.Maps;
import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;
import org.apache.commons.beanutils.PropertyUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 动态生成对象
 */
public class DynamicClassUtil {

    //获取实体中属性的值
    private static Object getFieldValueByName(String fieldName, Object o) {
        try {
            //通过反射获取值
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter);
            return method.invoke(o);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getTarget(Object superclass, Map<String, Object> addProperties) {
        Map<String, Class<?>> propertyMap = Maps.newHashMap();
        // 原有属性
        for (PropertyDescriptor d : PropertyUtils.getPropertyDescriptors(superclass)) {
            if (!"class".equalsIgnoreCase(d.getName())) {
                propertyMap.put(d.getName(), d.getPropertyType());
            }
        }
        // 添加的额外属性
        for (Map.Entry<String, Object> entry : addProperties.entrySet()) {
            propertyMap.put(entry.getKey(), entry.getValue().getClass());
        }
        BeanGenerator generator = new BeanGenerator();
        generator.setSuperclass(superclass.getClass());
        BeanGenerator.addProperties(generator, propertyMap);
        Object dynamic = generator.create();
        BeanMap beanMap = BeanMap.create(dynamic);
        for (Map.Entry<String, Class<?>> entry : propertyMap.entrySet()) {
            String key = entry.getKey();
            if (addProperties.containsKey(key)) {
                // 增加新值
                beanMap.put(key, addProperties.get(key));
            } else {
                // 增旧新值
                try {
                    beanMap.put(key, PropertyUtils.getNestedProperty(superclass, key));
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
        return dynamic;
    }

    public static class EntityClass {
    }
}