package com.kingen.hik.led.packet.impl;

import com.kingen.hik.led.packet.ByteArrayable;
import com.kingen.hik.util.TCPUtil;

import java.io.IOException;

public class SetScreenParametersControlPacketTest
{

    public static void main( String[] args ) throws IOException
    {
        //设置屏参内容64*64  全彩色
        ByteArrayable sendable2 = SetScreenParametersControlPacket.createSetScreenParametersControlPacket((short) 64,(short)64, SetScreenParametersControlPacket.ColorType.FULL_COLOR);
        TCPUtil.sendShortTcpMessage("192.168.1.81",10000,sendable2.getByteArray());

    }

}