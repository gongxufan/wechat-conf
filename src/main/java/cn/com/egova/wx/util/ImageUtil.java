package cn.com.egova.wx.util;

import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by gongxufan on 2016/8/24.
 */
public class ImageUtil {
    /**
     * 将网络图片进行Base64位编码
     *
     * @param imageUrl 图片的url路径，如http://.....xx.jpg
     * @return
     */
    public static String encodeImgageToBase64(String imageUrl) throws IOException {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        ByteArrayOutputStream outputStream = null;
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(new URL(imageUrl));
            outputStream = new ByteArrayOutputStream();
            //获取文件类型
            String type = "png";
            int c = imageUrl.indexOf("wx_fmt=");
            if( c != -1){
                type = imageUrl.substring(c+7);
            }
            ImageIO.write(bufferedImage, type, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            if(outputStream != null)
                outputStream.close();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        // 返回Base64编码过的字节数组字符串
        return "data:image/jpeg;base64,"+encoder.encode(outputStream.toByteArray());
    }
}
