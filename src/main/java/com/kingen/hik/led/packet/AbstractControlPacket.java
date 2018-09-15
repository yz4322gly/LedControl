package com.kingen.hik.led.packet;

import com.kingen.hik.util.ConvnetUtil;
import org.springframework.util.Assert;

/**
 * 抽象的led控制包<br/>
 * 每一个控制包代表一次传输数据的过程<br/>
 * 每一个控制指令，即6个控制指令之一 {@link ControlOperatingCode}<br/>
 * 都对应一个此类的子类，此类的核心方法为{@link ByteArrayable#getByteArray()}<br/>
 * 使用此方法可以获得对应的二进制流<br/>
 *
 * 注意继承此类的所有实现类，覆盖{@link AbstractControlPacket#AbstractControlPacket(ControlOperatingCode, byte[], byte[], byte[], byte[])}<br/>
 * 或者{@link AbstractControlPacket#AbstractControlPacket(ControlOperatingCode, byte[])}之一<br/>
 * 如果此实现类的指令长度不可能或无需分包，则使用{@link AbstractControlPacket#AbstractControlPacket(ControlOperatingCode, byte[])}
 * 否则采用第一个<br/>
 *
 *
 *
 * @author guolinyuan
 */
public abstract class AbstractControlPacket implements ByteArrayable
{
    /**
     * 请求数据包的实体
     */
    private byte[] packetMessage;

    /**
     * 帧头 固定内容 4字节
     */
    private static final byte[] HEAD = new byte[]{0x55, (byte) 0xAA, 0x00, 0x00};

    /**
     * 地址 其实是使用哪种通讯方式，0x01 由于非RS485通讯的方式，故设定为固定值0x01，适配tcp/udp
     */
    private static final byte ADDRESS = 0x01;

    /**
     * 长度为2字节的保留位置 内容为0x00
     */
    private static final byte[] RETAIN_00 = new byte[]{0x00, 0x00};

    /**
     * 控制类型
     */
    private ControlOperatingCode operatingCode;

    /**
     * 帧序号 2byte 数据内容过长时，分包发送，此数值即为分包编号，从0开始。
     */
    private byte[] frameNumber;

    /**
     * 帧序号长度
     */
    public static final int FRAME_NUMBER_LENGTH = 2;

    /**
     * 数据总长度 如果分包，即为各个分包长度之和。不分包即为当前包的长度。不计算内容填充长度
     */
    private byte[] allLength;

    /**
     * 数据总长长度
     */
    public static final int ALL_LENGTH_LENGTH = 4;

    /**
     * 长度为2字节的保留位置 内容为0x01
     */
    private static final byte[] RETAIN_01 = new byte[]{0x01, 0x01};

    /**
     * 当前数据包长度 不计算填充
     */
    private byte[] thisLength;

    /**
     * 数据长度长度
     */
    public static final int THIS_LENGTH_LENGTH = 4;

    /**
     * 数据内容 具体格式参照操作码的规定使用，数据长度为0-512字节，超长分包
     */
    private byte[] content;

    /**
     * 数据内容的最大长度
     */
    public static final int MAX_BUFFER_SIZE = 512;

    /**
     * 帧尾 固定内容 4字节，标识此数据包的结束
     */
    private static final byte[] END = new byte[]{0x00, 0x00, 0x0d, 0x0a};

    /**
     * 最基础的构造方法，构造一个抽象的控制请求包
     * 注意，此构造方法不会分包，content长度超过512会抛出异常
     *
     * @param operatingCode 控制类型
     * @param frameNumber   帧序号，数组长度为2
     * @param allLength     数据总长，数组长度为4
     * @param thisLength    数据长度，数组长度为4
     * @param content       数据内容具体格式参照操作码的规定使用，数据长度为0-512字节，超长抛出异常
     * @throws StructurePacketMessageException 构造参数不合法时抛出此异常
     */
    public AbstractControlPacket(ControlOperatingCode operatingCode, byte[] frameNumber, byte[] allLength, byte[] thisLength, byte[] content) throws StructurePacketMessageException
    {
        //检查参数合法性
        checkParameter(operatingCode, frameNumber, allLength, thisLength, content);

        this.operatingCode = operatingCode;
        this.frameNumber = frameNumber;
        this.allLength = allLength;
        this.thisLength = thisLength;
        this.content = content;

        //填充构造数据this.packetMessage
        //此方法必须在前面断言通过后才能调用
        structurePacketMessage();
    }

    /**
     * 使用此方法构造一个内容长度为512以下的控制包，超长会抛出异常 <br/>
     * 此方法会自动根据content的长度<br/>
     * 使用{@link AbstractControlPacket#AbstractControlPacket(ControlOperatingCode, byte[], byte[], byte[], byte[])}进行构造
     *
     * @param operatingCode 控制类型
     * @param content       数据内容具体格式参照操作码的规定使用，数据长度为0-512字节，超长抛出异常
     * @throws StructurePacketMessageException
     */
    public AbstractControlPacket(ControlOperatingCode operatingCode, byte[] content) throws StructurePacketMessageException
    {
        this(operatingCode
                , new byte[]{0x00, 0x00}
                , ConvnetUtil.intToByteArray(content.length)
                , ConvnetUtil.intToByteArray(content.length)
                , content);
    }

    /**
     * 填充构造数据this.packetMessage<br/>
     * 只在构造方法中调用，只要子类调用{@link AbstractControlPacket#AbstractControlPacket(ControlOperatingCode, byte[], byte[], byte[], byte[])}方法<br/>
     * 即执行了此方法，执行了此方法后{@link AbstractControlPacket#getByteArray()}则可以获得数据
     */
    private void structurePacketMessage() throws StructurePacketMessageException
    {
        //除去长度不固定的content以外的byte数组长度为24，故packetMessage的长度为24 + this.content.length
        this.packetMessage = new byte[24 + this.content.length];

        System.arraycopy(HEAD, 0, this.packetMessage, 0, HEAD.length);
        this.packetMessage[4] = ADDRESS;
        System.arraycopy(RETAIN_00, 0, this.packetMessage, 14, RETAIN_00.length);
        this.packetMessage[7] = this.operatingCode.getCode();
        System.arraycopy(frameNumber, 0, this.packetMessage, 8, frameNumber.length);
        System.arraycopy(allLength, 0, this.packetMessage, 10, allLength.length);
        System.arraycopy(RETAIN_01, 0, this.packetMessage, 14, RETAIN_01.length);
        System.arraycopy(thisLength, 0, this.packetMessage, 16, thisLength.length);
        System.arraycopy(content, 0, this.packetMessage, 20, content.length);
        System.arraycopy(END, 0, this.packetMessage, 20 + this.content.length, END.length);
    }

    /**
     * 检查参数合法性
     *
     * @param operatingCode 控制类型
     * @param frameNumber   帧序号，数组长度为2 ，超出抛出异常StructurePacketMessageException
     * @param allLength     数据总长，数组长度为4，超出抛出异常StructurePacketMessageException
     * @param thisLength    数据长度，数组长度为4，超出抛出异常StructurePacketMessageException
     * @param content       数据内容具体格式参照操作码的规定使用，数据长度为0-512字节，超出抛出异常StructurePacketMessageException
     * @throws StructurePacketMessageException
     */
    private void checkParameter(ControlOperatingCode operatingCode, byte[] frameNumber, byte[] allLength, byte[] thisLength, byte[] content) throws StructurePacketMessageException
    {
        //首先断言传入的所有参数不能是null的
        try
        {
            Assert.notNull(operatingCode, "operatingCode不能为null");
            Assert.notNull(frameNumber, "frameNumber不能为null");
            Assert.notNull(allLength, "allLength不能为null");
            Assert.notNull(thisLength, "thisLength不能为null");
            Assert.notNull(content, "content不能为null");
        }
        catch (IllegalArgumentException e)
        {
            throw new StructurePacketMessageException("传入的参数均不能为null", e);
        }
        if (frameNumber.length != FRAME_NUMBER_LENGTH)
        {
            throw new StructurePacketMessageException("帧序号长度必须为2");
        }
        if (allLength.length != ALL_LENGTH_LENGTH)
        {
            throw new StructurePacketMessageException("数据总长长度必须为4");
        }
        if (thisLength.length != THIS_LENGTH_LENGTH)
        {
            throw new StructurePacketMessageException("数据长度必须为4");
        }
        if (content.length > MAX_BUFFER_SIZE)
        {
            throw new StructurePacketMessageException("数据内容长度不允许超过512，请分包");
        }

    }

    /**
     * 当调用此方法时，若{@link AbstractControlPacket#packetMessage}为null时<br/>
     * 此方法会尝试调用@{@link AbstractControlPacket#structurePacketMessage()}构造数据<br/>
     * 若构造失败，则是因为有动态数据出现为null的原因，<br/>
     * 请使用构造方法构造对象，会抛出NullPointerException
     *
     * @return 此数据包对应的自己数组
     * @see ByteArrayable#getByteArray() 参见此接口方法
     */
    @Override
    public byte[] getByteArray() throws NullPointerException
    {
        if (this.packetMessage == null)
        {
            structurePacketMessage();
        }
        System.out.println("控制指令包为：" + ConvnetUtil.ArrayToUnsignedByte(this.packetMessage));
        return this.packetMessage;
    }

    @Override
    public String toString()
    {
        return ConvnetUtil.ArrayToUnsignedByte(this.getByteArray());
    }
}
