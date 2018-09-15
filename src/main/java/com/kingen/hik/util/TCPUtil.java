package com.kingen.hik.util;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author guolinyuan
 */
public class TCPUtil
{
    /**
     * 和指定的TCP服务器建立短链接，超时未链接，超时异常
     * 发送一个指定的二进制流过去，然后期待接收到一个二进制流数据
     * 如果不能收到，即在超时时间过后，返回一个TimeOutExc
     * 收到数据后，返回，随即断开此链接，注意，由于每次都需要建立一次链接
     * 此方法得资源消耗略大
     * @return
     */
    public static byte[] sendShortTcpMessage(String host,int port,byte[] message) throws IOException
    {
        Socket socket = new Socket(host,port);
        byte[] back = new byte[512];
        if (socket.isConnected())
        {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(message);
            outputStream.flush();
            System.out.println("发送指令："+ConvnetUtil.ArrayToUnsignedByte(message));
            int i = inputStream.read(back);
            System.out.println("收到回复："+ConvnetUtil.ArrayToUnsignedByte(back));
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(socket);
        }
        return back;
    }
}
