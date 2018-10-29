package swust.xl.mailservice;

/**
 * 
 * 邮件发送接口
 * </p>
 *
 * @author xuLiang
 * @since 0.0.1
 */
public interface MailService {
	public void sendSimpleMail(String to, String subject, String content);
}
