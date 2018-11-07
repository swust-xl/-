package swust.xl.mailservice;

/**
 * 
 * 邮件发送接口
 * </p>
 *
 * @author xuLiang
 * @since 1.0.0
 */
public interface MailService {
	/**
	 * 发送简易邮件
	 * 
	 * @param to
	 *            发送到的地址
	 * @param subject
	 *            邮件主题
	 * @param content
	 *            邮件正文
	 * @author xuLiang
	 * @since 1.0.0
	 */
	public void sendSimpleMail(String to, String subject, String content);
}
