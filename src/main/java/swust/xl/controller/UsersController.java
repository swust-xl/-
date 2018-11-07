package swust.xl.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import swust.xl.pojo.vo.UserLogin;
import swust.xl.pojo.vo.adduser.requset.VoAddUserRequest;
import swust.xl.pojo.vo.patchuser.request.VoPatchUserRequest;

/**
 * 
 * 用户信息操作相关控制器接口.
 *
 * @author xuLiang
 * @since 1.0.0
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
	 * @since 1.0.0
	 */
	ResponseEntity<Object> addUser(VoAddUserRequest voAddUserRequset);

	/**
	 * 
	 * 根据id获取一个用户信息记录.
	 *
	 * @param id
	 *            需要获取的用户信息id
	 * @return 封装用户信息的统一响应对象
	 * @author xuLiang
	 * @since 1.0.0
	 */
	ResponseEntity<Object> getUserById(Long id);

	/**
	 * 
	 * 根据用户名查询用户信息
	 *
	 * @param username
	 *            待获取用户的用户名
	 * @return 封装用户信息的统一响应对象
	 * 
	 * @author xuLiang
	 * @since 1.0.0
	 */
	ResponseEntity<Object> getUserByUsername(String username);

	/**
	 * 
	 * 删除一个用户信息记录.
	 *
	 * @param id
	 *            需要删除的用户id
	 * @author xuLiang
	 * @since 1.0.0
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
	 * @since 1.0.0
	 */

	ResponseEntity<Object> patchUser(VoPatchUserRequest voPatchUserRequest);

	/**
	 * 用户登陆验证
	 * 
	 * @param userLogin,session
	 *            用户登录请求体
	 * @return SESSION
	 * @author xuLiang
	 * @throws Exception
	 * @since 1.0.0
	 */
	public ResponseEntity<Object> loginVerify(UserLogin userLogin);

	/**
	 * 用户登出移除session
	 * 
	 * @param session
	 * @return ResponseEntity<Object>
	 * @author xuLiang
	 * @since 1.0.0
	 */
	public ResponseEntity<Object> logout();

}
