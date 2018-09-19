package com.kingen.hik.led.data;

import com.kingen.hik.led.data.impl.FontDataAreaBlock;
import com.kingen.hik.led.data.impl.TimeData;
import com.kingen.hik.led.data.impl.TimeDataAreaBlock;
import com.kingen.hik.led.data.impl.VoiceAnnouncementsDataAreaBlock;
import com.kingen.hik.led.packet.ByteArrayable;
import com.kingen.hik.led.packet.impl.CommonDisplayDataControlPacket;
import com.kingen.hik.util.TCPUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.kingen.hik.led.data.FontSize.FONT_16;
import static com.kingen.hik.led.data.FontSize.FONT_32;


public class ActBlockTest
{
    @Test
    public void test1() throws IOException
    {
        AbstractDataAreaBlock block = VoiceAnnouncementsDataAreaBlock.createVoiceAnnouncementsDataAreaBlock((byte)1,(byte)1,"[v10]yqwergh");
        AbstractDataAreaBlock block2 = FontDataAreaBlock.createFontDataAreaBlock((byte)1,"adfasdfasdf");
        AbstractDataAreaBlock block3 = FontDataAreaBlock.createFontDataAreaBlock((byte)1,LedLocation.MAX_SCREEN_64_64, FontColor.GREEN, MoveAction.SUCCESSIVE_RIGHT,(byte) 1,(byte) 1, FONT_32,"1asdgfg发噶似的语法关于sdfgsdf山豆根士大夫公司股份反倒是反馈iu风光不与率u一份不是故意犯规送出野人谷好爽快度分红鬼地方hi开国皇帝苏菲韩国i上海分公司看i回归书法韩国i苦涩的发挥的高发u史蒂夫噶士大夫啊厚度覅上的功夫伊萨");
        AbstractDataAreaBlock block4 = FontDataAreaBlock.createFontDataAreaBlock((byte)1,LedLocation.MAX_SCREEN_64_64, FontColor.BLUE, MoveAction.SUCCESSIVE_TOP,(byte) 1,(byte) 1, FONT_16,"2asdgfg发噶似的语法关于sdfgsdf山豆根士大夫公司股份反倒是反馈iu风光不与率u一份不是故意犯规送出野人谷好爽快度分红鬼地方hi开国皇帝苏菲韩国i上海分公司看i回归书法韩国i苦涩的发挥的高发u史蒂夫噶士大夫啊厚度覅上的功夫伊萨");
        AbstractDataAreaBlock block5 = FontDataAreaBlock.createFontDataAreaBlock((byte)1,LedLocation.MAX_SCREEN_64_64, FontColor.CYAN, MoveAction.SUCCESSIVE_DOWN,(byte) 5,(byte) 1,FONT_16,"3asdgfg发噶似的语法关于sdfgsdf山豆根士大夫公司股份反倒是反馈iu风光不与率u一份不是故意犯规送出野人谷好爽快度分红鬼地方hi开国皇帝苏菲韩国i上海分公司看i回归书法韩国i苦涩的发挥的高发u史蒂夫噶士大夫啊厚度覅上的功夫伊萨");
        AbstractDataAreaBlock block6 = FontDataAreaBlock.createFontDataAreaBlock((byte)1,LedLocation.MAX_SCREEN_64_64, FontColor.VIOLET, MoveAction.TWINKLE,(byte) 1,(byte) 1, FONT_16,"4asdgfg发噶似的语法关于sdfgsdf山豆根士大夫公司股份反倒是反馈iu风光不与率u一份不是故意犯规送出野人谷好爽快度分红鬼地方hi开国皇帝苏菲韩国i上海分公司看i回归书法韩国i苦涩的发挥的高发u史蒂夫噶士大夫啊厚度覅上的功夫伊萨");
        AbstractDataAreaBlock block7 = FontDataAreaBlock.createFontDataAreaBlock((byte)1,"5asdgfg发噶似的语法关于sdfgsdf山豆根士大夫公司股份反倒是反馈iu风光不与率u一份不是故意犯规送出野人谷好爽快度分红鬼地方hi开国皇帝苏菲韩国i上海分公司看i回归书法韩国i苦涩的发挥的高发u史蒂夫噶士大夫啊厚度覅上的功夫伊萨");



        ActBlock actBlock = new ActBlock((byte) 1, Collections.singletonList(block));
        ActBlock actBlock2 = new ActBlock((byte) 2, Collections.singletonList(block2));
        ActBlock actBlock3 = new ActBlock((byte) 3, Collections.singletonList(block3));
        ActBlock actBlock4 = new ActBlock((byte) 4, Collections.singletonList(block4));
        ActBlock actBlock5 = new ActBlock((byte) 5, Collections.singletonList(block5));
        ActBlock actBlock6 = new ActBlock((byte) 6, Collections.singletonList(block6));
        ActBlock actBlock7 = new ActBlock((byte) 7, Collections.singletonList(block7));

        List<CommonDisplayDataControlPacket> packets = CommonDisplayDataControlPacket.createCommonDisplayDataControlPacket(Arrays.asList(actBlock,actBlock2,actBlock3,actBlock4,actBlock5,actBlock6,actBlock7));

        for (CommonDisplayDataControlPacket packet :packets)
        {
            TCPUtil.sendShortTcpMessage("192.168.1.81", 10000, packet.getByteArray());
        }
    }

    @Test
    public void twoBlockTest() throws IOException
    {
        AbstractVisibleDataAreaBlock block1 = TimeDataAreaBlock.createTimeDataAreaBlock((byte) 1
                , new LedLocation((short) 0,(short) 0,(short)63,(short) 15)
                , FontColor.RED
                , MoveAction.SUCCESSIVE_LEFT
                , (byte) 16, (byte) 1, FontSize.FONT_16, new TimeData(TimeData.TimeUpdateType.SECOND
                        , TimeData.HourType.H24
                        , TimeData.TimeZoneType.BEIJING_EAST_8
                        , (byte) 0
                        , (byte) 0
                        , TimeData.MODEL_02)
        );
        AbstractVisibleDataAreaBlock block2 = FontDataAreaBlock.createFontDataAreaBlock(
                (byte) 2
                , new LedLocation((short) 0,(short) 18,(short) 63,(short) 63)
                , FontColor.RED
                , MoveAction.STOP
                , (byte) 16, (byte) 1, FontSize.FONT_16, "智慧停车 欢迎您!");


        ActBlock actBlock = new ActBlock((byte) 1, Arrays.asList(block1,block2));

        List<CommonDisplayDataControlPacket> packets = CommonDisplayDataControlPacket.createCommonDisplayDataControlPacket(Collections.singletonList(actBlock));
        for (ByteArrayable byteArrayable : packets)
        {
            TCPUtil.sendShortTcpMessage("192.168.1.81", 10000, byteArrayable.getByteArray());
        }
    }

}

