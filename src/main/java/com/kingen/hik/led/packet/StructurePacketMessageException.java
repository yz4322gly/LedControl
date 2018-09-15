package com.kingen.hik.led.packet;

/**
 * 当出现构造异常时抛出此异常
 * @author guolinyuan
 */
public class StructurePacketMessageException extends RuntimeException
{
    public StructurePacketMessageException(String message)
    {
        super(message);
    }

    public StructurePacketMessageException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
