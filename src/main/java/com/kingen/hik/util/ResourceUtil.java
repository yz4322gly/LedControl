package com.kingen.hik.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

/**
 * 和资源读取有关的工具类，包含一些表示目录的常量
 * @author guolinyuan
 */
public class ResourceUtil
{
    /**
     * 表示Maven项目中的resources资源位置
     */
    public static final String resourcePath = Objects.requireNonNull(ResourceUtil.class.getClassLoader().getResource("")).toString().substring(6);

    /**
     * 从默认的resources目录下读取指定的文件
     * 若 文件不存在或其他io异常，不抛出异常 返回null
     * 若 获取指定的参数，若没有，返回null
     * @param key properties的key
     * @param fileName 指定的文件名
     * @return 对应的value值
     */
    public static String getProperty(String key, String fileName)
    {
        Properties properties = new Properties();
        try
        {
            properties.load(new FileInputStream(resourcePath+ fileName));
            return properties.getProperty(key);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 从默认的resources目录下读取默认的config.properties文件
     * 若 文件不存在或其他io异常，不抛出异常 返回null
     * 若 获取指定的参数，若没有，返回null
     * @param key properties的key
     * @return 对应的value值
     */
    public static String getProperty(String key)
    {
        return getProperty(key,"config.properties");
    }

}
