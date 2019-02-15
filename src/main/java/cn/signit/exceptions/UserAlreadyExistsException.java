package cn.signit.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * 用户已存在异常
 * 
 * @author xuLiang
 * @since 1.0.0
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "用户名或邮箱已存在")
public class UserAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 7878623114764784593L;

	public UserAlreadyExistsException() {
		super();
	}

	public UserAlreadyExistsException(String message) {
		super(message);
	}

	public UserAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserAlreadyExistsException(Throwable cause) {
		super(cause);
	}

}