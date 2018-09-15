package com.kingen.hik.led.data;

import com.kingen.hik.led.packet.ByteArrayable;
import com.kingen.hik.led.packet.StructurePacketMessageException;
import com.kingen.hik.util.ConvnetUtil;

import java.util.List;

/**
 * 每个显示数据包含若干个此对象
 * 节目数据块，每个节目数据块包含若干个区域数据块{@link AbstractDataAreaBlock}
 *
 * @author guolinyuan
 */
public class ActBlock implements ByteArrayable
{
    /**
     * 代表此对象核心内容
     * 即此对象的同义byte数组
     */
    private byte[] bytes;

    /**
     * 节目编号
     */
    private byte actNumber;

    /**
     * 当前整个节目数据块的总长度
     * 即this.byte的长度
     */
    private int actLength;

    /**
     * 节目区域数量
     */
    private byte actQuantity;

    /**
     * 固定的保留长度18字节的空白内容
     */
    private static final byte[] RETAIN_00 = new byte[18];

    /**
     * 节目区域的属性是固定的24字节长度
     */
    private static final int ACT_ATTRIBUTE_LENGTH = 24;

    private static final int MAX_ACT_QUANTITY = 5;

    /**
     * 所有节目块的总大小
     */
    private int areaBlocksSize;

    /**
     * 此节目的所有区域数据块，在list中顺序排列
     */
    private List<AbstractDataAreaBlock> areaBlocks;

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
        System.out.println("节目数据包为：" + ConvnetUtil.ArrayToUnsignedByte(this.bytes));
        return this.bytes;
    }

    public ActBlock(byte actNumber, List<AbstractDataAreaBlock> areaBlocks) throws StructurePacketMessageException
    {
        checkParameter(actNumber, areaBlocks);

        this.actNumber = actNumber;
        this.areaBlocks = areaBlocks;
        this.actQuantity = (byte) areaBlocks.size();

        for (AbstractDataAreaBlock block : areaBlocks)
        {
            this.areaBlocksSize += block.getSize();
        }

        structurePacketMessage();
    }

    private void structurePacketMessage()
    {
        this.bytes = new byte[ACT_ATTRIBUTE_LENGTH + this.areaBlocksSize];
        this.actLength = this.bytes.length;

        byte[] sizeB = ConvnetUtil.intToByteArray(this.actLength);
        this.bytes[0] = this.actNumber;
        System.arraycopy(sizeB,0,this.bytes,1,4);
        this.bytes[5] = this.actQuantity;

        //节目数据块的下标，从24开始
        int index = 24;
        for (AbstractDataAreaBlock areaBlock : areaBlocks)
        {
            byte[] blockArray = areaBlock.getByteArray();
            System.arraycopy(blockArray,0,this.bytes,index,blockArray.length);
            index += blockArray.length;
        }
    }

    private void checkParameter(byte actNumber, List<AbstractDataAreaBlock> areaBlocks) throws StructurePacketMessageException
    {
        if (actNumber < 0)
        {
            throw new StructurePacketMessageException("节目号从1开始");
        }
        if (areaBlocks == null)
        {
            throw new StructurePacketMessageException("areaBlocks不能为null");
        }
        else if (areaBlocks.size() < 1)
        {
            throw new StructurePacketMessageException("areaBlocks不能是空列表");
        }
        else if (areaBlocks.size() > MAX_ACT_QUANTITY)
        {
            throw new StructurePacketMessageException("areaBlocks列表长度不能超过5");
        }
        else
        {
            int voiceNumber = 0;
            for (AbstractDataAreaBlock block : areaBlocks)
            {
                if (block.getType() == AbstractDataAreaBlock.DataAreaType.VOICE)
                {
                    voiceNumber++;
                }
            }
            if (voiceNumber > 1)
            {
                throw new StructurePacketMessageException("每一个节目最多只能有一个语音播报数据区域");
            }
        }


    }
}
