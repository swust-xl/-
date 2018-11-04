package swust.xl.mailservice.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import swust.xl.mailservice.MailService;

/**
 * 
 * 邮件发送实现类
 * </p>
 *
 * @author xuLiang
 * @since 1.0.0
 */
@Component
public class MailServiceImpl implements MailService {

	@Autowired
	private JavaMailSender mailSender;

	@Value("${mail.fromMail.addr}")
	private String from;

	@Override
	public void sendSimpleMail(String to, String subject, String content) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(content);
		try {
			mailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}