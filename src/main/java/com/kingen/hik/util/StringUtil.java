package com.kingen.hik.util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil
{
    //首字母转小写
    public static String toLowerCaseFirstOne(String s)
    {
        if (Character.isLowerCase(s.charAt(0)))
        {
            return s;
        }
        else
        {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    //首字母转大写
    public static String toUpperCaseFirstOne(String s)
    {
        if (Character.isUpperCase(s.charAt(0)))
        {
            return s;
        }
        else
        {
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }


}
