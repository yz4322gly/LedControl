package com.kingen.hik.led.packet.impl;

import com.kingen.hik.led.packet.AbstractControlPacket;
import com.kingen.hik.led.packet.ControlOperatingCode;
import com.kingen.hik.led.packet.StructurePacketMessageException;
import com.kingen.hik.util.ConvnetUtil;

/**
 * LED六个控制指令之一
 * 屏幕显示设置指令
 * @author guolinyuan
 */
public class SetScreenParametersControlPacket extends AbstractControlPacket
{
    /**
     * 纵向点数：显示屏的显示高度，最大128
     */
    private static final short MAX_LED_HEIGHT = 128;
    /**
     * 纵向点数：显示屏的显示高度，最小16
     */
    private static final short MIN_LED_HEIGHT = 16;
    /**
     * 横向点数：显示屏的显示宽度，最大256
     */
    private static final short MAX_LED_WIDTH = 256;
    /**
     * 横向点数：显示屏的显示宽度，最小16
     */
    private static final short MIN_LED_WIDTH = 16;

    /**
     * 使用一个content数组构造一个设置屏参的实例
     * 此方法过于底层不推荐使用，请使用{@link SetScreenParametersControlPacket#createSetScreen(short, short, ColorType)}
     *
     * @param content 核心数据数组内容
     * @throws StructurePacketMessageException 当content长度超过512时，抛出此异常
     */
    private SetScreenParametersControlPacket(byte[] content) throws StructurePacketMessageException
    {
        super(ControlOperatingCode.SET_SCREEN_PARAMETERS, content);
    }

    /**
     * 静态方法构造一个设置屏参对象。推荐使用此方法
     *
     * @param xNum      横向点数：显示屏的显示宽度，最小16,最大256
     * @param yNum      纵向点数：显示屏的显示高度，最小16，最大128
     * @param colorType 颜色 单色 双色 全彩
     * @return 一个SetScreenParametersControlPacket的实例
     * @throws StructurePacketMessageException 当输入参数不合法时，抛出此异常
     */
    public static SetScreenParametersControlPacket createSetScreenParametersControlPacket(short xNum, short yNum, ColorType colorType) throws StructurePacketMessageException
    {
        byte[] bytes = new byte[7];
        if (xNum > MAX_LED_WIDTH || xNum < MIN_LED_WIDTH || yNum > MAX_LED_HEIGHT || yNum < MIN_LED_HEIGHT)
        {
            throw new StructurePacketMessageException("屏幕参数不在合法的范围内");
        }
        System.arraycopy((ConvnetUtil.shortToByteArray(xNum)), 0, bytes, 0, 2);
        System.arraycopy((ConvnetUtil.shortToByteArray(yNum)), 0, bytes, 2, 2);
        bytes[4] = colorType.getCode();
        return new SetScreenParametersControlPacket(bytes);
    }

    /**
     * 色彩类型的枚举变量
     */
    public enum ColorType
    {
        /**
         * 单色模式
         */
        ONE_COLOR((byte) 0x01),
        /**
         * 双色模式
         */
        TOW_COLOR((byte) 0x02),
        /**
         * 全彩模式
         */
        FULL_COLOR((byte) 0x03);

        private byte code;

        ColorType(byte code)
        {
            this.code = code;
        }

        public byte getCode()
        {
            return code;
        }
    }
}
