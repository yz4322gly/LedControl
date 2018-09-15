package com.kingen.hik.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author guolinyuan
 */
public class EntityUtil
{
    /**
     * 从一个实体类的所有字段中，获得非空的值，组成一个map返回
     * 若实体全为空，则返回空map
     *
     * @param entity 一个实体类
     * @return key为字段名
     */
    public static Map<String, Object> getNotNullMap(Object entity)
    {
        Map<String, Object> map = new HashMap<>();
        Class clazz = entity.getClass();
        Method[] methods = clazz.getMethods();
        for (Method m : methods)
        {
            String methodName = m.getName();
            try
            {
                if (methodName.startsWith("is"))
                {
                    Object o = m.invoke(entity);
                    if (o != null)
                    {
                        map.put(StringUtil.toLowerCaseFirstOne(m.getName().substring(2)), o);
                    }
                }
                else if (methodName.startsWith("get") && !methodName.equals("getClass"))
                {
                    Object o = m.invoke(entity);
                    if (o != null)
                    {
                        map.put(StringUtil.toLowerCaseFirstOne(m.getName().substring(3)), o);
                    }
                }
            }
            catch (IllegalAccessException | InvocationTargetException e)
            {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 将两个相同类型的对象，将o2的非空的字段数据，合并覆盖到o1中
     * @param o1 被覆盖的对象
     * @param o2 来源对象
     */
    public static void merge(Object o1,Object o2)
    {
        Map<String, Object> map = new HashMap<>();
        Class clazz = o1.getClass();
        Method[] methods = clazz.getMethods();
        for (Method m : methods)
        {
            String methodName = m.getName();
            try
            {
                if (methodName.startsWith("is"))
                {
                    Object o = m.invoke(o2);
                    if (o != null)
                    {
                        Method setMethod = clazz.getMethod("set" + m.getName().substring(3),o.getClass());
                        setMethod.invoke(o1,o);
                    }
                }
                else if (methodName.startsWith("get") && !methodName.equals("getClass"))
                {
                    Object o = m.invoke(o2);
                    if (o != null)
                    {
                        Method setMethod = clazz.getMethod("set" + m.getName().substring(3),o.getClass());
                        setMethod.invoke(o1,o);
                    }
                }
            }
            catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
            {
                e.printStackTrace();
            }
        }
    }
}

