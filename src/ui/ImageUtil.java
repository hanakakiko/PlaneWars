package ui;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

//对图片进行操作的工具类
public class ImageUtil {
    //根据路径获取图片
    public static BufferedImage getImage(String path){
        try {
            BufferedImage image;
            image = ImageIO.read(com.sun.imageio.plugins.common.ImageUtil.class.getResource(path));
            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    };
}
