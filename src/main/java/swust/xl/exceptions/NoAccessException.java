package swust.xl.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "拒绝访问")
public class NoAccessException extends RuntimeException {

	private static final long serialVersionUID = -1963478108563445342L;

	public NoAccessException() {
		super();
	}

	public NoAccessException(String message) {
		super(message);
	}

	public NoAccessException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoAccessException(Throwable cause) {
		super(cause);
	}

	protected NoAccessException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
