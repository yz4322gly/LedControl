package com.kingen.hik.led.data.impl;

import com.kingen.hik.led.packet.ByteArrayable;
import com.kingen.hik.led.packet.impl.CommonDisplayDataControlPacket;
import com.kingen.hik.util.ConvnetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * 时间类型数据
 * @author guolinyuan
 */
public class TimeData implements ByteArrayable
{
    public static final String MODEL_01 = "%Y4-%M3-%D2 %h0:%m:%s %w1";
    public static final String MODEL_02 = "现在是北京时间：%Y4-%M3-%D2 %h0:%m:%s 星期%w2";
    public static final String MODEL_03 = "现在是北京时间：%Y4年%M2月%D0日 %h1:%m:%s 星期%w2";

    /**
     * 时间更新类型
     */
    public enum TimeUpdateType
    {
        /**
         *  表示更新秒
         */
        SECOND((byte)'s'),
        /**
         * 表示更新分
         */
        MINUTE((byte)'m'),
        /**
         * 表示更新小时
         */
        HOUR((byte)'h'),
        /**
         * 表示更新周
         */
        WEEK((byte)'w'),
        /**
         * 表示更新日
         */
        DAY((byte)'D'),
        /**
         * 表示更新月
         */
        MONTH((byte)'M'),
        /**
         * 表示更新年
         */
        YEAR((byte)'Y');

        private byte code;

        TimeUpdateType(byte code)
        {
            this.code = code;
        }

        public byte getCode()
        {
            return code;
        }
    }

    /**
     * 采用12还是24小时制
     */
    public enum HourType
    {
        /**
         * 12小时制
         */
        H12((byte)1),
        /**
         * 24小时制
         */
        H24((byte)0);

        private byte code;

        HourType(byte code)
        {
            this.code = code;
        }

        public byte getCode()
        {
            return code;
        }
    }

    /**
     * 时区类型
     */
    public enum TimeZoneType
    {
        /**
         * 东8区北京时间
         */
        BEIJING_EAST_8((byte)0),

        /**
         * 东区
         */
        EAST((byte)1),

        /**
         * 西区
         */
        WEST((byte)2);

        private byte code;

        TimeZoneType(byte code)
        {
            this.code = code;
        }

        public byte getCode()
        {
            return code;
        }
    }

    private TimeUpdateType timeUpdateType = TimeUpdateType.SECOND;
    private HourType hourType = HourType.H24;
    private TimeZoneType timeZoneType = TimeZoneType.BEIJING_EAST_8;
    private byte timeZoneDeviationHour = 0;
    private byte timeZoneDeviationMinute = 0;
    private String timeDate = MODEL_01;

    private static Logger logger = LoggerFactory.getLogger(TimeData.class);


    public TimeUpdateType getTimeUpdateType()
    {
        return timeUpdateType;
    }

    public void setTimeUpdateType(TimeUpdateType timeUpdateType)
    {
        this.timeUpdateType = timeUpdateType;
    }

    public HourType getHourType()
    {
        return hourType;
    }

    public void setHourType(HourType hourType)
    {
        this.hourType = hourType;
    }

    public TimeZoneType getTimeZoneType()
    {
        return timeZoneType;
    }

    public void setTimeZoneType(TimeZoneType timeZoneType)
    {
        this.timeZoneType = timeZoneType;
    }

    public byte getTimeZoneDeviationHour()
    {
        return timeZoneDeviationHour;
    }

    public void setTimeZoneDeviationHour(byte timeZoneDeviationHour)
    {
        this.timeZoneDeviationHour = timeZoneDeviationHour;
    }

    public byte getTimeZoneDeviationMinute()
    {
        return timeZoneDeviationMinute;
    }

    public void setTimeZoneDeviationMinute(byte timeZoneDeviationMinute)
    {
        this.timeZoneDeviationMinute = timeZoneDeviationMinute;
    }

    public String getTimeDate()
    {
        return timeDate;
    }

    public void setTimeDate(String timeDate)
    {
        this.timeDate = timeDate;
    }

    /**
     * 创建一个默认的时间数据格式字符
     */
    public TimeData()
    {

    }

    /**
     * 按照指定要求创建一个时间数据格式字符
     * @param timeUpdateType 时间更新类型
     * @param hourType 小时制12小时或者24小时
     * @param timeZoneType 时区类型
     * @param timeZoneDeviationHour 时区偏移小时，16进制,传入请带0x或者直接通过{@link com.kingen.hik.util.MathUtil#converted16Binary(int)}传入转换后的值
     * @param timeZoneDeviationMinute 时区偏移分钟，16进制，传入请带0x或者直接通过{@link com.kingen.hik.util.MathUtil#converted16Binary(int)}传入转换后的值
     * @param timeDate 时间数据格式字符，参见LED控制卡附录，时间数据格式转义字符
     */
    public TimeData(TimeUpdateType timeUpdateType, HourType hourType, TimeZoneType timeZoneType, byte timeZoneDeviationHour, byte timeZoneDeviationMinute, String timeDate)
    {
        this.timeUpdateType = timeUpdateType;
        this.hourType = hourType;
        this.timeZoneType = timeZoneType;
        this.timeZoneDeviationHour = timeZoneDeviationHour;
        this.timeZoneDeviationMinute = timeZoneDeviationMinute;
        this.timeDate = timeDate;
    }

    @Override
    public byte[] getByteArray()
    {
        try
        {
            byte[] timeDateByteArray = this.timeDate.getBytes("GBK");
            byte[] bytes =  new byte[6 + timeDateByteArray.length];
            bytes[0] = this.timeUpdateType.getCode();
            bytes[2] = this.hourType.getCode();
            bytes[3] = this.timeZoneType.getCode();
            bytes[4] = this.timeZoneDeviationHour;
            bytes[5] = this.timeZoneDeviationMinute;
            System.arraycopy(timeDateByteArray,0,bytes,6,timeDateByteArray.length);

            logger.info("时间数据格式字符内容为：" + ConvnetUtil.ArrayToUnsignedByte(bytes));
            return bytes;
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            return null;
        }
    }

}
