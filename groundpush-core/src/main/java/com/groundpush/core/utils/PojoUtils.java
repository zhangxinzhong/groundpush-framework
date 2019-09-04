package com.groundpush.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @description: pojo
 * @author: zhangxinzhong
 * @date: 2019-08-19 下午1:34
 */
@Component
public class PojoUtils {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private StringUtils stringUtils;

    public void setField(Object object, String fieldName, String fieldValue) {
        String methodName = "set" + stringUtils.capitalize(fieldName);

        try {
            Class<?> cls = object.getClass();
            Method method = cls.getMethod(methodName, String.class);

            method.invoke(object, fieldValue);
        } catch (NoSuchMethodException e) {
            logger.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public String getField(Object object, String fieldName) {
        String methodName = "get" + stringUtils.capitalize(fieldName);

        String fieldValue = "";
        try {
            Class<?> cls = object.getClass();
            Method method = cls.getMethod(methodName);

            Object fieldV = method.invoke(object);
            if (fieldV != null) {
                fieldValue = fieldV.toString();
            }
        } catch (NoSuchMethodException e) {
            logger.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            logger.error(e.getMessage(), e);
        }

        return fieldValue;
    }

    public <T> void setObjectField(Object object, String fieldName, Object fieldValue, Class claParam) {
        String methodName = "set" + stringUtils.capitalize(fieldName);

        try {
            Class<?> cls = object.getClass();
            Method method = cls.getMethod(methodName, claParam);
            method.invoke(object, fieldValue);
        } catch (NoSuchMethodException e) {
            logger.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public Object getObjectField(Object object, String fieldName) {
        String methodName = "get" + stringUtils.capitalize(fieldName);
        Object fieldValue = new Object();
        try {
            Class<?> cls = object.getClass();
            Method method = cls.getMethod(methodName);
            fieldValue = method.invoke(object);

        } catch (NoSuchMethodException e) {
            logger.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            logger.error(e.getMessage(), e);
        }
        return fieldValue;
    }

    /**
     * Retrieve field value in current class and its super class among private,package,public modifier scope.
     *
     * @param object
     * @param fieldName
     * @return
     */
    public Object getFieldRecursively(Object object, String fieldName) {
        return getFieldRecursively(object.getClass(), object, fieldName);
    }

    private Object getFieldRecursively(Class cls, Object object, String fieldName) {
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            if (Modifier.isTransient(field.getModifiers())) {
                continue;
            }
            field.setAccessible(true);
            if (fieldName.equals(field.getName())) {
                try {
                    return field.get(object);
                } catch (IllegalAccessException e) {
                    logger.error("", e);
                    throw new RuntimeException("fail to retrieve field value.", e);
                }
            }
        }
        if (cls.getSuperclass() != Object.class) {
            return getFieldRecursively(cls.getSuperclass(), object, fieldName);
        }
        return null;
    }

    public void debugForClass(Class cls) {
        String className = cls.getName();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            logger.debug(className + ":[field name]:" + field.getName() + "[field type]:" + field.getType());
        }
        Method[] methods = cls.getDeclaredMethods();
        for (Method method : methods) {
            method.setAccessible(true);
            logger.debug(className + ":[method name]:" + method.getName());
        }
        if (cls.getSuperclass() != Object.class) {
            debugForClass(cls.getSuperclass());
        }
    }
}
