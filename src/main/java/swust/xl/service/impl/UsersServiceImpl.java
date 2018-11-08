package swust.xl.service.impl;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.sql.Timestamp;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import swust.xl.dao.Mappers;
import swust.xl.pojo.bo.adduser.request.BoAddUserRequest;
import swust.xl.pojo.bo.getuser.response.BoGetUserResp;
import swust.xl.pojo.bo.patchuser.request.BoPatchUserRequest;
import swust.xl.pojo.dto.BoMapper;
import swust.xl.pojo.vo.UserLogin;
import swust.xl.pojo.vo.VerificationCodeResp;
import swust.xl.service.UsersService;
import swust.xl.util.image.VerifyImageUtil;
import swust.xl.util.md5.Md5Util;

/**
 * 
 * 用户信息操作相关服务层实现.
 *
 * @author xuLiang
 * @since 1.0.0
 */
@Service
public class UsersServiceImpl implements UsersService {

	@Autowired
	private Mappers mappers;

	/**
	 * 
	 * 根据id获取一条用户信息记录.
	 *
	 * @param id
	 *            待获取用户id
	 * @return 一条得到的用户信息记录
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@Override
	public BoGetUserResp getUser(@Valid @NotNull Long id) {
		return BoMapper.INSTANCE.toBoGetUserRespMap(mappers.getUser(id));

	}

	/**
	 * 根据用户名获取一条用户信息记录.
	 * 
	 * @param username
	 *            待获取用户的用户名
	 * @return 一条得到的用户信息记录
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@Override
	public BoGetUserResp getUser(@Valid @NotNull String username) {
		return BoMapper.INSTANCE.toBoGetUserRespMap(mappers.getUser(username));

	}

	/**
	 * 
	 * 添加一条用户信息记录.
	 *
	 * @param boAddUserRequest
	 *            待添加的用户信息
	 * @return 添加完成的用户信息对象
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public BoGetUserResp addUser(BoAddUserRequest boAddUserRequest) {
		String salt = Md5Util.getSalt();
		String passwordWithSalt = Md5Util.md5Hex(boAddUserRequest.getPassword() + salt);
		boAddUserRequest.setSalt(salt);
		boAddUserRequest.setPassword(passwordWithSalt);
		boAddUserRequest.setRegistDatetime(new Timestamp(System.currentTimeMillis()));
		return BoMapper.INSTANCE
				.toBoGetUserRespMap(mappers.addUser(BoMapper.INSTANCE.fromBoAddUserReqMap(boAddUserRequest)));
	}

	/**
	 * 
	 * 删除一条用户信息记录.
	 *
	 * @param id
	 *            待删除的用户信息id
	 * @return 删除结果：true-删除成功，成功删除一条用户记录；false-删除失败，没有删除任何记录
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean deleteUser(Long id) {
		return mappers.deleteUser(id);
	}

	/**
	 * 
	 * 更新一个用户信息记录.
	 * 
	 * @param userPerson
	 *            需要更新的用户对象
	 * 
	 * @return 更新后的用户对象
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public BoGetUserResp patchUser(BoPatchUserRequest boPatchUserRequest) {
		String salt = Md5Util.getSalt();
		String passwordWithSalt = Md5Util.md5Hex(boPatchUserRequest.getPassword() + salt);
		boPatchUserRequest.setSalt(salt);
		boPatchUserRequest.setPassword(passwordWithSalt);
		return BoMapper.INSTANCE
				.toBoGetUserRespMap(mappers.patchUser(BoMapper.INSTANCE.fromBoPatchUserReqMap(boPatchUserRequest)));
	}

	/**
	 * 
	 * 验证用户登陆
	 * 
	 * @param userLogin
	 *            用户登陆请求体
	 * @return true ， false
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@Override
	public boolean verifyLogin(UserLogin userLogin) {
		userLogin.setPassword(
				Md5Util.md5Hex(userLogin.getPassword() + mappers.getUser(userLogin.getUsername()).getSalt()));
		return mappers.findByIdAndPassword(userLogin);
	}

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
	 * @throws Exception
	 */
	@Override
	public VerificationCodeResp getImage(int originImgWidth, int originImgHeight, int cuttedImgWidth,
			int cuttedImgHeight, String path) throws Exception {
		VerificationCodeResp response = new VerificationCodeResp();
		int x = new Random().nextInt(originImgWidth - cuttedImgWidth);
		int y = new Random().nextInt(originImgHeight - cuttedImgHeight);
		response.setxCoordinate(x + "");
		response.setyCoordinate(y + "");
		BufferedImage bufferedImage = ImageIO.read(new FileInputStream(path));
		// 用来裁剪到滑动的方块
		BufferedImage cuttedImg = VerifyImageUtil.getCuttedImage(bufferedImage, x, y, cuttedImgWidth, cuttedImgHeight);
		response.setCuttedImgBase64(VerifyImageUtil.imageToBase64(cuttedImg));
		// 被抠滑块的坐标集合
		int[][] cuttedOriginImgCoordinate = VerifyImageUtil.getCutAreaData(originImgWidth, originImgHeight, x, y,
				cuttedImgWidth, cuttedImgHeight);
		// 得到抠掉滑块后的图并加阴影
		VerifyImageUtil.cutByTemplate(bufferedImage, cuttedOriginImgCoordinate);
		response.setCuttedOriginImgBase64(VerifyImageUtil.imageToBase64(bufferedImage));
		return response;
	}

	/**
	 * 更新用户最后登录时间
	 * 
	 * @param username
	 * @return Timestamp更新后的时间
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Timestamp updateLastLoginDatetime(String username) {
		return mappers.updateLastLoginDatetime(username);
	}

}
