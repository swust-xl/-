package cn.signit.controller.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cn.signit.annotation.checkuser.CheckUser;
import cn.signit.annotation.requestlimit.RequestLimit;
import cn.signit.config.websecurity.WebSecurityConfig;
import cn.signit.controller.UsersController;
import cn.signit.exceptions.UserInfoException;
import cn.signit.pojo.CommonResp;
import cn.signit.pojo.dto.VoMapper;
import cn.signit.pojo.po.mysql.tables.pojos.VerifyStatistics;
import cn.signit.pojo.vo.UserLogin;
import cn.signit.pojo.vo.VoAddUserRequest;
import cn.signit.pojo.vo.VoGetUserResp;
import cn.signit.pojo.vo.VoUpdateUserRequest;
import cn.signit.service.UsersService;
import cn.signit.service.VerifyStatisticsService;
import cn.signit.urls.RestUrls;

/**
 * 
 * 用户信息操作相关控制器实现.
 *
 * @author xuLiang
 * @since 1.0.0
 */
@RestController
public class UsersControllerImpl implements UsersController {

	@Autowired
	private UsersService usersService;
	@Autowired
	private VerifyStatisticsService verifyStatisticsService;
	@Autowired
	private HttpServletRequest request;

	/**
	 * 
	 * 添加一个用户信息记录.
	 *
	 * @param voAddUserRequest
	 *            需要添加的用户信息记录
	 * @return 封装用户信息的统一响应对象
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@PostMapping(RestUrls.USERS)
	@ResponseStatus(HttpStatus.CREATED)
	@RequestLimit(count = 1)
	@Override
	public CommonResp<?> addUser(@RequestBody VoAddUserRequest voAddUserRequest) {
		return new CommonResp<>(1, "添加成功", VoMapper.INSTANCE.fromBoToVoGetUserResponse(
				usersService.addUser(VoMapper.INSTANCE.fromVoToBoAddUserRequest(voAddUserRequest))));
	}

	/**
	 * 
	 * 根据id查询用户信息
	 *
	 * @param id
	 *            待获取用户id
	 * @return 封装用户信息的统一响应对象
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@GetMapping(RestUrls.USERS_PathVariable_ID)
	@ResponseStatus(HttpStatus.OK)
	@Override
	public CommonResp<?> getUserById(@PathVariable("user-id") Long id) {

		return new CommonResp<>(1, "查询成功", VoMapper.INSTANCE.fromBoToVoGetUserResponse(usersService.getUser(id)));
	}

	/**
	 * 
	 * 根据用户名查询用户信息
	 *
	 * @param username
	 *            待获取用户的用户名
	 * @return 封装用户信息的统一响应对象
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@GetMapping(RestUrls.USERS)
	@ResponseStatus(HttpStatus.OK)
	@CheckUser(message = "拒绝访问")
	@Override
	public CommonResp<?> getUser(@RequestParam("usernameOrEmail") String usernameOrEmail) {
		VoGetUserResp result = VoMapper.INSTANCE.fromBoToVoGetUserResponse(usersService.getUser(usernameOrEmail));
		if (result == null) {
			throw new UserInfoException("没有此用户信息");
		}
		return new CommonResp<>(1, "查询成功", result);
	}

	/**
	 * 
	 * 删除一个用户信息记录.
	 *
	 * @param id
	 *            需要删除的用户id
	 * @return 封装用户信息的统一响应对象
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@DeleteMapping(RestUrls.USERS)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@CheckUser(message = "用户无限权")
	@Override
	public CommonResp<?> deleteUser(@RequestParam Long id) {

		return new CommonResp<>(1, "删除成功", usersService.deleteUser(id));

	}

	/**
	 * 
	 * 更新一个用户信息记录.
	 *
	 * @param username
	 *            需要更新的用户的用户名
	 * @param voPatchUserRequest
	 *            需要更新的用户对象
	 * @return 封装用户信息的统一响应对象
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@PutMapping(RestUrls.USERS)
	@ResponseStatus(HttpStatus.OK)
	@Override
	public CommonResp<?> patchUser(@RequestBody VoUpdateUserRequest voPatchUserRequest) {
		return new CommonResp<>(1, "更新成功", VoMapper.INSTANCE.fromBoToVoGetUserResponse(
				usersService.updateUser(VoMapper.INSTANCE.fromVoToBoUpdateUserRequest(voPatchUserRequest))));
	}

	/**
	 * 用户登陆验证
	 * 
	 * @param userLogin
	 *            用户登录请求体
	 * @return CommonResp<?>
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@PostMapping(RestUrls.USERS_LOGIN)
	@ResponseStatus(HttpStatus.OK)
	@RequestLimit(count = 3)
	@Override
	public CommonResp<?> loginVerify(@RequestBody UserLogin userLogin) {
		request.getSession().setAttribute(WebSecurityConfig.SESSION_KEY, userLogin.getUsernameOrEmail());
		request.getSession().setMaxInactiveInterval(3600);
		return new CommonResp<>(1, "登录成功", VoMapper.INSTANCE
				.fromBoToVoGetUserResponse(usersService.updateLastLoginDatetime(userLogin.getUsernameOrEmail())));
	}

	/**
	 * 用户登出移除session
	 * 
	 * @return CommonResp<?>
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@DeleteMapping(RestUrls.USERS_LOGOUT)
	@ResponseStatus(HttpStatus.OK)
	@Override
	public CommonResp<?> logout() {
		request.getSession().invalidate();
		return new CommonResp<>(1, "登出成功", null);
	}

	/**
	 * 查询用户统计数据
	 * 
	 * @param userName
	 *            用户名
	 * @return CommonResp<?>
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@GetMapping(RestUrls.USERS_STATISTICS_PathVariable_USERNAME)
	@ResponseStatus(HttpStatus.OK)
	@CheckUser(message = "拒绝访问")
	@Override
	public CommonResp<?> getStatistics(@PathVariable String userName) {
		VerifyStatistics result = verifyStatisticsService.getStatistics(userName);
		return new CommonResp<>(1, "查询成功", result);
	}

}