package cn.signit.annotation.checkuser;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.signit.config.websecurity.WebSecurityConfig;
import cn.signit.dao.UserMappers;
import cn.signit.exceptions.NoAccessException;
import cn.signit.util.session.SessionUtil;

/**
 * 检查用户相关切面类
 * 
 * @see cn.signit.annotation.checkuser.CheckUser
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

	// @Pointcut("execution(* cn.signit.controller.impl.*.*(..))") 启动超级慢
	@Pointcut("within(cn.signit.controller.impl..*)") // 基本秒开
	public void pointCut() {

	}

	/**
	 * 检查用户限权
	 * 
	 * @param joinPoint
	 *            切点
	 * @param checkUser
	 *            注解
	 * @throws NoAccessException
	 * @see cn.signit.annotation.checkuser.CheckUser
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
			} else if (arg instanceof Long) {
				if (mappers.getUser((Long) arg).getIsSystem().intValue() != 1) {
					throw new NoAccessException(checkUser.message());
				}
			}
		}
	}

}