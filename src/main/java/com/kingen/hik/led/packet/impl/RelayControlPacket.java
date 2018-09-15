package com.kingen.hik.led.packet.impl;

import com.kingen.hik.led.packet.AbstractControlPacket;
import com.kingen.hik.led.packet.ControlOperatingCode;
import com.kingen.hik.led.packet.StructurePacketMessageException;

/**
 * LED六个控制指令之一
 * 继电器控制指令
 * @author guolinyuan
 */
public class RelayControlPacket extends AbstractControlPacket
{
    public RelayControlPacket(ControlOperatingCode operatingCode, byte[] content) throws StructurePacketMessageException
    {
        super(operatingCode, content);
    }

    /**
     * 创建一个继电器指令对象，拥有2个继电器操作能力
     * @param relay1 false时，不操作，高电平。ture输出控制信号
     * @param relay2 false时，不操作，高电平。ture输出控制信号
     */
    public static RelayControlPacket createRelayControlPacket(boolean relay1,boolean relay2)
    {
        return new RelayControlPacket(ControlOperatingCode.RELAY
                ,new byte[]{
                (byte) (relay1?0x01:0x00),0,0,0,0,
                (byte) (relay2?0x01:0x00),0,0,0,0});
    }
}
