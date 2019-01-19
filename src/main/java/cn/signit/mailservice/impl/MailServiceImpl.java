package cn.signit.mailservice.impl;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import cn.signit.mailservice.MailService;

/**
 * 
 * 邮件发送实现类
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
		message.setFrom(from);// 设置发送人
		message.setTo(to);// 设置收件人
		message.setSubject(subject);// 设置主题
		message.setText(content);// 设置内容
		try {
			mailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendHtmlEmail(String to, String subject, String content) {
		MimeMessage message = mailSender.createMimeMessage();// 创建一个MINE消息
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);// true表示需要创建一个multipart message
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(content, true);
			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendAttachmentsEmail(String to, String subject, String content, String filePath) {
		MimeMessage message = mailSender.createMimeMessage();// 创建一个MINE消息
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(content, true);// true表示这个邮件是有附件的
			FileSystemResource file = new FileSystemResource(new File(filePath));// 创建文件系统资源
			String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
			helper.addAttachment(fileName, file);// 添加附件
			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendInlineResourceEmail(String to, String subject, String content, String rscPath, String rscId) {
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(content, true);
			FileSystemResource res = new FileSystemResource(new File(rscPath));// 添加内联资源，一个id对应一个资源，最终通过id来找到该资源
			helper.addInline(rscId, res);// 添加多个图片可以使用多条 <img src='cid:" + rscId + "' > 和 helper.addInline(rscId, res)
											// 来实现
			mailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}