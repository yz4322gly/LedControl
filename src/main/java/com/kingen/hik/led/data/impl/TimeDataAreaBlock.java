package com.kingen.hik.led.data.impl;

import com.kingen.hik.led.data.*;
import com.kingen.hik.led.packet.StructurePacketMessageException;
import com.kingen.hik.util.ConvnetUtil;

/**
 * @author guolinyuan
 */
public class TimeDataAreaBlock extends AbstractVisibleDataAreaBlock
{
    private LedLocation ledLocation;

    private TimeDataAreaBlock(byte areaNumber, byte[] content) throws StructurePacketMessageException
    {
        super(areaNumber, DataAreaType.TIME, content);
    }

    @Override
    public LedLocation getLedLocation()
    {
        return this.ledLocation;
    }

    public static TimeDataAreaBlock createTimeDataAreaBlock(byte areaNumber, LedLocation ledLocation, FontColor fontColor, MoveAction moveAction, byte speed, byte stopTime, FontSize fontSize, TimeData timeData)
    {
        checkFontLedLocation(ledLocation, fontSize);
        byte[] array = createContentArray(ledLocation, fontColor, moveAction, speed, stopTime, fontSize, timeData);
        TimeDataAreaBlock block = new TimeDataAreaBlock(areaNumber, array);
        block.ledLocation = ledLocation;

        return block;
    }

    public static TimeDataAreaBlock createTimeDataAreaBlock(byte areaNumber)
    {
        return createTimeDataAreaBlock(areaNumber, LedLocation.MAX_SCREEN_64_64, FontColor.RED, MoveAction.STOP, (byte) 255, (byte) 255, FontSize.FONT_16, new TimeData());
    }

    private static byte[] createContentArray(LedLocation ledLocation, FontColor fontColor, MoveAction moveAction, byte speed, byte stopTime, FontSize fontSize, TimeData timeData)
    {
        byte[] timeDataByteArray = timeData.getByteArray();
        byte[] bytes = new byte[20 + timeDataByteArray.length];

        System.arraycopy(ledLocation.getByteArray(), 0, bytes, 0, 8);
        bytes[8] = fontColor.getCode();
        bytes[11] = moveAction.getCode();
        bytes[13] = speed;
        bytes[14] = stopTime;
        bytes[15] = fontSize.getCode();
        System.arraycopy(ConvnetUtil.intToByteArray(timeDataByteArray.length), 0, bytes, 16, 4);
        System.arraycopy(timeDataByteArray, 0, bytes, 20, timeDataByteArray.length);

        return bytes;
    }


}
