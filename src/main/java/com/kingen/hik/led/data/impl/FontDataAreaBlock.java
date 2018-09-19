package com.kingen.hik.led.data.impl;

import com.kingen.hik.led.data.*;
import com.kingen.hik.led.packet.StructurePacketMessageException;
import com.kingen.hik.util.ConvnetUtil;
import java.io.UnsupportedEncodingException;

/**
 * 字符显示区域数据块
 * @author guolinyuan
 */
public class FontDataAreaBlock extends AbstractVisibleDataAreaBlock
{
    private LedLocation ledLocation ;

    private FontDataAreaBlock(byte areaNumber, byte[] content) throws StructurePacketMessageException
    {
        super(areaNumber, DataAreaType.FONT, content);
    }

    @Override
    public LedLocation getLedLocation()
    {
        return this.ledLocation;
    }

    /**
     * 从区域坐标到字符串的长度，固定大小为20
     */
    private static final int HEAD_LENGTH = 20;



    public static FontDataAreaBlock createFontDataAreaBlock(byte areaNumber, LedLocation ledLocation , FontColor fontColor, MoveAction moveAction, byte speed, byte stopTime, FontSize fontSize, String strContent) throws StructurePacketMessageException
    {
        checkFontLedLocation(ledLocation,fontSize);
        byte[] array = createContentArray(ledLocation,fontColor,moveAction,speed,stopTime,fontSize,strContent);
        FontDataAreaBlock block = new FontDataAreaBlock(areaNumber,array);
        block.ledLocation = ledLocation;

        return block;
    }

    public static FontDataAreaBlock createFontDataAreaBlock(byte areaNumber, String strContent) throws StructurePacketMessageException
    {
        return createFontDataAreaBlock(areaNumber,LedLocation.MAX_SCREEN_64_64,FontColor.RED, MoveAction.STOP,(byte)20,(byte)3,FontSize.FONT_16,strContent);
    }

    private static byte[] createContentArray(LedLocation ledLocation ,FontColor fontColor, MoveAction moveAction, byte speed, byte stopTime, FontSize fontSize, String strContent)
    {
        try
        {
            byte[] strContentArray ;
            strContentArray = strContent.getBytes("GBK");
            byte[] bytes = new byte[HEAD_LENGTH + strContentArray.length];
            System.arraycopy(ledLocation.getByteArray(),0,bytes,0,8);
            bytes[8] = fontColor.getCode();
            bytes[11] = moveAction.getCode();
            bytes[13] = speed;
            bytes[14] = stopTime;
            bytes[15] = fontSize.getCode();
            System.arraycopy(ConvnetUtil.intToByteArray(strContentArray.length),0,bytes,16,4);
            System.arraycopy(strContentArray,0,bytes,20,strContentArray.length);

            return bytes;
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}