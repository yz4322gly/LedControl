package com.kingen.hik.led.packet.impl;

import com.kingen.hik.led.data.ActBlock;
import com.kingen.hik.led.packet.AbstractControlPacket;
import com.kingen.hik.led.packet.ControlOperatingCode;
import com.kingen.hik.led.packet.StructurePacketMessageException;
import com.kingen.hik.util.ArrayUtil;
import com.kingen.hik.util.ConvnetUtil;
import org.springframework.lang.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author guolinyuan
 */
public class CommonDisplayDataControlPacket extends AbstractControlPacket
{
    private static Logger logger = LoggerFactory.getLogger(CommonDisplayDataControlPacket.class);

    private CommonDisplayDataControlPacket(byte[] frameNumber, byte[] allLength, byte[] thisLength, byte[] content) throws StructurePacketMessageException
    {
        super(ControlOperatingCode.COMMON_DISPLAY_DATA, frameNumber, allLength, thisLength, content);
    }

    private CommonDisplayDataControlPacket(byte[] content) throws StructurePacketMessageException
    {
        super(ControlOperatingCode.COMMON_DISPLAY_DATA, content);
    }

    public static List<CommonDisplayDataControlPacket> createCommonDisplayDataControlPacket(@Nullable List<ActBlock> actBlocks)
    {
        //如果传入的节目组为null，或者没有节目，返回一个空的内容，将节目数量标记为0
        if (actBlocks == null || actBlocks.size() == 0)
        {
            return Collections.singletonList(new CommonDisplayDataControlPacket(new byte[]{0}));
        }
        else
        {
            byte[] answer = new byte[]{};
            for (ActBlock actBlock :actBlocks)
            {
                answer = ArrayUtil.byteArrayAdd(answer,actBlock.getByteArray());
            }
            answer = ArrayUtil.byteArrayAdd((byte)actBlocks.size(),answer);

            return unpack(answer,512);
        }
    }

    /**
     * 按照指定的一个包的大小，
     * 将数组拆为分（如果需要的话）
     * @param bytes 内容数组
     * @param onePacketSize 一个包的最大长度
     * @return 返回指定的结果
     */
    private static List<CommonDisplayDataControlPacket> unpack(byte[] bytes,int onePacketSize)
    {
        //完整长度的包有多少个
        int wholePacketNumber = bytes.length / onePacketSize;

        //不完整长度的包的长度为多少
        int remainderPacketLength = bytes.length % onePacketSize;

        logger.debug("长度超过" + onePacketSize + "预计分包" + (wholePacketNumber + 1) + "个，其中完整包" + wholePacketNumber + "个，不完整包长度为" + remainderPacketLength);

        //总包数量始终为完整长度包+1
        List<CommonDisplayDataControlPacket> list = new ArrayList<>(wholePacketNumber + 1);


        for (int i = 0 ; i < wholePacketNumber ; i++)
        {
            byte[] now = new byte[onePacketSize];
            System.arraycopy(bytes,i * onePacketSize,now,0,onePacketSize);
            CommonDisplayDataControlPacket packet = new CommonDisplayDataControlPacket(
                    ConvnetUtil.shortToByteArray((short) i),
                    ConvnetUtil.intToByteArray(bytes.length),
                    ConvnetUtil.intToByteArray(onePacketSize),now);
            list.add(i,packet);
            logger.debug("分帧序号第" + i + "包，总长度为" +  bytes.length + "，此包长" + onePacketSize);
        }

        byte[] now = new byte[remainderPacketLength];
        System.arraycopy(bytes,wholePacketNumber * onePacketSize,now,0,remainderPacketLength);
        CommonDisplayDataControlPacket remainderPacket = new CommonDisplayDataControlPacket(
                ConvnetUtil.shortToByteArray((short) wholePacketNumber),
                ConvnetUtil.intToByteArray(bytes.length),
                ConvnetUtil.intToByteArray(remainderPacketLength),now);
        list.add(remainderPacket);
        logger.debug("分帧序号第" + wholePacketNumber + "包，总长度为" +  bytes.length + "，此包长" + remainderPacketLength);
        logger.debug("分包完成" + list);
        return list;
    }
}
