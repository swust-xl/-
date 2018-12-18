package swust.xl.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

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

	protected UserAlreadyExistsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
