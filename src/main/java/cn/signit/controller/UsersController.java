package cn.signit.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import cn.signit.pojo.CommonResp;
import cn.signit.pojo.vo.UserLogin;
import cn.signit.pojo.vo.VoAddUserRequest;
import cn.signit.pojo.vo.VoUpdateUserRequest;

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
	public CommonResp<?> addUser(@Valid @NotNull VoAddUserRequest voAddUserRequset);

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
	public CommonResp<?> getUserById(Long id);

	/**
	 * 
	 * 根据用户名查询用户信息
	 *
	 * @param userName
	 *            待获取用户的用户名
	 * @return 封装用户信息的统一响应对象
	 * 
	 * @author xuLiang
	 * @since 1.0.0
	 */
	public CommonResp<?> getUser(String userName);

	/**
	 * 
	 * 删除一个用户信息记录.
	 *
	 * @param id
	 *            需要删除的用户id
	 * @author xuLiang
	 * @since 1.0.0
	 */

	public CommonResp<?> deleteUser(Long id);

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

	public CommonResp<?> patchUser(@Valid @NotNull VoUpdateUserRequest voPatchUserRequest);

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
	public CommonResp<?> loginVerify(UserLogin userLogin);

	/**
	 * 用户登出移除session
	 * 
	 * @param session
	 * @return CommonResp<?>
	 * @author xuLiang
	 * @since 1.0.0
	 */
	public CommonResp<?> logout();

	/**
	 * 查询用户统计数据
	 * 
	 * @param userName
	 *            用户名
	 * @return CommonResp<?>
	 * @author xuLiang
	 * @since 1.0.0
	 */
	public CommonResp<?> getStatistics(String userName);
}
