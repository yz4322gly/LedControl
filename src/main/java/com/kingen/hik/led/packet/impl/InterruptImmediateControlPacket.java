package com.kingen.hik.led.packet.impl;

import com.kingen.hik.led.packet.AbstractControlPacket;
import com.kingen.hik.led.packet.ControlOperatingCode;
import com.kingen.hik.led.packet.StructurePacketMessageException;

/**
 * LED六个控制指令之一
 * 中断即时指令
 * @author guolinyuan
 */
public class InterruptImmediateControlPacket extends AbstractControlPacket
{
    /**
     * 中断即使信息指令内容
     */
    private static final byte[] INTERRUPT_IMMEDIATE_CONTENT = {0x00, 0x00};

    private InterruptImmediateControlPacket(ControlOperatingCode operatingCode, byte[] content) throws StructurePacketMessageException
    {
        super(operatingCode, content);
    }

    public static InterruptImmediateControlPacket creareInterruptImmediateControlPacket()
    {
        return new InterruptImmediateControlPacket(ControlOperatingCode.INTERRUPT_IMMEDIATE_ORDER, INTERRUPT_IMMEDIATE_CONTENT);
    }
}
