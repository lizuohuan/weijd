package com.magic.weijd.util;


import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifDirectory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Eric Xie on 2017/4/6 0006.
 */
public class RotateImage {


    public static int getRotateAngleForPhoto(String filePath)
    {
        File file = new File(filePath);

        int angle = 0;
        try
        {
            Metadata metadata = JpegMetadataReader.readMetadata(file);
            Directory directory = metadata.getDirectory(ExifDirectory.class);
            if (directory.containsTag(274))
            {
                int orientation = directory.getInt(274);

                if (6 == orientation)
                {
                    angle = 90;
                } else if (3 == orientation)
                {
                    angle = 180;
                } else if (8 == orientation)
                {
                    angle = 270;
                }
            }
        } catch (JpegProcessingException e) {
            e.printStackTrace();
        } catch (MetadataException e) {
            e.printStackTrace();
        }

        return angle;
    }

    public static String rotatePhonePhoto(String fullPath, int angel)
    {
        try
        {
            BufferedImage src = ImageIO.read(new File(fullPath));

            int src_width = src.getWidth(null);
            int src_height = src.getHeight(null);

            Rectangle rect_des = CalcRotatedSize(new Rectangle(new Dimension(src_width, src_height)), angel);

            BufferedImage res = new BufferedImage(rect_des.width, rect_des.height, 1);
            Graphics2D g2 = res.createGraphics();

            g2.translate((rect_des.width - src_width) / 2,
                    (rect_des.height - src_height) / 2);
            g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);

            g2.drawImage(src, null, null);

            ImageIO.write(res, "jpg", new File(fullPath));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return fullPath;
    }

    public static Rectangle CalcRotatedSize(Rectangle src, int angel) {
        if (angel >= 90) {
            if (angel / 90 % 2 == 1) {
                int temp = src.height;
                src.height = src.width;
                src.width = temp;
            }
            angel %= 90;
        }

        double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2.0D;
        double len = 2.0D * Math.sin(Math.toRadians(angel) / 2.0D) * r;
        double angel_alpha = (3.141592653589793D - Math.toRadians(angel)) / 2.0D;
        double angel_dalta_width = Math.atan(src.height / src.width);
        double angel_dalta_height = Math.atan(src.width / src.height);

        int len_dalta_width = (int)(len * Math.cos(3.141592653589793D - angel_alpha -
                angel_dalta_width));
        int len_dalta_height = (int)(len * Math.cos(3.141592653589793D - angel_alpha -
                angel_dalta_height));
        int des_width = src.width + len_dalta_width * 2;
        int des_height = src.height + len_dalta_height * 2;
        return new Rectangle(new Dimension(des_width, des_height));
    }

    public static void main(String[] args) {
        int angle = getRotateAngleForPhoto("c:\\temp\\123.jpg");
        rotatePhonePhoto("c:\\temp\\123.jpg", angle);
        System.out.print("成功" + angle);
    }

}
