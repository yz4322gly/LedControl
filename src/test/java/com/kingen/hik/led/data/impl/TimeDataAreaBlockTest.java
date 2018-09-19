package com.kingen.hik.led.data.impl;

import com.kingen.hik.led.data.*;
import com.kingen.hik.led.packet.ByteArrayable;
import com.kingen.hik.led.packet.impl.CommonDisplayDataControlPacket;
import com.kingen.hik.util.TCPUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class TimeDataAreaBlockTest
{
    @Test
    public void test1() throws IOException
    {
        AbstractVisibleDataAreaBlock block = TimeDataAreaBlock.createTimeDataAreaBlock((byte) 1);
        ActBlock actBlock = new ActBlock((byte) 1, Collections.singletonList(block));
        List<CommonDisplayDataControlPacket> packets = CommonDisplayDataControlPacket.createCommonDisplayDataControlPacket(Collections.singletonList(actBlock));
        for (ByteArrayable byteArrayable : packets)
        {
            TCPUtil.sendShortTcpMessage("192.168.1.81", 10000, byteArrayable.getByteArray());
        }
    }

    @Test
    public void test2() throws IOException
    {
        AbstractVisibleDataAreaBlock block = TimeDataAreaBlock.createTimeDataAreaBlock((byte) 1
                , new LedLocation((short) 0,(short) 0,(short)63,(short) 15)
                , FontColor.RED
                , MoveAction.SUCCESSIVE_LEFT
                , (byte) 16, (byte) 1, FontSize.FONT_16, new TimeData(TimeData.TimeUpdateType.SECOND
                        , TimeData.HourType.H24
                        , TimeData.TimeZoneType.BEIJING_EAST_8
                        , (byte) 0
                        , (byte) 0
                        , TimeData.MODEL_02)
        );
        ActBlock actBlock = new ActBlock((byte) 1, Collections.singletonList(block));
        List<CommonDisplayDataControlPacket> packets = CommonDisplayDataControlPacket.createCommonDisplayDataControlPacket(Collections.singletonList(actBlock));
        for (ByteArrayable byteArrayable : packets)
        {
            TCPUtil.sendShortTcpMessage("192.168.1.81", 10000, byteArrayable.getByteArray());
        }
    }
}

