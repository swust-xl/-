package swust.xl.service;

import java.sql.Timestamp;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import swust.xl.pojo.bo.adduser.request.BoAddUserRequest;
import swust.xl.pojo.bo.getuser.response.BoGetUserResp;
import swust.xl.pojo.bo.patchuser.request.BoPatchUserRequest;
import swust.xl.pojo.vo.UserLogin;
import swust.xl.pojo.vo.VerificationCodeResp;

/**
 * 
 * 用户信息操作相关服务层接口.
 * </p>
 *
 * @author xuLiang
 * @since 1.0.0
 */
@Validated
public interface UsersService {
	/**
	 * 根据id获取一条用户信息记录.
	 * 
	 * @param id
	 *            待获取用户的id
	 * @return 一条得到的用户信息记录
	 * @author xuLiang
	 * @since 1.0.0
	 */

	BoGetUserResp getUser(@Valid @NotNull Long id);

	/**
	 * 根据用户名获取一条用户信息记录.
	 * 
	 * @param username
	 *            待获取用户的用户名
	 * @return 一条得到的用户信息记录
	 * @author xuLiang
	 * @since 1.0.0
	 */

	BoGetUserResp getUser(@Valid @NotNull String username);

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

	BoGetUserResp addUser(@Valid @NotNull BoAddUserRequest boAddUserRequest);

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
	boolean deleteUser(@Valid @NotNull Long id);

	/**
	 * 
	 * 更新一个用户信息记录.
	 * 
	 * @param boPatchUserRequest
	 *            需要更新的用户对象
	 * @return 更新完成的用户信息对象
	 * @author xuLiang
	 * @since 1.0.0
	 */
	BoGetUserResp patchUser(@Valid @NotNull BoPatchUserRequest boPatchUserRequest);

	/**
	 * 更新用户最后登录时间
	 * 
	 * @param username
	 * @return Timestamp更新后的时间
	 * @author xuLiang
	 * @since 1.0.0
	 */
	Timestamp updateLastLoginDatetime(String username);

	/**
	 * 
	 * 验证用户登陆
	 * 
	 * @param userLogin
	 *            用户登陆请求体
	 * @return true or false
	 * @author xuLiang
	 * @since 1.0.0
	 */
	boolean verifyLogin(@Valid @NotNull UserLogin userLogin);

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
	VerificationCodeResp getImage(int originImgWidth, int originImgHeight, int cuttedImgWidth, int cuttedImgHeight,
			String path) throws Exception;

}
