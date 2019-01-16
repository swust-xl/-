package swust.xl.annotation.requestlimit;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import swust.xl.exceptions.RequestTooFrequentException;

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
	private HttpServletRequest request;
	private Map<String, Integer> limitMap = new HashMap<>();

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
		String ip = request.getRemoteAddr();
		String url = request.getRequestURL().toString();
		String key = "req_limit_".concat(url).concat(ip);
		if (limitMap.get(key) == null || limitMap.get(key) == 0) {
			limitMap.put(key, 1);
		} else {
			limitMap.put(key, limitMap.get(key) + 1);
		}
		int count = limitMap.get(key);
		if (count > 0) {
			ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(2);
			executor.schedule(new Runnable() {
				@Override
				public void run() {
					limitMap.remove(key);
				}
			}, requestLimit.time(), TimeUnit.MINUTES);
		}
		if (count > requestLimit.value()) {
			throw new RequestTooFrequentException();
		}
	}
}
