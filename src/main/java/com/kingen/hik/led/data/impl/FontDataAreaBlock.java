package com.kingen.hik.led.data.impl;

import com.kingen.hik.led.data.AbstractDataAreaBlock;
import com.kingen.hik.led.data.LedLocation;
import com.kingen.hik.led.packet.StructurePacketMessageException;
import com.kingen.hik.util.ConvnetUtil;
import java.io.UnsupportedEncodingException;

public class FontDataAreaBlock extends AbstractDataAreaBlock
{
    private FontDataAreaBlock(byte areaNumber, DataAreaType type, byte[] content) throws StructurePacketMessageException
    {
        super(areaNumber, type, content);
    }

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

    /**
     * 从区域坐标到字符串的长度，固定大小为20
     */
    private static final int HEAD_LENGTH = 20;

    public static FontDataAreaBlock createFontDataAreaBlock(byte areaNumber, LedLocation ledLocation , FontColor fontColor, MoveAction moveAction, byte speed, byte stopTime, byte fontSize, String strContent) throws StructurePacketMessageException
    {
        byte[] array = createContentArray(ledLocation,fontColor,moveAction,speed,stopTime,fontSize,strContent);
        return new FontDataAreaBlock(areaNumber,DataAreaType.FONT,array);
    }

    public static FontDataAreaBlock createFontDataAreaBlock(byte areaNumber, String strContent) throws StructurePacketMessageException
    {
        return createFontDataAreaBlock(areaNumber,LedLocation.MAX_SCREEN_64,FontColor.RED,MoveAction.STOP,(byte)20,(byte)3,(byte) 16,strContent);
    }

    private static byte[] createContentArray(LedLocation ledLocation ,FontColor fontColor, MoveAction moveAction, byte speed, byte stopTime, byte fontSize, String strContent)
    {
        byte[] strContentArray = new byte[0];
        try
        {
            strContentArray = strContent.getBytes("GBK");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        byte[] bytes = new byte[HEAD_LENGTH + strContentArray.length];

        System.arraycopy(ledLocation.getByteArray(),0,bytes,0,8);
        bytes[8] = fontColor.getCode();
        bytes[11] = moveAction.getCode();
        bytes[13] = speed;
        bytes[14] = stopTime;
        bytes[15] = fontSize;
        System.arraycopy(ConvnetUtil.intToByteArray(strContentArray.length),0,bytes,16,4);
        System.arraycopy(strContentArray,0,bytes,20,strContentArray.length);

        return bytes;
    }
}