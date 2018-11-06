package swust.xl.util.session;

import java.util.Timer;
import java.util.TimerTask;

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
	 * @param attrName
	 *            指定session中的值
	 * @param time
	 *            指定时间(秒)
	 * @author xuLiang
	 * @since 1.0.0
	 */
	public static void removeAttribute(HttpSession session, String attrName, int time) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				session.removeAttribute(attrName);
				timer.cancel();
			}
		}, time * 1000);
	}

}
