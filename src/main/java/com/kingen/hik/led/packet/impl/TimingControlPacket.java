package com.kingen.hik.led.packet.impl;

import com.kingen.hik.led.packet.AbstractControlPacket;
import com.kingen.hik.led.packet.ControlOperatingCode;
import com.kingen.hik.led.packet.StructurePacketMessageException;

import java.util.Calendar;

/**
 * LED六个控制指令之一
 * 校时指令
 * @author guolinyuan
 */
public class TimingControlPacket extends AbstractControlPacket
{
    private static final byte WEEK_MAX = 6;
    private static final byte WEEK_MIN = 0;
    private static final byte MONTH_MAX = 12;
    private static final byte MONTH_MIN = 1;
    private static final byte DAY_MAX = 31;
    private static final byte DAY_MIN = 1;
    private static final byte HOUR_MAX = 23;
    private static final byte HOUR_MIN = 0;
    private static final byte MIN_MAX = 59;
    private static final byte MIN_MIN = 0;
    private static final byte SEC_MAX = 59;
    private static final byte SEC_MIN = 0;


    /**
     * 请调用静态方法构造{@link TimingControlPacket#createTimingControlPacket(long)}
     * 或{@link TimingControlPacket#createTimingControlPacket(byte, byte, byte, byte, byte, byte, byte)}
     * @param operatingCode 0xC5
     * @param content 时间内容
     * @throws StructurePacketMessageException
     */
    private TimingControlPacket(byte[] content) throws StructurePacketMessageException
    {
        super(ControlOperatingCode.TIMING, content);
    }

    /**
     * 按照指定时间，生成校时指令
     * @param timeMillis 它是距历元（即格林威治标准时间 1970 年 1 月 1 日的 00:00:00.000，格里高利历）的偏移量
     */
    public static TimingControlPacket createTimingControlPacket(long timeMillis)
    {
        return new TimingControlPacket(getByte(timeMillis));
    }

    /**
     * 按照指定时间，生成校时指令 此方法有构造失败的可能性，不推荐使用
     * @param year 年：实际年数据减2000，如2017发送17
     * @param week 星期：0-6星期天用0表示
     * @param month 月：1-12
     * @param day 日：1-31
     * @param hourOfDay 时：0-23
     * @param minute  分：0-59
     * @param second 秒：0-59
     * @throws StructurePacketMessageException 设置参数不在上文指定的范围内抛出此异常
     */
    public static TimingControlPacket createTimingControlPacket(byte year, byte week, byte month, byte day, byte hourOfDay, byte minute, byte second) throws StructurePacketMessageException
    {
        if (week < WEEK_MIN || week > WEEK_MAX)
        {
            throw new StructurePacketMessageException("周参数设置不对！");
        }
        if (month < MONTH_MIN || month > MONTH_MAX)
        {
            throw new StructurePacketMessageException("月参数设置不对！");
        }
        if (day < DAY_MIN || day > DAY_MAX)
        {
            throw new StructurePacketMessageException("日参数设置不对！");
        }
        if (hourOfDay < HOUR_MIN || hourOfDay > HOUR_MAX)
        {
            throw new StructurePacketMessageException("小时参数设置不对！");
        }
        if (minute < MIN_MIN || minute > MIN_MAX)
        {
            throw new StructurePacketMessageException("分钟参数设置不对！");
        }
        if (second < SEC_MIN || second > SEC_MAX)
        {
            throw new StructurePacketMessageException("秒参数设置不对！");
        }

        return new TimingControlPacket(new byte[]{year, week, month, day, hourOfDay, minute, second});
    }

    private static byte[] getByte(long timeMillis)
    {
        byte[] bytes = new byte[7];
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);
        bytes[0] = (byte) (calendar.get(Calendar.YEAR) -2000);
        bytes[1] = (byte) (calendar.get(Calendar.DAY_OF_WEEK) - 1);
        bytes[2] = (byte) (calendar.get(Calendar.MONTH) + 1);
        bytes[3] = (byte) calendar.get(Calendar.DATE);
        bytes[4] = (byte) calendar.get(Calendar.HOUR_OF_DAY);
        bytes[5] = (byte) calendar.get(Calendar.MINUTE);
        bytes[6] = (byte) calendar.get(Calendar.SECOND);

        return bytes;
    }
}
