package swust.xl.util.image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Component;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 图片处理工具类
 * 
 * @author xuLiang
 * @since 1.0.0
 */
@Component
public class ImageUtil {

	/**
	 * 用来裁剪到滑动的方块
	 * 
	 * @param bufferedImage
	 *            原图
	 * @param x
	 *            裁出来的图的X坐标
	 * @param y
	 *            裁出来的图的Y坐标
	 * @param width
	 *            裁出来的图的宽
	 * @param height裁出来的图的高
	 */
	public static BufferedImage getCuttedImage(BufferedImage bufferedImage, int x, int y, int width, int height) {
		BufferedImage bi = bufferedImage.getSubimage(x, y, width, height);
		return bi;
	}

	/**
	 * 被抠滑块的坐标集合
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
	 * @author xuLiang
	 * @since 1.0.0
	 */
	public static int[][] getCutAreaData(int targetLength, int targetWidth, int x, int y, int length, int width) {
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
	 * 得到抠掉滑块并加阴影后的图
	 * 
	 * @param oriImage
	 * @param templateImage
	 * @author xuLiang
	 * @since 1.0.0
	 */
	public static void cutByTemplate(BufferedImage oriImage, int[][] templateImage) {
		for (int i = 0; i < oriImage.getWidth(); i++) {
			for (int j = 0; j < oriImage.getHeight(); j++) {
				int rgb = templateImage[i][j];
				if (rgb == 1) {
					// 原图对应位置颜色变化
					oriImage.setRGB(i, j, -1);
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
	 * @author xuLiang
	 * @since 1.0.0
	 */
	public static String imageToBase64(BufferedImage image) throws Exception {
		byte[] imagedata = null;
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		ImageIO.write(image, "png", bao);
		imagedata = bao.toByteArray();
		BASE64Encoder encoder = new BASE64Encoder();
		String base64Image = encoder.encodeBuffer(imagedata).trim();
		// 删除 \r\n
		base64Image = base64Image.replaceAll("\n", "").replaceAll("\r", "");
		return base64Image;
	}

	/**
	 * Base64转图片
	 * 
	 * @param base64String
	 * @return image
	 * @throws Exception
	 * @author xuLiang
	 * @throws IOException
	 * @since 1.0.0
	 */
	public static BufferedImage base64StringToImage(String base64String) throws IOException {
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] bytes1 = decoder.decodeBuffer(base64String);
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
		return ImageIO.read(bais);
	}

}