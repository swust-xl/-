package swust.xl.pojo.vo.verify.response;

public class VerifyResp {

	private int code;
	private String message;
	private String source;

	@Override
	public String toString() {
		return "OpenApiResp [code=" + code + ", message=" + message + "]";
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
