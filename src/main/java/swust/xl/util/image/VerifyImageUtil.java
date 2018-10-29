package swust.xl.util.image;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.springframework.stereotype.Component;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 图片处理工具类
 * 
 * @author xuLiang
 * @since 0.0.1
 */
@SuppressWarnings("restriction")
@Component
public class VerifyImageUtil {

	/**
	 * 用来裁剪到滑动的方块
	 */
	public BufferedImage getMarkImage(BufferedImage image, int x, int y, int length, int width) throws IOException {
		InputStream is = null;
		ImageInputStream iis = null;
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(image, "png", os);
			is = new ByteArrayInputStream(os.toByteArray());
			Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName("png");
			ImageReader reader = it.next();
			iis = ImageIO.createImageInputStream(is);
			reader.setInput(iis, true);
			ImageReadParam param = reader.getDefaultReadParam();
			Rectangle rectangle = new Rectangle(x, y, length, width);
			param.setSourceRegion(rectangle);
			BufferedImage bi = reader.read(0, param);
			return bi;
		} finally {
			if (is != null)
				is.close();
			if (iis != null)
				iis.close();
		}

	}

	/**
	 * 被抠滑块的坐标
	 * 
	 * @param targetLength
	 *            原图的长度
	 * @param targetWidth
	 *            原图的宽度
	 * @param x
	 *            裁剪区域的x坐标
	 * @param y
	 *            裁剪区域的y坐标
	 * @param length
	 *            抠图的长度
	 * @param width
	 *            抠图的宽度
	 * @return 被抠滑块的坐标
	 */
	public int[][] getCutAreaData(int targetLength, int targetWidth, int x, int y, int length, int width) {
		int[][] data = new int[targetLength][targetWidth];
		for (int i = 0; i < targetLength; i++) {
			for (int j = 0; j < targetWidth; j++) {
				if (i < x + length && i >= x && j < y + width && j > y) {
					data[i][j] = 1;
				} else {
					data[i][j] = 0;
				}
			}
		}
		return data;
	}

	/**
	 * 得到抠掉滑块后的图并加阴影
	 * 
	 * @param oriImage
	 * @param templateImage
	 * 
	 */
	public static void cutByTemplate(BufferedImage oriImage, int[][] templateImage) {

		for (int i = 0; i < oriImage.getWidth(); i++) {
			for (int j = 0; j < oriImage.getHeight(); j++) {
				int rgb = templateImage[i][j];
				// 原图中对应位置变色处理

				// int rgb_ori = oriImage.getRGB(i, j);

				if (rgb == 1) {
					// 颜色处理
					/**
					 * int r = (0xff & rgb_ori); int g = (0xff & (rgb_ori >> 8)); int b = (0xff &
					 * (rgb_ori >> 16)); int Gray = (r * 2 + g * 5 + b * 1) >> 3;
					 * 
					 */

					// 原图对应位置颜色变化

					oriImage.setRGB(i, j, 99999999);
				}
			}
		}
	}

	/**
	 * 图片转Base64
	 * 
	 * @param image
	 * @return Base64
	 * @throws Exception
	 */
	public String imageToBase64(BufferedImage image) throws Exception {
		byte[] imagedata = null;
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		ImageIO.write(image, "png", bao);
		imagedata = bao.toByteArray();
		BASE64Encoder encoder = new BASE64Encoder();
		String BASE64IMAGE = encoder.encodeBuffer(imagedata).trim();
		BASE64IMAGE = BASE64IMAGE.replaceAll("\n", "").replaceAll("\r", "");// 删除 \r\n
		return BASE64IMAGE;
	}

	/**
	 * Base64转图片
	 * 
	 * @param base64String
	 * @return image
	 * @throws Exception
	 */
	public BufferedImage base64StringToImage(String base64String) {
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] bytes1 = decoder.decodeBuffer(base64String);
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
			return ImageIO.read(bais);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}