package com.kingen.hik.util;

/**
 * 一些此字节数据转换的工具类
 * @author guolinyuan
 */
public class ConvnetUtil
{
    /**
     * 将short转化为 byte数组
     * 低字节先转换方法
     * @param s short型数据
     * @return byte数组
     */
    public static byte[] shortToByteArray(short s)
    {
        return new byte[]{
                (byte) (s & 0xFF),
                (byte) ((s >> 8) & 0xFF)
        };
    }

    /**
     * 将int转化为 byte数组
     * 低字节先转换方法
     * @param s int型数据
     * @return byte数组
     */
    public static byte[] intToByteArray(int s)
    {
        return new byte[]{
                (byte) (s & 0xFF),
                (byte) ((s >> 8) & 0xFF),
                (byte) ((s >> 16) & 0xFF),
                (byte) ((s >> 24) & 0xFF)
        };
    }


    /**
     * 高字节先转换方法
     *
     * @param s
     * @return
     */
    public static byte[] intToByteArrayH(int s)
    {
        return new byte[]{
                (byte) ((s >> 24) & 0xFF),
                (byte) ((s >> 16) & 0xFF),
                (byte) ((s >> 8) & 0xFF),
                (byte) (s & 0xFF)
        };
    }

    /**
     * 高字节先转换方法
     *
     * @param s
     * @return
     */
    public static byte[] shortToByteArrayH(short s)
    {
        return new byte[]{
                (byte) ((s >> 8) & 0xFF),
                (byte) (s & 0xFF)
        };
    }

    /**
     * 将byte数组，转换无符号的16进制的数据，作为字符串返回
     * @param a
     * @return
     */
    public static String ArrayToUnsignedByte(byte[] a)
    {
        if (a == null)
        {
            return "null";
        }
        int iMax = a.length - 1;
        if (iMax == -1)
        {
            return "[]";
        }

        StringBuilder b = new StringBuilder();
        b.append('[');
        for (int i = 0; ; i++)
        {
            b.append(Integer.toHexString((int) a[i]&0xff));
            if (i == iMax)
            {
                return b.append(']').toString();
            }
            b.append(", ");
        }
    }
}
