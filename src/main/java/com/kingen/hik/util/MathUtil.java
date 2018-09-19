package com.kingen.hik.util;

import java.awt.Rectangle;
import java.util.Calendar;
import java.util.List;

/**
 * @author guolinyuan
 */
public class MathUtil
{
    /**
     * 检测指定的矩形列表中是否存在任意的一个相交
     * @param rectangles 若为null 或者 元素个数小于1，返回false
     * @return 存在任意一个相交都返回true，否则返回false
     */
    public static boolean rectsIntersects(List<Rectangle> rectangles)
    {
        if (rectangles == null || rectangles.size() == 0 )
        {
            return false;
        }
        for (int i = 0 ; i < rectangles.size()-1 ; i++)
        {
            Rectangle now = rectangles.get(i);
            for (int j = i + 1; j < rectangles.size(); j++)
            {
                if (now.intersects(rectangles.get(j)))
                {
                    //存在一个碰撞检测为true，则略过其他所有检测，直接返回
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 将传入的数据当作16进制数处理，输出对应的10进制数
     */
    public static byte converted16Binary(int b)
    {
       return Byte.parseByte ( String.valueOf(b),16);
    }
}
