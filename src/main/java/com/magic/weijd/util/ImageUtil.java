package com.magic.weijd.util;
/**
 * Created by admin on 2017/9/7.
 */

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.*;
import java.util.Iterator;

import java.awt.Image;

import javax.imageio.ImageIO;
/**
 * 图片工具类
 *
 * @author lzh
 * @create 2017-09-07 21:23
 **/
public class ImageUtil {


    /**
     * 压缩图片,通过压缩图片质量，保持原图大小
     * @param imageByte 由file转换的byte[]
     * @return byte[]
     */
    public static byte[] compressPic(byte[] imageByte) {
        byte[] inByte = null;
        try {
            ByteArrayInputStream byteInput = new ByteArrayInputStream(imageByte);
            Image img = ImageIO.read(byteInput);
            float newWidth = img.getWidth(null);
            float newHeight = img.getHeight(null);


            Image image = img.getScaledInstance((int) newWidth,(int) newHeight, Image.SCALE_SMOOTH);


            // 缩放图像
            BufferedImage tag = new BufferedImage((int) newWidth,
                    (int) newHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = tag.createGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            ByteArrayOutputStream out = new ByteArrayOutputStream(imageByte.length);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            JPEGEncodeParam jep=JPEGCodec.getDefaultJPEGEncodeParam(tag);
            /* 压缩质量 */
            jep.setQuality((float)0.6, true);
            encoder.encode(tag, jep);
            inByte = out.toByteArray();
            out.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return inByte;
    }


    /**
     * @Title: compressPicByQuality
     * @Description: 压缩图片,通过压缩图片质量，保持原图大小
     * @param  quality：0-1
     * @return byte[]
     * @throws
     */
    public static byte[] compressPicByQuality(File file, float quality) {
        byte[] inByte = null;
        try {
//            ByteArrayInputStream byteInput = new ByteArrayInputStream(imgByte);
            BufferedImage image = ImageIO.read(file);

            // 如果图片空，返回空
            if (image == null) {
                return null;
            }
            // 得到指定Format图片的writer
            Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpeg");// 得到迭代器
            ImageWriter writer = iter.next(); // 得到writer

            // 得到指定writer的输出参数设置(ImageWriteParam )
            ImageWriteParam iwp = writer.getDefaultWriteParam();
            iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT); // 设置可否压缩
//            iwp.setCompressionQuality(quality); // 设置压缩质量参数

//            iwp.setProgressiveMode(ImageWriteParam.MODE_DISABLED);

            ColorModel colorModel = ColorModel.getRGBdefault();
            // 指定压缩时使用的色彩模式
//            iwp.setDestinationType(new javax.imageio.ImageTypeSpecifier(colorModel,
//                    colorModel.createCompatibleSampleModel(32, 32)));

            // 开始打包图片，写入byte[]
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); // 取得内存输出流
            IIOImage iIamge = new IIOImage(image, null, null);

            // 此处因为ImageWriter中用来接收write信息的output要求必须是ImageOutput
            // 通过ImageIo中的静态方法，得到byteArrayOutputStream的ImageOutput
            writer.setOutput(ImageIO
                    .createImageOutputStream(byteArrayOutputStream));
            writer.write(null, iIamge, iwp);
            inByte = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            System.out.println("write errro");
            e.printStackTrace();
        }
        return inByte;
    }


    public static BufferedImage toBufferedImage(Image image, int type) {
        int w = image.getWidth(null);
        int h = image.getHeight(null);
        BufferedImage result = new BufferedImage(w, h, type);
        Graphics2D g = result.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return result;
    }

    public static void main(String[] args) throws IOException {
        File file = new File("E:\\1.png");
//        InputStream input = new FileInputStream(file);0.5
//        byte[] byt = new byte[input.available()];
        byte[] byt = File2byte("E:\\1.png");
//        byte[] byt = compressPicByQuality(file,Float.valueOf("1") );
        byte2File(compressPic(byt),"E:\\","5.png");


    }

    /**
     * 将byte转为file
     * @param buf byte[]
     * @param filePath 文件路径
     * @param fileName 文件名
     */
    public static void byte2File(byte[] buf, String filePath, String fileName)
    {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try
        {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory())
            {
                dir.mkdirs();
            }
            file = new File(filePath + File.separator + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(buf);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (bos != null)
            {
                try
                {
                    bos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (fos != null)
            {
                try
                {
                    fos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将file转换为byte
     * @param filePath 文件路径
     * @return
     */
    public static byte[] File2byte(String filePath)
    {
        byte[] buffer = null;
        try
        {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1)
            {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return buffer;
    }

}
