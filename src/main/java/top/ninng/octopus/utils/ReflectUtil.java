package top.ninng.octopus.utils;

import com.google.common.collect.Maps;
import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;
import org.apache.commons.beanutils.PropertyUtilsBean;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 动态生成对象
 */
public class ReflectUtil {

    //获取实体中属性的值
    private static Object getFieldValueByName(String fieldName, Object o) {
        try {
            //通过反射获取值
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[]{});
            return method.invoke(o, new Object[]{});
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getTarget(Object dest, Map<String, Object> addProperties) {
        // 获取属性映射
        PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
        PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(dest);
        Map<String, Class> propertyMap = Maps.newHashMap();
        for (PropertyDescriptor d : descriptors) {
            if (!"class".equalsIgnoreCase(d.getName())) {
                propertyMap.put(d.getName(), d.getPropertyType());
            }
        }
        // 添加额外属性
        for (Map.Entry<String, Object> entry : addProperties.entrySet()) {
            propertyMap.put(entry.getKey(), entry.getValue().getClass());
        }
        // 新动态 bean
        DynamicBean dynamicBean = new DynamicBean(dest.getClass(), propertyMap);
        for (Map.Entry<String, Class> entry : propertyMap.entrySet()) {
            try {
                if (!addProperties.containsKey(entry.getKey())) {
                    // 过滤额外属性
                    // 添加旧值
                    dynamicBean.setValue(entry.getKey(), propertyUtilsBean.getNestedProperty(dest, entry.getKey()));
                } else {
                    // 增加新值
                    dynamicBean.setValue(entry.getKey(), addProperties.get(entry.getKey()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dynamicBean.getTarget();
    }

    public static class DynamicBean {

        /**
         * 目标对象
         */
        private Object target;

        /**
         * 属性集合
         */
        private BeanMap beanMap;

        public DynamicBean(Class superclass, Map<String, Class> propertyMap) {
            this.target = generateBean(superclass, propertyMap);
            this.beanMap = BeanMap.create(this.target);
        }

        /**
         * 根据属性生成对象
         *
         * @param superclass
         * @param propertyMap
         * @return
         */
        private Object generateBean(Class superclass, Map<String, Class> propertyMap) {
            BeanGenerator generator = new BeanGenerator();
            if (null != superclass) {
                generator.setSuperclass(superclass);
            }
            BeanGenerator.addProperties(generator, propertyMap);
            return generator.create();
        }

        /**
         * 获取对象
         *
         * @return
         */
        public Object getTarget() {
            return this.target;
        }

        /**
         * 获取属性值
         *
         * @param property
         * @return
         */
        public Object getValue(String property) {
            return beanMap.get(property);
        }

        /**
         * bean 添加属性和值
         *
         * @param property
         * @param value
         */
        public void setValue(String property, Object value) {
            beanMap.put(property, value);
        }
    }

    public static class EntityClass {
    }
}