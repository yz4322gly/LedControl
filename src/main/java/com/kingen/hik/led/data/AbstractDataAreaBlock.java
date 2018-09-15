package com.kingen.hik.led.data;

import com.kingen.hik.led.packet.ByteArrayable;
import com.kingen.hik.led.packet.StructurePacketMessageException;
import com.kingen.hik.util.ConvnetUtil;

/**
 * 抽象区域数据块，是字符内码区域，时间节目区域，点阵位图区域
 * 和不显示在屏幕上的语音播报区域的抽象表示
 * <p>
 * 每个节目 由 一个节目属性，和若干（区域数据块）此类实现构成。
 * 每个常规数据 由 一个节目个数（1byte） 和 若干个节目构成
 *
 * @author guolinyuan
 */
public abstract class AbstractDataAreaBlock implements ByteArrayable
{

    /**
     * 区域数据类型
     */
    public enum DataAreaType
    {
        /**
         * 语音播报数据：0x2D
         */
        VOICE((byte) 0x2D),
        /**
         * 字符内码：0x0E
         */
        FONT((byte) 0x0E),
        /**
         * 点阵位图: 0x01
         */
        PHOTO((byte) 0x01),
        /**
         * 时间节目：0x21
         */
        TIME((byte) 0x21);

        private byte code;
        DataAreaType(byte i)
        {
            this.code = i;
        }

        public byte getCode()
        {
           return code;
        }
    }

    /**
     * 代表此对象核心内容
     * 即此对象的同义byte数组
     */
    private byte[] bytes;

    /**
     * 区域编号，暂不清楚是如何编号
     * 文档未说明，只说明是从1开始标号
     * 推测是此区域块在一个节目数据块中的编号
     */
    private byte areaNumber;

    /**
     * 区域数据块大小
     */
    private int size;

    /**
     * 区域数据类型 DataAreaType
     * 参见枚举类型
     */
    private DataAreaType type;

    /**
     * 除了content的其他3个内容的长度
     */
    private static final int HEAD_LENGTH = 6;

    /**
     * 此实现类不同的内容实现
     */
    private byte[] content;

    public AbstractDataAreaBlock(byte areaNumber, DataAreaType type, byte[] content)throws StructurePacketMessageException
    {
        checkParameter(areaNumber,size,type,content);

        this.areaNumber = areaNumber;
        this.type = type;
        this.content = content;


        structurePacketMessage();
    }

    private void structurePacketMessage()
    {
        this.bytes = new byte[HEAD_LENGTH + this.content.length];
        this.size = this.bytes.length;
        System.out.println("数据区域大小" + this.size);

        byte[] sizeB = ConvnetUtil.intToByteArray(this.size);
        this.bytes[0] = this.areaNumber;
        System.arraycopy(sizeB,0,this.bytes,1,4);
        this.bytes[5] = this.type.getCode();
        System.arraycopy(this.content,0,this.bytes,HEAD_LENGTH,this.content.length);
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
        System.out.println("区域数据块包为：" + ConvnetUtil.ArrayToUnsignedByte(this.bytes));
        return this.bytes;
    }

    private void checkParameter(byte areaNumber, int size, DataAreaType type, byte[] content) throws StructurePacketMessageException
    {
        if (areaNumber < 1)
        {
            throw new StructurePacketMessageException("区域数据块编号从1开始");
        }
        if (size < 0)
        {
            throw new StructurePacketMessageException("区域数据块大小必须大于0");
        }
        if (type == null)
        {
            throw new StructurePacketMessageException("区域数据块类型0");
        }
        if (content == null)
        {
            throw new StructurePacketMessageException("区域数据块不能为null");
        }
    }

    public DataAreaType getType()
    {
        return type;
    }

    public int getSize()
    {
        return size;
    }
}
