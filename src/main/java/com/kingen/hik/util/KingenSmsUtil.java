package com.kingen.hik.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class KingenSmsUtil
{
    /**
     * http://web.1xinxi.cn/   用户名：dingji   密码：871647
     */
    private static String name = "dingji";
    private static String pwd = "29DCAB8EC008A155516A86EFCD37";
    private static StringBuffer urlStr = new StringBuffer("http://sms.1xinxi.cn/asmx/smsservice.aspx?");
    private static String sign = "鼎集智能";

    public static String sendSms(String phone, String content)
    {
        InputStream is = null;
        try
        {
            // 向StringBuffer追加用户名
            urlStr.append("name=" + name);
            // 向StringBuffer追加密码（登陆网页版，在管理中心--基本资料--接口密码，是28位的）
            urlStr.append("&pwd=" + pwd);

            // 向StringBuffer追加手机号码
            urlStr.append("&mobile=" + phone + "");
            // 向StringBuffer追加消息内容转URL标准码
            urlStr.append("&content=" + URLEncoder.encode(content, "UTF-8"));
            //追加发送时间，可为空，为空为及时发送
            urlStr.append("&stime=");
            //加签名
            urlStr.append("&sign=" + URLEncoder.encode(sign, "UTF-8"));

            //type为固定值pt  extno为扩展码，必须为数字 可为空
            urlStr.append("&type=pt&extno=");
            // 创建url对象
            URL url = new URL(urlStr.toString());
            // 打开url连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 设置url请求方式 ‘get’ 或者 ‘post’
            connection.setRequestMethod("POST");

            // 发送
            is = url.openStream();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        //转换返回值
        return convertStreamToString(is);
    }

    /**
     * 转换返回值类型为UTF-8格式.
     *
     * @param is
     * @return
     */
    public static String convertStreamToString(InputStream is)
    {
        StringBuilder sb1 = new StringBuilder();
        byte[] bytes = new byte[4096];
        int size = 0;

        try
        {
            while ((size = is.read(bytes)) > 0)
            {
                String str = new String(bytes, 0, size, "UTF-8");
                sb1.append(str);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                is.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return sb1.toString();
    }
}
