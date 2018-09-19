package com.kingen.hik.led.data;

import com.kingen.hik.led.packet.ByteArrayable;
import com.kingen.hik.led.packet.StructurePacketMessageException;
import com.kingen.hik.util.ConvnetUtil;

import java.awt.*;

/**
 * led显示的区域数据块
 * 所必须有的属性
 * @author guolinyuan
 */
public class LedLocation implements ByteArrayable
{

    /**
     * 代表此对象核心内容
     * 即此对象的同义byte数组
     */
    private byte[] bytes = new byte[8];

    private static final int SCREEN_LENGTH_MAX = 63;
    private static final int SCREEN_WIDTH_MAX = 63;

    /**
     * 一个64*64占满全屏幕的区域数据块
     */
    public static final LedLocation MAX_SCREEN_64_64 = new LedLocation((short) 0,(short) 0,(short) 63,(short) 63);
    public static final LedLocation HALF_LEFT_32_64 = new LedLocation((short) 0,(short) 0,(short) 31,(short) 63);
    public static final LedLocation HALF_RIGHT_32_64 = new LedLocation((short) 32,(short) 0,(short) 63,(short) 63);
    public static final LedLocation HALF_UP_64_32 = new LedLocation((short) 0,(short) 0,(short) 63,(short) 31);
    public static final LedLocation HALF_DOWN_64_32 = new LedLocation((short) 0,(short) 32,(short) 63,(short) 63);
    public static final LedLocation QUARTER_LEFT_UP_32_32 = new LedLocation((short) 0,(short) 0,(short) 31,(short) 31);
    public static final LedLocation QUARTER_LEFT_DOWN_32_32 = new LedLocation((short) 0,(short) 32,(short) 31,(short) 63);
    public static final LedLocation QUARTER_RIGHT_UP_32_32 = new LedLocation((short) 32,(short) 0,(short) 63,(short) 31);
    public static final LedLocation QUARTER_RIGHT_DOWN_32_32 = new LedLocation((short) 32,(short) 32,(short) 63,(short) 63);

    /**
     * 表示显示区域的左上角横坐标x
     */
    private short topLeftX ;
    /**
     * 表示显示区域的左上角纵坐标y
     */
    private short topLeftY ;

    /**
     * 表示显示区域的右下角横坐标X
     */
    private short lowerRightX ;
    /**
     * 表示显示区域的右下角纵坐标y
     */
    private short lowerRightY ;

    public short getTopLeftX()
    {
        return topLeftX;
    }

    public void setTopLeftX(short topLeftX)
    {
        this.topLeftX = topLeftX;
    }

    public short getTopLeftY()
    {
        return topLeftY;
    }

    public void setTopLeftY(short topLeftY)
    {
        this.topLeftY = topLeftY;
    }

    public short getLowerRightX()
    {
        return lowerRightX;
    }

    public void setLowerRightX(short lowerRightX)
    {
        this.lowerRightX = lowerRightX;
    }

    public short getLowerRightY()
    {
        return lowerRightY;
    }

    public void setLowerRightY(short lowerRightY)
    {
        this.lowerRightY = lowerRightY;
    }

    public LedLocation(short topLeftX, short topLeftY, short lowerRightX, short lowerRightY)
    {
        checkParameter(topLeftX,topLeftY,lowerRightX,lowerRightY);
        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;
        this.lowerRightX = lowerRightX;
        this.lowerRightY = lowerRightY;
        structurePacketMessage();
    }

    private void structurePacketMessage()
    {
        System.arraycopy(ConvnetUtil.shortToByteArray(topLeftX),0,bytes,0,2);
        System.arraycopy(ConvnetUtil.shortToByteArray(topLeftY),0,bytes,2,2);
        System.arraycopy(ConvnetUtil.shortToByteArray(lowerRightX),0,bytes,4,2);
        System.arraycopy(ConvnetUtil.shortToByteArray(lowerRightY),0,bytes,6,2);
    }

    private void checkParameter(short topLeftX, short topLeftY, short lowerRightX, short lowerRightY)
    {
        if (topLeftX < 0 || topLeftX > SCREEN_LENGTH_MAX
        || topLeftY < 0 || topLeftY > SCREEN_WIDTH_MAX
        || lowerRightX < 0 || lowerRightX > SCREEN_LENGTH_MAX
        || lowerRightY < 0 || lowerRightY > SCREEN_WIDTH_MAX )
        {
            throw new StructurePacketMessageException("超出led屏幕的范围了！");
        }
        if (lowerRightX - topLeftX < 0 || lowerRightY - topLeftY < 0)
        {
            throw new StructurePacketMessageException("右下角的点位于左下角点貌似写反了哦！");
        }
    }

    /**
     * 当调用此方法时，若{@link this#bytes}为null时<br/>
     * 此方法会尝试调用@{@link this#structurePacketMessage()}构造数据<br/>
     * 若构造失败，则是因为有动态数据出现为null的原因，<br/>
     * 请使用构造方法构造对象，会抛出NullPointerException
     *
     * @return 此数据包对应的自己数组
     * @see ByteArrayable#getByteArray() 参见此接口方法
     */
    @Override
    public byte[] getByteArray()
    {
        if (this.bytes == null)
        {
            structurePacketMessage();
        }
        System.out.println("位置数据包为：" + ConvnetUtil.ArrayToUnsignedByte(this.bytes));
        return this.bytes;
    }

    public Rectangle getRectangle()
    {
        //此处是长度而不是点坐标，故而加一
        return new Rectangle(topLeftX,topLeftY,lowerRightX - topLeftX + 1,lowerRightY - topLeftY + 1);
    }
}
