package swust.xl.util.session;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpSession;

/**
 * session工具类
 * 
 * @author xuLiang
 * @since 1.0.0
 */
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
	public static void removeAttribute(HttpSession session, String attributeName, int time) {
		ThreadFactory threadFactory = new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r, "销毁session值线程");
				return t;
			}
		};
		ScheduledThreadPoolExecutor scheduled = new ScheduledThreadPoolExecutor(2, threadFactory);
		scheduled.schedule(new Runnable() {
			@Override
			public void run() {
				session.removeAttribute(attributeName);
			}
		}, time, TimeUnit.SECONDS);

	}
}
