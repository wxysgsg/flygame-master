package com.rxy.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ImageLoaderUtil {
    public static BufferedImage load(String imgName){
        BufferedImage image= null;
        try {
            URL url = ImageLoaderUtil.class.getResource("/image/"+imgName);
            image = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
