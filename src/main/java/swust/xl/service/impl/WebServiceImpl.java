package swust.xl.service.impl;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import swust.xl.pojo.vo.VerificationCodeResp;
import swust.xl.service.WebService;
import swust.xl.util.image.ImageUtil;

/**
 * 
 * 验证相关服务层实现
 *
 * @author xuLiang
 * @since 1.0.0
 */
@Service
public class WebServiceImpl implements WebService {
	/**
	 * 获取图片和坐标值
	 * 
	 * @param originImgWidth
	 *            原图的宽
	 * @param originImgHeight
	 *            原图的高
	 * @param cuttedImgWidth
	 *            裁出来的方块的宽
	 * @param cuttedImgHeight
	 *            裁出来的方块的高
	 * @param path
	 *            原图文件路径
	 * @return VerificationCodeResp
	 * @author xuLiang
	 * @since 1.0.0
	 * @throws IOException
	 */
	@Override
	public VerificationCodeResp getImage(int originImgWidth, int originImgHeight, int cuttedImgWidth,
			int cuttedImgHeight, String path) throws IOException {
		VerificationCodeResp response = new VerificationCodeResp();
		int x = new Random().nextInt(originImgWidth - cuttedImgWidth);
		int y = new Random().nextInt(originImgHeight - cuttedImgHeight);
		response.setxCoordinate(Integer.toString(x));
		response.setyCoordinate(Integer.toString(y));
		BufferedImage bufferedImage = ImageIO.read(new FileInputStream(path));
		// 用来裁剪到滑动的方块
		BufferedImage cuttedImg = ImageUtil.getCuttedImage(bufferedImage, x, y, cuttedImgWidth, cuttedImgHeight);
		response.setCuttedImgBase64(ImageUtil.imageToBase64(cuttedImg));
		// 被抠滑块的坐标集合
		int[][] cuttedOriginImgCoordinate = ImageUtil.getCutAreaData(originImgWidth, originImgHeight, x, y,
				cuttedImgWidth, cuttedImgHeight);
		// 得到抠掉滑块后的图并加阴影
		ImageUtil.cutByTemplate(bufferedImage, cuttedOriginImgCoordinate);
		response.setCuttedOriginImgBase64(ImageUtil.imageToBase64(bufferedImage));
		return response;
	}

}