package com.kingen.hik.led.data;

/**
 * 可视的内容，实现此接口
 * @author guolinyuan
 */
public interface Visible
{
    /**
     * 获得表示此区域块的位置信息
     * @return 位置信息的表示
     */
    LedLocation getLedLocation();
}
