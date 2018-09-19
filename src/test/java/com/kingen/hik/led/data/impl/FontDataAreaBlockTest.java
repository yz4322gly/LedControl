package com.kingen.hik.led.data.impl;

import com.kingen.hik.led.data.*;
import com.kingen.hik.led.packet.ByteArrayable;
import com.kingen.hik.led.packet.impl.CommonDisplayDataControlPacket;
import com.kingen.hik.util.TCPUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FontDataAreaBlockTest
{

    @Test
    public void twoActTest() throws IOException
    {
        AbstractVisibleDataAreaBlock block = FontDataAreaBlock.createFontDataAreaBlock((byte) 1, "复撒旦hi法律晒u覅首都华沙的六十度副hi水立方说的话");
        AbstractVisibleDataAreaBlock block2 = FontDataAreaBlock.createFontDataAreaBlock(
                (byte) 2
                , LedLocation.MAX_SCREEN_64_64
                , FontColor.GREEN
                , MoveAction.SUCCESSIVE_LEFT
                , (byte) 3, (byte) 1, FontSize.FONT_16, "复撒旦hi法律晒u覅首都华沙的六十度案说法的撒发射点法发斗法大赛副hi水立方说的话");

        ActBlock actBlock = new ActBlock((byte) 1, Collections.singletonList(block));
        ActBlock actBlock2 = new ActBlock((byte) 2, Collections.singletonList(block2));
        List<CommonDisplayDataControlPacket> packets = CommonDisplayDataControlPacket.createCommonDisplayDataControlPacket(Arrays.asList(actBlock, actBlock2));
        for (ByteArrayable byteArrayable : packets)
        {
            TCPUtil.sendShortTcpMessage("192.168.1.81", 10000, byteArrayable.getByteArray());
        }
    }

    @Test
    public void twoBlockTest() throws IOException
    {
        AbstractVisibleDataAreaBlock block3 = FontDataAreaBlock.createFontDataAreaBlock(
                (byte) 1
                , new LedLocation((short) 0,(short) 0,(short) 31,(short) 31)
                , FontColor.GREEN
                , MoveAction.SUCCESSIVE_LEFT
                , (byte) 3, (byte) 1, FontSize.FONT_16, "复撒旦hi法律晒u覅首都华沙的六十度案说法的撒发射点法发斗法大赛副hi水立方说的话");

        AbstractVisibleDataAreaBlock block4 = FontDataAreaBlock.createFontDataAreaBlock(
                (byte) 2
                , new LedLocation((short) 32,(short) 32,(short) 47,(short) 63)
                , FontColor.RED
                , MoveAction.SUCCESSIVE_DOWN
                , (byte) 3, (byte) 1, FontSize.FONT_16, "复asdf立方说的asdfasdf发的发大水发射点发士大夫萨芬士大夫话");

        AbstractVisibleDataAreaBlock block5 = FontDataAreaBlock.createFontDataAreaBlock(
                (byte) 3
                , new LedLocation((short) 48,(short) 32,(short) 63,(short) 63)
                , FontColor.CYAN
                , MoveAction.SUCCESSIVE_RIGHT
                , (byte) 10, (byte) 1, FontSize.FONT_16, "合法iu和fail啊打发");

        AbstractVisibleDataAreaBlock block6 = FontDataAreaBlock.createFontDataAreaBlock(
                (byte) 4
                , new LedLocation((short) 32,(short) 0,(short) 63,(short) 31)
                , FontColor.CYAN
                , MoveAction.SUCCESSIVE_RIGHT
                , (byte) 10, (byte) 1, FontSize.FONT_32, "澳高度要发噶酷似广东覅嘎斯");

        ActBlock actBlock = new ActBlock((byte) 1, Arrays.asList(block3,block4,block5,block6));

        List<CommonDisplayDataControlPacket> packets = CommonDisplayDataControlPacket.createCommonDisplayDataControlPacket(Collections.singletonList(actBlock));
        for (ByteArrayable byteArrayable : packets)
        {
            TCPUtil.sendShortTcpMessage("192.168.1.81", 10000, byteArrayable.getByteArray());
        }
    }

    @Test
    public void LedSizeTest() throws IOException
    {
        AbstractVisibleDataAreaBlock block1 = FontDataAreaBlock.createFontDataAreaBlock(
                (byte) 1
                , LedLocation.QUARTER_RIGHT_UP_32_32
                , FontColor.GREEN
                , MoveAction.SUCCESSIVE_LEFT
                , (byte) 10, (byte) 1, FontSize.FONT_16, "符合国序列hi是对规划峰会上合国序列hi是对规划峰会上邽粉丝u费德勒d's's'd'f'j啊手动阀手动阀'yu'f际法邽粉丝u费德勒d's's'd'f'j啊手动阀手动阀'yu'f际法");

        AbstractVisibleDataAreaBlock block2 = FontDataAreaBlock.createFontDataAreaBlock(
                (byte) 2
                , LedLocation.HALF_LEFT_32_64
                , FontColor.RED
                , MoveAction.SUCCESSIVE_RIGHT
                , (byte) 15, (byte) 1, FontSize.FONT_16, "符合国序列hi是对规划峰会上合国序列hi是对规划峰会上邽粉丝u费德勒d's's'd'f'j啊手动阀手动阀'yu'f际法邽粉丝u费德勒d's's'd'f'j啊手动阀手动阀'yu'f际法");

        AbstractVisibleDataAreaBlock block3 = FontDataAreaBlock.createFontDataAreaBlock(
                (byte) 3
                , LedLocation.QUARTER_RIGHT_DOWN_32_32
                , FontColor.GREEN
                , MoveAction.SUCCESSIVE_RIGHT
                , (byte) 20, (byte) 1, FontSize.FONT_16, "符合国序列hi是对规划峰会上合国序列hi是对规划峰会上邽粉丝u费德勒d's's'd'f'j啊手动阀手动阀'yu'f际法邽粉丝u费德勒d's's'd'f'j啊手动阀手动阀'yu'f际法");


        ActBlock actBlock = new ActBlock((byte) 1, Arrays.asList(block1,block2,block3));

        List<CommonDisplayDataControlPacket> packets = CommonDisplayDataControlPacket.createCommonDisplayDataControlPacket(Collections.singletonList(actBlock));
        for (ByteArrayable byteArrayable : packets)
        {
            TCPUtil.sendShortTcpMessage("192.168.1.81", 10000, byteArrayable.getByteArray());
        }

    }
}