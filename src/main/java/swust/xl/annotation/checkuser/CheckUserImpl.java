package swust.xl.annotation.checkuser;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import swust.xl.config.websecurity.WebSecurityConfig;
import swust.xl.dao.UserMappers;
import swust.xl.exceptions.NoAccessException;
import swust.xl.exceptions.UserAlreadyExistsException;
import swust.xl.exceptions.UserInfoException;
import swust.xl.pojo.vo.adduser.requset.VoAddUserRequest;
import swust.xl.pojo.vo.patchuser.request.VoPatchUserRequest;
import swust.xl.pojo.vo.user.login.UserLogin;
import swust.xl.service.UsersService;
import swust.xl.util.session.SessionUtil;

/**
 * 检查用户相关切面类
 * 
 * @see swust.xl.annotation.checkuser.CheckUser
 * @author xuLiang
 * @since 1.0.0
 */
@Component
@Aspect
public class CheckUserImpl {

	@Autowired
	private SessionUtil sessionUtil;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private UserMappers mappers;
	@Autowired
	private UsersService usersService;

	// @Pointcut("execution(* swust.xl.controller.impl.*.*(..))") 启动超级慢
	@Pointcut("within(swust.xl.controller.impl..*)") // 基本秒开
	public void pointCut() {

	}

	/**
	 * 检查用户名或邮箱是否已经存在
	 * 
	 * @param joinPoint
	 *            切点
	 * @param checkUser
	 *            注解
	 * @throws UserAlreadyExistsException
	 * @see swust.xl.annotation.checkuser.CheckUser
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@Before(value = "pointCut() && @annotation(checkUser)")
	public void checkIfUserExists(JoinPoint joinPoint, CheckUser checkUser) {
		Object[] objects = joinPoint.getArgs();
		for (Object arg : objects) {
			if (arg instanceof VoAddUserRequest) {
				VoAddUserRequest voAddUserRequest = (VoAddUserRequest) arg;
				if (mappers.getUser(voAddUserRequest.getUserName()) != null
						|| mappers.getUser(voAddUserRequest.getEmail()) != null) {
					throw new UserAlreadyExistsException(checkUser.message());
				}
			}
		}
	}

	/**
	 * 检查用户名和密码是否匹配
	 * 
	 * @param joinPoint
	 *            切点
	 * @param checkUser
	 *            注解
	 * @throws UserInfoException
	 * @see swust.xl.annotation.checkuser.CheckUser
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@Before(value = "pointCut() && @annotation(checkUser)")
	public void verifyUserInfo(JoinPoint joinPoint, CheckUser checkUser) {
		Object[] objects = joinPoint.getArgs();
		for (Object arg : objects) {
			if (arg instanceof UserLogin) {
				UserLogin userLogin = (UserLogin) arg;
				if (!usersService.verifyLogin(userLogin)) {
					throw new UserInfoException(checkUser.message());
				}
			}
			if (arg instanceof VoPatchUserRequest) {
				VoPatchUserRequest voPatchUserRequest = (VoPatchUserRequest) arg;
				if (!usersService.verifyLogin(
						new UserLogin(voPatchUserRequest.getUserName(), voPatchUserRequest.getOldPassword()))) {
					throw new UserInfoException(checkUser.message());
				}
			}
		}
	}

	/**
	 * 检查用户限权
	 * 
	 * @param joinPoint
	 *            切点
	 * @param checkUser
	 *            注解
	 * @throws NoAccessException
	 * @see swust.xl.annotation.checkuser.CheckUser
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@Before(value = "pointCut() && @annotation(checkUser)")
	public void checkAccess(JoinPoint joinPoint, CheckUser checkUser) {
		Object[] objects = joinPoint.getArgs();
		for (Object arg : objects) {
			if (arg instanceof String) {
				boolean accesse = sessionUtil.checkAttribute(request.getSession(), WebSecurityConfig.SESSION_KEY)
						&& sessionUtil.verifyAttribute(request.getSession(), WebSecurityConfig.SESSION_KEY, arg);
				if (!accesse) {
					throw new NoAccessException(checkUser.message());
				}
			}
		}
	}

}