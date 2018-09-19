package com.kingen.hik.led.data;

/**
 * 字体颜色枚举类型
 */
public enum FontColor
{
    /**
     * 红
     */
    RED((byte) 0x01),
    /**
     * 绿
     */
    GREEN((byte) 0x02),
    /**
     * 黄
     */
    YELLOW((byte) 0x04),
    /**
     * 蓝
     */
    BLUE((byte) 0x08),
    /**
     * 青
     */
    CYAN((byte) 0x10),
    /**
     * 紫
     */
    VIOLET((byte) 0x20),
    /**
     * 白
     */
    WHITE((byte) 0x40);

    private byte code;

    FontColor(byte a)
    {
        this.code = a;
    }

    public byte getCode()
    {
        return code;
    }
}