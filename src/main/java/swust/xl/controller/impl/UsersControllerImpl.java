package swust.xl.controller.impl;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import swust.xl.annotation.checkuser.CheckUser;
import swust.xl.annotation.requestlimit.RequestLimit;
import swust.xl.config.websecurity.WebSecurityConfig;
import swust.xl.controller.UsersController;
import swust.xl.pojo.dto.VoMapper;
import swust.xl.pojo.vo.GetUserCommonResp;
import swust.xl.pojo.vo.GetUserResp;
import swust.xl.pojo.vo.UserLogin;
import swust.xl.pojo.vo.adduser.requset.VoAddUserRequest;
import swust.xl.pojo.vo.getuser.response.VoGetUserResp;
import swust.xl.pojo.vo.patchuser.request.VoPatchUserRequest;
import swust.xl.service.UsersService;
import swust.xl.service.VerifyStatisticsService;
import swust.xl.util.response.ResponseUtil;
import swust.xl.util.session.SessionUtil;

/**
 * 
 * 用户信息操作相关控制器实现.
 * </p>
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
	private ResponseUtil responseUtil;
	@Autowired
	private SessionUtil sessionUtil;

	/**
	 * 
	 * 添加一个用户信息记录.
	 *
	 * @param voAddUserRequest
	 *            需要添加的用户信息记录
	 * @return 封装用户信息的统一响应对象
	 * 
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@ApiOperation(value = "新增用户", notes = "同一IP一分钟内只能注册成功一次", httpMethod = "POST", response = GetUserCommonResp.class)
	@PostMapping("/users")
	@CheckUser(message = "用户名或邮箱已存在")
	@RequestLimit(60)
	@Override
	public ResponseEntity<Object> addUser(@RequestBody VoAddUserRequest voAddUserRequest) {
		verifyStatisticsService.InitUserInfo(voAddUserRequest.getUserName());
		return responseUtil.commonResp(HttpStatus.CREATED, 1, "添加成功",
				VoMapper.INSTANCE.fromBoToVoGetUserCommonResponseMap(
						usersService.addUser(VoMapper.INSTANCE.fromVoToBoAddUserRequestMap(voAddUserRequest))));
	}

	/**
	 * 
	 * 根据id查询用户信息
	 *
	 * @param id
	 *            待获取用户id
	 * @return 封装用户信息的统一响应对象
	 * 
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@ApiOperation(value = "根据id查询用户", notes = "仅管理员可操作", httpMethod = "GET", response = GetUserResp.class)
	@GetMapping("/users/{user-id}")
	@Override
	public ResponseEntity<Object> getUserById(@PathVariable("user-id") Long id) {
		if (sessionUtil.checkAttribute(request.getSession(), "admin")) {
			return responseUtil.getUserResp(HttpStatus.OK, 1, "查询成功",
					VoMapper.INSTANCE.fromBoToVoGetUserResponseMap(usersService.getUser(id)));
		}
		return responseUtil.errorResp(HttpStatus.BAD_REQUEST, 0, "拒绝访问");
	}

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
	@ApiOperation(value = "根据用户名或邮箱查询用户", notes = "用户登录后才可查询并且只能查询当前登录用户的信息", httpMethod = "GET", response = GetUserResp.class)
	@GetMapping("/users")
	@CheckUser(message = "拒绝访问")
	@RequestLimit
	@Override
	public ResponseEntity<Object> getUserByUsername(@RequestParam("usernameOrEmail") String usernameOrEmail) {
		VoGetUserResp result = VoMapper.INSTANCE.fromBoToVoGetUserResponseMap(usersService.getUser(usernameOrEmail));
		if (result == null) {
			return responseUtil.errorResp(HttpStatus.BAD_REQUEST, 0, "没有相关用户信息");
		}
		return responseUtil.getUserResp(HttpStatus.OK, 0, "查询成功", result);
	}

	/**
	 * 
	 * 删除一个用户信息记录.
	 *
	 * @param id
	 *            需要删除的用户id
	 * @return 封装用户信息的统一响应对象
	 * 
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@ApiOperation(value = "根据id删除用户", notes = "仅管理员可操作", httpMethod = "DELETE")
	@DeleteMapping("/users")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Override
	public void deleteUser(@RequestParam Long id) {
		if (request.getSession().getAttribute("admin") != null) {
			usersService.deleteUser(id);
		}
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
	 * 
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@ApiOperation(value = "更新用户", notes = "无论用户更新了哪条信息都需要将完整的用户信息发送给后台(用户名不能更改)", httpMethod = "PUT", response = GetUserResp.class)
	@PutMapping("/users")
	@CheckUser(message = "用户名或密码错误")
	@RequestLimit
	@Override
	public ResponseEntity<Object> patchUser(@RequestBody VoPatchUserRequest voPatchUserRequest) {
		return responseUtil.commonResp(HttpStatus.OK, 1, "更新成功", VoMapper.INSTANCE.fromBoToVoGetUserCommonResponseMap(
				usersService.patchUser(VoMapper.INSTANCE.fromVoToBoPatchUserRequestMap(voPatchUserRequest))));
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
	@ApiOperation(value = "登录", notes = "用户登录接口,管理员账号登录后有管理员限权", httpMethod = "POST", response = GetUserCommonResp.class)
	@PostMapping("/users/login")
	@CheckUser(message = "用户名或密码错误")
	@RequestLimit
	@Override
	public ResponseEntity<Object> loginVerify(@RequestBody UserLogin userLogin) {
		request.getSession().setAttribute(WebSecurityConfig.SESSION_KEY, userLogin.getUsernameOrEmail());
		request.getSession().setMaxInactiveInterval(3600);
		return responseUtil.commonResp(HttpStatus.OK, 1, "登录成功", VoMapper.INSTANCE.fromBoToVoGetUserCommonResponseMap(
				usersService.updateLastLoginDatetime(userLogin.getUsernameOrEmail())));
	}

	/**
	 * 用户登出移除session
	 * 
	 * @return ResponseEntity<Object>
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@ApiOperation(value = "登出", notes = "用户登出接口", httpMethod = "DELETE")
	@DeleteMapping("/users/logout")
	@Override
	public ResponseEntity<Object> logout() {
		request.getSession().invalidate();
		return responseUtil.commonResp(HttpStatus.OK, 1, "登出成功", null);
	}
}