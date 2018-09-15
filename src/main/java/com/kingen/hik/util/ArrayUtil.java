package com.kingen.hik.util;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.Arrays;

/**
 * @author guolinyuan
 */
public class ArrayUtil
{

    /**
     * 粘合一个byte值和一个byte数组，成为新的数组
     * bytes传入null，将初始化为一个长度为0的数组
     * 单个byte值在前
     * @param sByte 单个的byte值
     * @param bytes 一个byte数组
     * @return sByte + bytes 的数组值
     */
    public static byte[] byteArrayAdd(@Nullable byte sByte ,@Nullable byte[] bytes)
    {
        if (bytes == null)
        {
            bytes = new byte[]{};
        }
        byte[] answer = new byte[bytes.length + 1];
        answer[0] = sByte;
        System.arraycopy(bytes,0,answer,1,bytes.length);
        return answer;
    }

    /**
     * 将两个数组粘到一起，成为一个新的数组
     * 传入null，将初始化为一个长度为0的数组
     * @param a 第一个数组
     * @param b 第二个数组
     * @return a + b的新数组
     */
    public static byte[] byteArrayAdd(@Nullable byte[] a,@Nullable byte[] b)
    {
        if (a == null)
        {
            a = new byte[]{};
        }
        if (b == null)
        {
            b = new byte[]{};
        }

        byte[] answer = new byte[a.length + b.length];
        System.arraycopy(a,0,answer,0,a.length);
        System.arraycopy(b,0,answer,a.length,b.length);
        return answer;
    }
}
