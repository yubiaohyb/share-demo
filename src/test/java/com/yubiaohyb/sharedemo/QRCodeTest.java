package com.yubiaohyb.sharedemo;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * 人若志趣不远，心不在焉，虽学不成。
 * <p>
 * description  -  functionDescrption
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2019-06-02 21:57
 */
public class QRCodeTest {


    // 图片宽度的一半
    private static final int IMAGE_WIDTH = 300;
    private static final int IMAGE_HEIGHT = 300;
    private static final int IMAGE_HALF_WIDTH = IMAGE_WIDTH / 2;
    private static final int FRAME_WIDTH = 2;
    // 二维码写码器
    private static MultiFormatWriter mutiWriter = new MultiFormatWriter();

    public static void encode(String content, int width, int height, String srcImagePath, String destImagePath) {
        try {
            //生成图片文件
            ImageIO.write(genBarcode(content, width, height, srcImagePath), "jpg", new File(destImagePath));
            System.out.println("二维码生成成功！");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到BufferedImage
     *
     * @param content 二维码显示的文本
     * @param width   二维码的宽度
     * @param height  二维码的高度
     * @return
     * @throws WriterException
     * @throws IOException
     */
    private static BufferedImage genBarcode(String content, int width,
                                            int height, String srcImagePath) throws WriterException,
            IOException {
        // 读取源图像
        BufferedImage scaleImage = scale(srcImagePath, IMAGE_WIDTH,
                IMAGE_HEIGHT, true);
        int[][] srcPixels = new int[IMAGE_WIDTH][IMAGE_HEIGHT];
        for (int i = 0; i < scaleImage.getWidth(); i++) {
            for (int j = 0; j < scaleImage.getHeight(); j++) {
                srcPixels[i][j] = scaleImage.getRGB(i, j);//图片的像素点
            }
        }
        //编码
        Map hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        //设置容错等级，在这里我们使用M级别
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        // 生成二维码
        BitMatrix matrix = mutiWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);

        // 二维矩阵转为一维像素数组
        int halfW = matrix.getWidth() / 2;
        int halfH = matrix.getHeight() / 2;
        int[] pixels = new int[width * height];

        for (int y = 0; y < matrix.getHeight(); y++) {
            for (int x = 0; x < matrix.getWidth(); x++) {

                pixels[y * width + x] = srcPixels[x][y];


            }
        }

        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        image.getRaster().setDataElements(0, 0, width, height, pixels);

        return image;
    }

    /**
     * 把传入的原始图像按高度和宽度进行缩放，生成符合要求的图标
     *
     * @param srcImageFile 源文件地址
     * @param height       目标高度
     * @param width        目标宽度
     * @param hasFiller    比例不对时是否需要补白：true为补白; false为不补白;
     * @throws IOException
     */
    private static BufferedImage scale(String srcImageFile, int height, int width, boolean hasFiller) throws IOException {
        double ratio = 0.0; // 缩放比例
        File file = new File(srcImageFile);
        BufferedImage srcImage = ImageIO.read(file);
        Image destImage = srcImage.getScaledInstance(width, height,
                BufferedImage.SCALE_SMOOTH);
        // 计算比例
        if ((srcImage.getHeight() > height) || (srcImage.getWidth() > width)) {
            if (srcImage.getHeight() > srcImage.getWidth()) {
                ratio = (new Integer(height)).doubleValue()
                        / srcImage.getHeight();
            } else {
                ratio = (new Integer(width)).doubleValue()
                        / srcImage.getWidth();
            }
            AffineTransformOp op = new AffineTransformOp(
                    AffineTransform.getScaleInstance(ratio, ratio), null);
            destImage = op.filter(srcImage, null);
        }
        if (hasFiller) {// 补白
            BufferedImage image = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D graphic = image.createGraphics();
            graphic.setColor(Color.white);
            graphic.fillRect(0, 0, width, height);
            if (width == destImage.getWidth(null))
                graphic.drawImage(destImage, 0,
                        (height - destImage.getHeight(null)) / 2,
                        destImage.getWidth(null), destImage.getHeight(null),
                        Color.white, null);//画图片
            else
                graphic.drawImage(destImage,
                        (width - destImage.getWidth(null)) / 2, 0,
                        destImage.getWidth(null), destImage.getHeight(null),
                        Color.white, null);
            graphic.dispose();
            destImage = image;
        }
        return (BufferedImage) destImage;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String filePath = "/Users/huangyubiao/Downloads";
        String fileName = "zxing.png";
        JSONObject json = new JSONObject();
        json.put(
                "zxing",
                "https://github.com/zxing/zxing/tree/zxing-3.0.0/javase/src/main/java/com/google/zxing");
        json.put("author", "shihy");
        //String content = json.toJSONString();// 内容
        String content = "https://github.com/zxing/zxing/tree/zxing-3.0.0/javase/src/main/java/com/google/zxing";// 内容
        int width = 300; // 图像宽度
        int height = 300; // 图像高度
        String format = "png";// 图像类型
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix bitMatrix = null;// 生成矩阵
        try {
            bitMatrix = new MultiFormatWriter().encode(content,
                    BarcodeFormat.QR_CODE, width, height, hints);

//            BufferedImage scaleImage = scale("/Users/huangyubiao/Downloads/4.png", IMAGE_WIDTH,
//                    IMAGE_HEIGHT, true);
            BufferedImage scaleImage = ImageIO.read(new File("/Users/huangyubiao/Downloads/4.png"));
            int[][] srcPixels = new int[IMAGE_WIDTH][IMAGE_HEIGHT];
            for (int i = 0; i < scaleImage.getWidth(); i++) {
                for (int j = 0; j < scaleImage.getHeight(); j++) {
                    srcPixels[i][j] = scaleImage.getRGB(i, j);//图片的像素点
                }
            }
            Color color = new Color(255, 255, 255);
            Color colr = new Color(175, 100, 175);

            BufferedImage image = new BufferedImage(width, height, 12);
            int[] rowPixels = new int[width];
            BitArray row = new BitArray(width);

            for (int y = 0; y < height; ++y) {
                row = bitMatrix.getRow(y, row);

                for (int x = 0; x < width; ++x) {

                    if (row.get(x)) {
//                        rowPixels[x] = srcPixels[x][y];
//                        rowPixels[x] = scaleImage.getRGB(x, y);

                        System.out.println(1);
                        scaleImage.setRGB(x, y, 0);
                        rowPixels[x] = scaleImage.getRGB(x, y);
                    } else {
                        rowPixels[x] = colr.getRGB();
                        if (x < width / 2) {
//                            Raster tile = scaleImage.getTile(x, y);
//                            tile.createCompatibleWritableRaster(x,y).setPixel(x, y, new int[]{colr.getRGB()});
//                            scaleImage.setRGB(x, y, 0);
                        } else {
//                            scaleImage.setRGB(x, y, 0);
//                            scaleImage.releaseWritableTile(x, y);
                        }

                    }

                }
                image.setRGB(0, y, width, 1, rowPixels, 0, width);

            }


            Path path = FileSystems.getDefault().getPath(filePath, fileName);
//            MatrixToImageWriter.writeToPath(bitMatrix, format, path, new MatrixToImageConfig(color.getRGB(), -1));// 输出图像
            ImageIO.write(image, format, path.toFile());
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("输出成功.");

    }
}
