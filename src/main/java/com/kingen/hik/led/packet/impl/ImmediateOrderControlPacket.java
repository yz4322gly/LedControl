package com.kingen.hik.led.packet.impl;

import com.kingen.hik.led.packet.AbstractControlPacket;
import com.kingen.hik.led.packet.ControlOperatingCode;
import com.kingen.hik.led.packet.StructurePacketMessageException;

/**
 * LED六个控制指令之一
 * 中断信息指令
 * @author guolinyuan
 */
public class ImmediateOrderControlPacket extends AbstractControlPacket
{
    //fixme
    private ImmediateOrderControlPacket(ControlOperatingCode operatingCode, byte[] frameNumber, byte[] allLength, byte[] thisLength, byte[] content) throws StructurePacketMessageException
    {
        super(operatingCode, frameNumber, allLength, thisLength, content);
    }
}
