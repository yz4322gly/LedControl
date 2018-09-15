package com.kingen.hik.led.packet;

/**
 * 将自身的包含的信息转化为byte数组
 * 实现此接口，即实现此接口的对象可以成为led数据包的一部分
 *
 * @author guolinyuan
 */
public interface ByteArrayable
{
    /**
     * 获取表示此数据包的byte数组内容，
     * 即提供给网络所发送的数据内容
     * @return 数据包的byte数组内容
     */
    byte[] getByteArray();


}
