package com.kingen.hik.led.data.impl;

import com.kingen.hik.led.data.AbstractDataAreaBlock;
import com.kingen.hik.led.packet.StructurePacketMessageException;
import com.kingen.hik.util.ConvnetUtil;

import java.io.UnsupportedEncodingException;

/**
 * 注意 3处文档未说明清楚，待修改
 * 1 使用高位优先，还是低位优先？
 * 2 保留区域长度到底是15，还是16？
 * @author guolinyuan
 */
public class VoiceAnnouncementsDataAreaBlock extends AbstractDataAreaBlock
{
    /**
     * 此处文档有误，显示偏移数据为D6-D21,长度为15
     * 但D6-D21 ,长度明明为16
     */
    private static final byte[] RETAIN15 = new byte[15];

    private VoiceAnnouncementsDataAreaBlock(byte areaNumber, DataAreaType type, byte[] content) throws StructurePacketMessageException
    {
        super(areaNumber, type, content);
    }

    private VoiceAnnouncementsDataAreaBlock(byte areaNumber, byte[] content) throws StructurePacketMessageException
    {
        this(areaNumber, DataAreaType.VOICE, content);
    }

    /**
     *
     * @param areaNumber 区域号
     * @param frequency 重复次数
     * @param strContent 语音的文本内容，支持语音参数配置，详情查看《XFS5152CE语音合成芯片用户开发指南V1.2》
     * @return
     * @throws UnsupportedEncodingException
     */
    public static VoiceAnnouncementsDataAreaBlock createVoiceAnnouncementsDataAreaBlock
            (byte areaNumber,byte frequency,String strContent) throws UnsupportedEncodingException
    {
        byte[] strBytes = strContent.getBytes("GBK");
        byte[] strLength = ConvnetUtil.intToByteArray(strBytes.length);
        byte[] bytes = new byte[20 + strBytes.length];
        bytes[15] = frequency;
        System.arraycopy(strLength,0,bytes,16,3);
        System.arraycopy(strBytes,0,bytes,20,strBytes.length);
        return new VoiceAnnouncementsDataAreaBlock(areaNumber,bytes);
    }


}
