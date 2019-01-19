package cn.signit.service;

import java.io.IOException;

import cn.signit.pojo.vo.VerificationCodeResp;

/**
 * 
 * 验证相关服务层接口
 *
 * @author xuLiang
 * @since 1.0.0
 */
public interface WebService {
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
	VerificationCodeResp getImage(int originImgWidth, int originImgHeight, int cuttedImgWidth, int cuttedImgHeight,
			String path) throws IOException;
}
