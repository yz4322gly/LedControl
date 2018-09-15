package com.kingen.hik.util;

import java.util.Arrays;

public class ArrayUtilTest
{
    public static void main(String[] args)
    {
        byte[] a = new byte[]{};
        byte[] b = new byte[]{66,34,122};
        System.out.println(Arrays.toString(ArrayUtil.byteArrayAdd(a, b)));
        System.out.println(Arrays.toString(ArrayUtil.byteArrayAdd((byte) 23, null)));
    }
}