package com.kingen.hik.led.data;

/**
 * 动作方式枚举类型
 */
public enum MoveAction
{
    /**
     * 静止显示/立即显示/翻页显示
     */
    STOP((byte) 0x01),
    /**
     * 向左移动
     */
    LEFT((byte) 0x1C),
    /**
     * 向右移动
     */
    RIGHT((byte) 0x1D),
    /**
     * 向上移动
     */
    TOP((byte) 0x1A),
    /**
     * 向下移动
     */
    DOWN((byte) 0x1B),
    /**
     * 连续向上移动
     */
    SUCCESSIVE_TOP((byte) 0x1E),
    /**
     * 连续向下移动
     */
    SUCCESSIVE_DOWN((byte) 0x1F),
    /**
     * 连续向左移动
     */
    SUCCESSIVE_LEFT((byte) 0x20),
    /**
     * 连续向右移动
     */
    SUCCESSIVE_RIGHT((byte) 0x21),
    /**
     * 闪烁
     */
    TWINKLE((byte) 0x29);

    private byte code;
    MoveAction(byte code)
    {
        this.code = code;
    }

    public byte getCode()
    {
        return code;
    }
}
