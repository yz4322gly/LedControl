package com.kingen.hik.led.packet;

/**
 * @author guolinyuan
 * LED六个控制指令的枚举类型
 */
public enum ControlOperatingCode
{
    /**
     * 设置屏幕参数
     */
    SET_SCREEN_PARAMETERS((byte) 0xC1),
    /**
     * 常规显示数据
     */
    COMMON_DISPLAY_DATA((byte) 0xDA),
    /**
     * 即时指令发送
     */
    IMMEDIATE_ORDER((byte) 0xDB),
    /**
     * 中断即时指令
     */
    INTERRUPT_IMMEDIATE_ORDER((byte) 0xDC),
    /**
     * 校时指令
     */
    TIMING((byte) 0xC5),
    /**
     * 继电器指令
     */
    RELAY((byte) 0xC8);

    private byte code;

    ControlOperatingCode(byte code)
    {
        this.code = code;
    }

    public byte getCode()
    {
        return code;
    }
}
