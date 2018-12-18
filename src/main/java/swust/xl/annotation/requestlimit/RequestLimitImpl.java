package swust.xl.annotation.requestlimit;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import swust.xl.exceptions.RequestTooFrequentException;
import swust.xl.util.session.SessionUtil;

/**
 * 限制接口访问次数
 * 
 * @see swust.xl.annotation.requestlimit.RequestLimit
 * @author xuLiang
 * @since 1.0.0
 */
@Component
@Aspect
public class RequestLimitImpl {

	@Autowired
	private SessionUtil sessionUtil;
	@Autowired
	private HttpServletRequest request;

	@Pointcut("within(swust.xl.controller.impl..*)")
	public void pointCut() {

	}

	/**
	 * 限制同一IP访问接口次数
	 * 
	 * @param joinPoint
	 *            切点
	 * @param requestLimit
	 *            注解
	 * @throws RequestTooFrequentException
	 * @see swust.xl.annotation.requestlimit.RequestLimit
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@Before(value = "pointCut() && @annotation(requestLimit)")
	public void requestLimit(JoinPoint joinPoint, RequestLimit requestLimit) {
		if (!sessionUtil.checkAttribute(request.getSession(), "ip")) {
			request.getSession().setAttribute("ip", request.getRemoteAddr());
			sessionUtil.removeAttribute(request.getSession(), "ip", requestLimit.value());
		} else {
			throw new RequestTooFrequentException(requestLimit.message());
		}
	}

}
