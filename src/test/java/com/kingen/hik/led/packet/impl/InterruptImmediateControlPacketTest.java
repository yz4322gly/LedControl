package com.kingen.hik.led.packet.impl;

import com.kingen.hik.led.packet.ByteArrayable;
import com.kingen.hik.util.TCPUtil;

import java.io.IOException;

public class InterruptImmediateControlPacketTest
{
    public static void main(String[] args) throws IOException
    {
        ByteArrayable sendable = InterruptImmediateControlPacket.creareInterruptImmediateControlPacket();
        TCPUtil.sendShortTcpMessage("192.168.1.81",10000,sendable.getByteArray());
    }
}