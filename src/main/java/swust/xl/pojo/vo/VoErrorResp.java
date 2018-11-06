package swust.xl.pojo.vo;

/**
 * 请求错误响应体
 * 
 * @author xuLiang
 * @since 1.0.0
 */
public class VoErrorResp {

	private int code;
	private String message;
	private String error;

	@Override
	public String toString() {
		return "VoErrorResp [code=" + code + ", message=" + message + ", error=" + error + "]";
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
