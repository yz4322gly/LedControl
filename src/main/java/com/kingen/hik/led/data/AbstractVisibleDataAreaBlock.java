package com.kingen.hik.led.data;


import com.kingen.hik.led.packet.StructurePacketMessageException;

/**
 * 抽象区域数据块，是显示在屏幕上的,字符内码区域，时间节目区域，点阵位图区域的抽象表示<br/>
 * 每个节目 由 一个节目属性，和若干（区域数据块）此类实现构成。
 * 每个常规数据 由 一个节目个数（1byte） 和 若干个节目构成
 *
 * @author guolinyuan
 */
public abstract class AbstractVisibleDataAreaBlock  extends AbstractDataAreaBlock implements Visible
{
    public AbstractVisibleDataAreaBlock(byte areaNumber, DataAreaType type, byte[] content) throws StructurePacketMessageException
    {
        super(areaNumber, type, content);
    }

    /**
     * 检查led显示的区域是否是16的整数倍
     * 以及区域是否能容纳下至少一个指定的字符大小
     * 当显示的文字内容需要有文字出现的时候，必须执行此检查
     * @param ledLocation led位置
     * @param fontSize 字体大小
     * @throws StructurePacketMessageException
     */
    public static void checkFontLedLocation(LedLocation ledLocation, FontSize fontSize) throws StructurePacketMessageException
    {
        final int width =  ledLocation.getLowerRightX() - ledLocation.getTopLeftX() + 1;
        final int height = ledLocation.getLowerRightY() - ledLocation.getTopLeftY() + 1;

        //支持的最小字符大小为16*16字符
        if (width % FontSize.FONT_16.getCode() > 0)
        {
            throw new StructurePacketMessageException("横向宽度必须是16的整数倍！");
        }

//        if (height % FontSize.FONT_16.getCode() > 0)
//        {
//            throw new StructurePacketMessageException("纵向宽度必须是16的整数倍！");
//        }
        if (width < fontSize.getCode())
        {
            throw new StructurePacketMessageException("横向宽度"+width+"不足以容纳至少一个宽度为" + fontSize.getCode() + "的字符");
        }
        if (height < fontSize.getCode())
        {
            throw new StructurePacketMessageException("纵向高度"+height+"不足以容纳至少一个高度为" + fontSize.getCode() + "的字符");
        }
    }
}
