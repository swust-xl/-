package swust.xl.controller.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import swust.xl.config.websecurity.WebSecurityConfig;
import swust.xl.controller.UsersController;
import swust.xl.pojo.dto.VoMapper;
import swust.xl.pojo.vo.UserLogin;
import swust.xl.pojo.vo.adduser.requset.VoAddUserRequest;
import swust.xl.pojo.vo.patchuser.request.VoPatchUserRequest;
import swust.xl.service.UsersService;
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

	public static final String REFUSE_ATTRIBUTE = "ip";
	public static final String ADMIN_ATTRIBUTE = "admin";

	@Autowired
	private UsersService usersService;
	@Autowired
	private HttpServletRequest request;

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
	@PostMapping("/users")
	@ResponseStatus(HttpStatus.CREATED)
	@Override
	public ResponseEntity<Object> addUser(@RequestBody VoAddUserRequest voAddUserRequest) {
		if (!SessionUtil.checkAttribute(request.getSession(), REFUSE_ATTRIBUTE)) {
			if (StringUtils.isEmpty(voAddUserRequest.getUsername())
					|| StringUtils.isEmpty(voAddUserRequest.getPassword())) {
				return ResponseUtil.errorResp(HttpStatus.BAD_REQUEST, 0, "用户信息格式错误");
			}
			if (usersService.getUser(voAddUserRequest.getUsername()) == null) {
				request.getSession().setAttribute(REFUSE_ATTRIBUTE, request.getRemoteAddr());
				SessionUtil.removeAttribute(request.getSession(), REFUSE_ATTRIBUTE, 60);
				return ResponseUtil.commonResp(HttpStatus.OK, 1, "添加成功",
						VoMapper.INSTANCE.fromBoToVoGetUserCommonResponseMap(
								usersService.addUser(VoMapper.INSTANCE.fromVoToBoAddUserRequestMap(voAddUserRequest))));
			}
			return ResponseUtil.errorResp(HttpStatus.BAD_REQUEST, 0, "用户名已存在");
		}
		return ResponseUtil.errorResp(HttpStatus.BAD_REQUEST, 0, "请求过于频繁");
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
	@GetMapping("/users/{user-id}")
	@ResponseStatus(HttpStatus.OK)
	@Override
	public ResponseEntity<Object> getUserById(@PathVariable("user-id") Long id) {
		if (SessionUtil.checkAttribute(request.getSession(), ADMIN_ATTRIBUTE)) {
			return ResponseUtil.getUserResp(HttpStatus.OK, 1, "查询成功",
					VoMapper.INSTANCE.fromBoToVoGetUserResponseMap(usersService.getUser(id)));
		}
		return ResponseUtil.errorResp(HttpStatus.BAD_REQUEST, 0, "拒绝访问");
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
	@GetMapping("/users")
	@ResponseStatus(HttpStatus.OK)
	@Override
	public ResponseEntity<Object> getUserByUsername(@RequestParam("username") String username) {
		if (SessionUtil.checkAttribute(request.getSession(), WebSecurityConfig.SESSION_KEY)
				&& SessionUtil.verifyAttribute(request.getSession(), WebSecurityConfig.SESSION_KEY, username)) {
			return ResponseUtil.getUserResp(HttpStatus.OK, 0, "查询成功",
					VoMapper.INSTANCE.fromBoToVoGetUserResponseMap(usersService.getUser(username)));
		}
		return ResponseUtil.errorResp(HttpStatus.BAD_REQUEST, 0, "拒绝访问");
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
	@DeleteMapping("/users")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Override
	public void deleteUser(@RequestParam Long id) {
		if (request.getSession().getAttribute(ADMIN_ATTRIBUTE) != null) {
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
	@PutMapping("/users")
	@ResponseStatus(HttpStatus.OK)
	@Override
	public ResponseEntity<Object> patchUser(@RequestBody VoPatchUserRequest voPatchUserRequest) {
		if (SessionUtil.checkAttribute(request.getSession(), WebSecurityConfig.SESSION_KEY)
				&& SessionUtil.verifyAttribute(request.getSession(), WebSecurityConfig.SESSION_KEY,
						voPatchUserRequest.getUsername())) {
			if (StringUtils.isEmpty(voPatchUserRequest.getUsername())
					|| StringUtils.isEmpty(voPatchUserRequest.getPassword())) {
				return ResponseUtil.errorResp(HttpStatus.BAD_REQUEST, 0, "用户信息格式错误");
			}
			return ResponseUtil.getUserResp(HttpStatus.OK, 1, "更新成功", VoMapper.INSTANCE.fromBoToVoGetUserResponseMap(
					usersService.patchUser(VoMapper.INSTANCE.fromVoToBoPatchUserRequestMap(voPatchUserRequest))));
		}
		return ResponseUtil.errorResp(HttpStatus.BAD_REQUEST, 0, "拒绝访问");
	}

	/**
	 * 用户登陆验证
	 * 
	 * @param userLogin
	 *            用户登录请求体
	 * @return ResponseEntity<Object>
	 * @author xuLiang
	 * @since 1.0.0
	 * 
	 */
	@PostMapping("/loginverify")
	@ResponseStatus(HttpStatus.OK)
	@Override
	public ResponseEntity<Object> loginVerify(@RequestBody UserLogin userLogin) {
		boolean verify = usersService.verifyLogin(userLogin);
		if (verify) {
			if (usersService.getUser(userLogin.getUsername()).getIsSystem() == 1) {
				request.getSession().setAttribute(WebSecurityConfig.SESSION_KEY, userLogin.getUsername());
				request.getSession().setAttribute("admin", 1);
				request.getSession().setMaxInactiveInterval(1800);
			} else {
				request.getSession().setAttribute(WebSecurityConfig.SESSION_KEY, userLogin.getUsername());
				request.getSession().setMaxInactiveInterval(3600);
			}
			usersService.updateLastLoginDatetime(userLogin.getUsername());
			return ResponseUtil.commonResp(HttpStatus.OK, 1, "登录成功", VoMapper.INSTANCE
					.fromBoToVoGetUserCommonResponseMap(usersService.getUser(userLogin.getUsername())));
		} else {
			return ResponseUtil.errorResp(HttpStatus.UNAUTHORIZED, 0, "用户名或密码错误");
		}
	}

	/**
	 * 用户登出移除session
	 * 
	 * @return ResponseEntity<Object>
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@DeleteMapping("/logout")
	@ResponseStatus(HttpStatus.OK)
	@Override
	public ResponseEntity<Object> logout() {
		request.getSession().invalidate();
		return ResponseUtil.commonResp(HttpStatus.OK, 1, "登出成功", null);
	}
}
