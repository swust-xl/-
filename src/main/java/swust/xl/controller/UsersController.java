package swust.xl.controller;

import javax.servlet.http.HttpSession;

import org.springframework.validation.annotation.Validated;
import swust.xl.pojo.vo.GetUserResp;
import swust.xl.pojo.vo.UserLogin;
import swust.xl.pojo.vo.adduser.requset.VoAddUserRequest;
import swust.xl.pojo.vo.patchuser.request.VoPatchUserRequest;

/**
 * 
 * 用户信息操作相关控制器接口.
 * </p>
 *
 * @author xuLiang
 * @since 0.0.1
 */
@Validated
public interface UsersController {
	/**
	 * 
	 * 添加一个用户信息记录.
	 *
	 * @param voAddUserRequset
	 *            需要添加的用户信息记录
	 * @return 封装用户信息的统一响应对象
	 * @author xuLiang
	 * @since 0.0.1
	 */
	GetUserResp addUser(VoAddUserRequest voAddUserRequset);

	/**
	 * 
	 * 获取一个用户信息记录.
	 *
	 * @param id
	 *            需要获取的用户信息id
	 * @return 封装用户信息的统一响应对象
	 * @author xuLiang
	 * @since 0.0.1
	 */
	GetUserResp getUser(Long id);

	/**
	 * 
	 * 删除一个用户信息记录.
	 *
	 * @param id
	 *            需要删除的用户id
	 * @author xuLiang
	 * @since 0.0.1
	 */

	void deleteUser(Long id);

	/**
	 * 
	 * 更新一个用户信息记录.
	 * 
	 * @param id
	 *            需要更新的用户id
	 * @param voPatchUserRequest
	 *            需要更新的用户信息内容
	 * @return 封装用户信息的统一响应对象
	 * @author xuLiang
	 * @since 0.0.1
	 */

	GetUserResp patchUser(Long id, VoPatchUserRequest voPatchUserRequest);

	/**
	 * 用户登陆验证
	 * 
	 * @param userLogin,session
	 *            用户登录请求体
	 * @return SESSION
	 * @author xuLiang
	 * @since 0.0.1
	 */
	public String loginVerify(UserLogin userLogin, HttpSession session);

	/**
	 * 用户登出移除session
	 * 
	 * @param session
	 * 
	 * @author xuLiang
	 * @since 0.0.1
	 */
	public String logout(HttpSession session);
}
