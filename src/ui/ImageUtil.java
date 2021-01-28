package ui;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageUtil {
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
