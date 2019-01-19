package cn.signit.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * 
 * 用户信息异常
 * 
 * @author xuLiang
 * @since 1.0.0
 */
@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "用户名或密码错误")
public class UserInfoException extends RuntimeException {

	private static final long serialVersionUID = 8259722260466424303L;

	public UserInfoException() {
		super();
	}

	public UserInfoException(String message) {
		super(message);
	}

	public UserInfoException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserInfoException(Throwable cause) {
		super(cause);
	}

	protected UserInfoException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
