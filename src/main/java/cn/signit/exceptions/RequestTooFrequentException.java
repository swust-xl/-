package cn.signit.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * 
 * 请求过于频繁异常
 * 
 * @author xuLiang
 * @since 1.0.0
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "请求过于频繁")
public class RequestTooFrequentException extends RuntimeException {

	private static final long serialVersionUID = 396483021624727726L;

	public RequestTooFrequentException() {
		super();
	}

	public RequestTooFrequentException(String message) {
		super(message);
	}

	public RequestTooFrequentException(String message, Throwable cause) {
		super(message, cause);
	}

	public RequestTooFrequentException(Throwable cause) {
		super(cause);
	}

	protected RequestTooFrequentException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}