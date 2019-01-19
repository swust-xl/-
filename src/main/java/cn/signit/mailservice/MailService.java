package cn.signit.mailservice;

/**
 * 
 * 邮件发送接口
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

	/**
	 * 发送html格式邮件
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
	public void sendHtmlEmail(String to, String subject, String content);

	/**
	 * 发送带附件的邮件
	 * 
	 * @param to
	 *            发送到的地址
	 * @param subject
	 *            邮件主题
	 * @param content
	 *            邮件正文
	 * @param filePath
	 *            文件路径
	 * @author xuLiang
	 * @since 1.0.0
	 */
	public void sendAttachmentsEmail(String to, String subject, String content, String filePath);

	/**
	 * 发送带静态资源的邮件
	 * 
	 * @param to
	 *            发送到的地址
	 * @param subject
	 *            邮件主题
	 * @param content
	 *            邮件正文
	 * @param rscPath
	 *            静态资源路径
	 * @param rscId
	 *            静态资源名称
	 * @author xuLiang
	 * @since 1.0.0
	 */
	public void sendInlineResourceEmail(String to, String subject, String content, String rscPath, String rscId);
}
