package com.kingen.hik.led.data.impl;

import com.kingen.hik.led.data.AbstractDataAreaBlock;
import com.kingen.hik.led.data.ActBlock;
import com.kingen.hik.led.data.LedLocation;
import com.kingen.hik.led.packet.ByteArrayable;
import com.kingen.hik.led.packet.impl.CommonDisplayDataControlPacket;
import com.kingen.hik.util.TCPUtil;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class FontDataAreaBlockTest
{
    public static void main(String[] args) throws IOException
    {
        AbstractDataAreaBlock block = FontDataAreaBlock.createFontDataAreaBlock((byte)1,"复撒旦hi法律晒u覅首都华沙的六十度副hi水立方说的话");
        AbstractDataAreaBlock block2 = FontDataAreaBlock
                .createFontDataAreaBlock(
                        (byte)1
            , LedLocation.MAX_SCREEN_64
            , FontDataAreaBlock.FontColor.GREEN
            , FontDataAreaBlock.MoveAction.SUCCESSIVE_LEFT
            ,(byte)3,(byte)1,(byte) 16,"复撒旦hi法律晒u覅首都华沙的六十度案说法的撒发射点法发斗法大赛副hi水立方说的话");
        ActBlock actBlock = new ActBlock((byte)1, Collections.singletonList(block));
        ActBlock actBlock2 = new ActBlock((byte)2, Collections.singletonList(block2));
        List<CommonDisplayDataControlPacket> packets = CommonDisplayDataControlPacket.createCommonDisplayDataControlPacket(Collections.singletonList(actBlock2));
        for (ByteArrayable byteArrayable :packets)
        {
            TCPUtil.sendShortTcpMessage("192.168.1.81",10000,byteArrayable.getByteArray());
        }
    }

}