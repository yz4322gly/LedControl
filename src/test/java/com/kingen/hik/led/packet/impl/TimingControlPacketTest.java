package com.kingen.hik.led.packet.impl;

import com.kingen.hik.led.packet.ByteArrayable;
import com.kingen.hik.util.TCPUtil;
import org.junit.Test;

import java.io.IOException;

public class TimingControlPacketTest
{
    @Test
    public void test1() throws IOException
    {
        ByteArrayable sendable = TimingControlPacket.createTimingControlPacket(System.currentTimeMillis());
        TCPUtil.sendShortTcpMessage("192.168.1.81",10000,sendable.getByteArray());
    }

    @Test
    public void test2() throws IOException
    {
        ByteArrayable sendable = TimingControlPacket.createTimingControlPacket(
                (byte) 0x19,(byte)0x6,(byte)0x11,(byte)0x30,(byte)0x23,(byte)0x57,(byte)0x30
        );
        TCPUtil.sendShortTcpMessage("192.168.1.81",10000,sendable.getByteArray());
    }

}