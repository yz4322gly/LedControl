package com.kingen.hik.led.data;

/**
 * 字体大小
 * @author guolinyuan
 */
public enum FontSize
{
    /**
     * 16*16大小的字体
     */

    FONT_16((byte) 16),
    /**
     * 32*32大小的字体
     */
    FONT_32((byte) 32);

    private byte code;

    FontSize(byte code)
    {
        this.code = code;
    }

    public byte getCode()
    {
        return code;
    }
}