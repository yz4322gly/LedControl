package com.kingen.hik.led.data.impl;

import com.kingen.hik.led.data.AbstractDataAreaBlock;
import com.kingen.hik.led.data.ActBlock;
import com.kingen.hik.led.packet.ByteArrayable;

import com.kingen.hik.led.packet.impl.CommonDisplayDataControlPacket;
import com.kingen.hik.util.TCPUtil;

import java.io.IOException;
import java.util.Collections;
import java.util.List;


public class VoiceAnnouncementsDataAreaBlockTest
{
    public static void main(String[] args) throws IOException
    {
        AbstractDataAreaBlock block = VoiceAnnouncementsDataAreaBlock
                .createVoiceAnnouncementsDataAreaBlock((byte)1,(byte)20,"DASFDS");
        ActBlock actBlock = new ActBlock((byte)1, Collections.singletonList(block));
        List<CommonDisplayDataControlPacket> packets = CommonDisplayDataControlPacket.createCommonDisplayDataControlPacket(Collections.singletonList(actBlock));
        for (ByteArrayable byteArrayable :packets)
        {
            TCPUtil.sendShortTcpMessage("192.168.1.81",10000,byteArrayable.getByteArray());
        }
    }
  }