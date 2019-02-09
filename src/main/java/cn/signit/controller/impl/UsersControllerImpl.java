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
import cn.signit.exceptions.NoAccessException;
import cn.signit.pojo.CommonResp;
import cn.signit.pojo.dto.VoMapper;
import cn.signit.pojo.po.mysql.tables.pojos.VerifyStatistics;
import cn.signit.pojo.vo.UserLogin;
import cn.signit.pojo.vo.VoAddUserRequest;
import cn.signit.pojo.vo.VoGetUserResp;
import cn.signit.pojo.vo.VoUpdateUserRequest;
import cn.signit.service.UsersService;
import cn.signit.service.VerifyStatisticsService;
import cn.signit.util.session.SessionUtil;

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
	@Autowired
	private SessionUtil sessionUtil;

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
	@PostMapping("/users")
	@CheckUser(message = "用户名或邮箱已存在")
	@RequestLimit(value = 1)
	@Override
	public CommonResp<?> addUser(@RequestBody VoAddUserRequest voAddUserRequest) {
		verifyStatisticsService.initUserInfo(voAddUserRequest.getUserName());
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
	@GetMapping("/users/{user-id}")
	@Override
	public CommonResp<?> getUserById(@PathVariable("user-id") Long id) {
		if (sessionUtil.checkAttribute(request.getSession(), "admin")) {
			return new CommonResp<>(1, "查询成功", VoMapper.INSTANCE.fromBoToVoGetUserResponse(usersService.getUser(id)));
		}
		throw new NoAccessException("拒绝访问");
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
	@GetMapping("/users")
	@CheckUser(message = "拒绝访问")
	@Override
	public CommonResp<?> getUser(@RequestParam("usernameOrEmail") String usernameOrEmail) {
		VoGetUserResp result = VoMapper.INSTANCE.fromBoToVoGetUserResponse(usersService.getUser(usernameOrEmail));
		if (result == null) {
			return new CommonResp<>(0, "没有相关用户信息", null);
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
	@DeleteMapping("/users")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Override
	public CommonResp<?> deleteUser(@RequestParam Long id) {
		if (request.getSession().getAttribute("admin") != null) {
			return new CommonResp<>(1, "删除成功", usersService.deleteUser(id));
		}
		throw new NoAccessException("拒绝访问");
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
	@PutMapping("/users")
	@CheckUser(message = "用户名或密码错误")
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
	 * @return ResponseEntity<Object>
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@PostMapping("/users/login")
	@CheckUser(message = "用户名或密码错误")
	@RequestLimit(value = 3)
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
	@DeleteMapping("/users/logout")
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
	@GetMapping("/users/statisics/{userName}")
	@CheckUser(message = "拒绝访问")
	@Override
	public CommonResp<?> getAllStatistics(@PathVariable String userName) {
		VerifyStatistics result = verifyStatisticsService.getStatistics(userName);
		return new CommonResp<>(1, "查询成功", result);
	}
}