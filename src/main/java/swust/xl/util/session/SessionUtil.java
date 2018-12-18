package swust.xl.util.session;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

/**
 * session工具类
 * 
 * @author xuLiang
 * @since 1.0.0
 */
@Component
public class SessionUtil {
	/**
	 * 设置倒计时删除session中指定的值
	 * 
	 * @param session
	 *            指定session
	 * @param attributeName
	 *            指定session中的值
	 * @param time
	 *            指定时间(秒)
	 * @author xuLiang
	 * @since 1.0.0
	 */
	public void removeAttribute(HttpSession session, String attributeName, int time) {
		ThreadFactory threadFactory = new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r, "清除Attribute线程");
				return t;
			}
		};
		ScheduledThreadPoolExecutor scheduled = new ScheduledThreadPoolExecutor(2, threadFactory);
		scheduled.allowsCoreThreadTimeOut();
		scheduled.setKeepAliveTime(90, TimeUnit.SECONDS);
		scheduled.schedule(new Runnable() {
			@Override
			public void run() {
				session.removeAttribute(attributeName);
			}
		}, time, TimeUnit.SECONDS);
	}

	/**
	 * 查找指定attribute在session中是否存在
	 * 
	 * @param session
	 *            指定session
	 * @param attributeName
	 *            指定attribute名称
	 * @return true-存在；false-不存在
	 * @author xuLiang
	 * @since 1.0.0
	 */
	public boolean checkAttribute(HttpSession session, String attributeName) {
		if (session == null || session.getAttribute(attributeName) == null) {
			return false;
		}
		return true;
	}

	/**
	 * 验证session中的值与指定值是否相等
	 * 
	 * @param session
	 *            指定session
	 * @param attributeName
	 *            指定attribute名称
	 * @param value
	 *            指定attribute值
	 * @return boolean
	 * @author xuLiang
	 * @since 1.0.0
	 */
	public boolean verifyAttribute(HttpSession session, String attributeName, Object value) {
		if (session.getAttribute(attributeName).equals(value)) {
			return true;
		}
		return false;
	}
}