package swust.xl.controller.impl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import swust.xl.annotation.CustomAnnotation;
import swust.xl.controller.UsersController;
import swust.xl.pojo.dto.VoMapper;
import swust.xl.pojo.vo.GetUserResp;
import swust.xl.pojo.vo.UserLogin;
import swust.xl.pojo.vo.adduser.requset.VoAddUserRequest;
import swust.xl.pojo.vo.patchuser.request.VoPatchUserRequest;
import swust.xl.service.UsersService;
import swust.xl.webSecurityConfig.WebSecurityConfig;

/**
 * 
 * 用户信息操作相关控制器实现.
 * </p>
 *
 * @author xuLiang
 * @since 0.0.1
 */
@RestController
public class UsersControllerImpl implements UsersController {

	@Autowired
	public UsersService usersService;

	/**
	 * 
	 * 添加一个用户信息记录.
	 *
	 * @param voAddUserRequest
	 *            需要添加的用户信息记录
	 * @return 封装用户信息的统一响应对象
	 * 
	 * @author xuLiang
	 * @since 0.0.1
	 */
	@PostMapping("/users")
	@ResponseStatus(HttpStatus.CREATED)
	@Override
	@CustomAnnotation
	public GetUserResp addUser(@RequestBody VoAddUserRequest voAddUserRequest) {
		GetUserResp result = new GetUserResp();
		result.setCode(1);
		result.setMessage("添加成功！");
		result.setVoGetUserResponse(VoMapper.INSTANCE.fromBoToVoGetUserResponseMap(
				usersService.addUser(VoMapper.INSTANCE.fromVoToBoAddUserRequestMap(voAddUserRequest))));
		return result;
	}

	/**
	 * 
	 * 获取一个用户信息记录.
	 *
	 * @param id
	 *            待获取用户id
	 * @return 封装用户信息的统一响应对象
	 * 
	 * @author xuLiang
	 * @since 0.0.1
	 */

	@GetMapping("/users/{user-id}")
	@ResponseStatus(HttpStatus.OK)
	@Override
	public GetUserResp getUser(@PathVariable("user-id") Long id) {
		GetUserResp result = new GetUserResp();
		result.setCode(1);
		result.setMessage("查询成功！");
		result.setVoGetUserResponse(VoMapper.INSTANCE.fromBoToVoGetUserResponseMap(usersService.getUser(id)));
		return result;
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
	 * @since 0.0.1
	 */
	@DeleteMapping("/users/{user-id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Override
	public void deleteUser(@PathVariable("user-id") Long id) {
		usersService.deleteUser(id);
	}

	/**
	 * 
	 * 更新一个用户信息记录.
	 *
	 * @param id
	 *            需要更新的用户id
	 * @param voPatchUserRequest
	 *            需要更新的用户对象
	 * @return 封装用户信息的统一响应对象
	 * 
	 * @author xuLiang
	 * @since 0.0.1
	 */
	@PutMapping("/users/{user-id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	@Override
	public GetUserResp patchUser(@PathVariable("user-id") Long id, @RequestBody VoPatchUserRequest voPatchUserRequest) {
		GetUserResp result = new GetUserResp();
		result.setCode(1);
		result.setMessage("update succeed");
		result.setVoGetUserResponse(VoMapper.INSTANCE.fromBoToVoGetUserResponseMap(
				usersService.patchUser(VoMapper.INSTANCE.fromVoToBoPatchUserRequestMap(voPatchUserRequest))));

		return result;
	}

	/**
	 * 用户登陆验证
	 * 
	 * @param userLogin
	 *            用户登录请求体
	 * @return SESSION
	 * @author xuLiang
	 * @since 0.0.1
	 * 
	 */
	@PostMapping("/loginVerify")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@Override
	public String loginVerify(@RequestBody UserLogin userLogin, HttpSession session) {
		if (userLogin.getId().toString().isEmpty() || userLogin.getPassword().isEmpty()) {
			return "ID或密码不能为空";
		} else {
			boolean verify = usersService.verifyLogin(userLogin);
			if (verify) {
				session.setAttribute(WebSecurityConfig.SESSION_KEY, userLogin.getId());
				session.setMaxInactiveInterval(3600);
				return "登陆成功";
			} else {
				return "ID或密码错误";
			}
		}

	}

	/**
	 * 用户登出移除session
	 * 
	 * @param userLogin
	 *            用户登录请求体
	 * @return SESSION
	 * @author xuLiang
	 * @since 0.0.1
	 */
	@GetMapping("/logout")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@Override
	public String logout(HttpSession session) {
		session.removeAttribute(WebSecurityConfig.SESSION_KEY);
		return "登出成功";
	}
}
