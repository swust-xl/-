package swust.xl.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import swust.xl.pojo.bo.adduser.request.BoAddUserRequest;
import swust.xl.pojo.bo.getuser.response.BoGetUserResp;
import swust.xl.pojo.bo.patchuser.request.BoPatchUserRequest;
import swust.xl.pojo.vo.UserLogin;

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
	 * 根据用户名或邮箱获取一条用户信息记录.
	 * 
	 * @param usernameOrEmail
	 *            待获取用户的用户名或邮箱
	 * @return 一条得到的用户信息记录
	 * @author xuLiang
	 * @since 1.0.0
	 */

	BoGetUserResp getUser(@Valid @NotNull String usernameOrEmail);

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
	 * @return UserPerson
	 * @author xuLiang
	 * @since 1.0.0
	 */
	BoGetUserResp updateLastLoginDatetime(@Valid @NotNull String userameOrEmail);

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
}
