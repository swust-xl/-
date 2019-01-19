package cn.signit.pojo.vo;

/**
 * 
 * 获取图片响应体
 * 
 * @author xuLiang
 * @since 1.0.0
 */
public class GetImgResp {
	private int code;
	private String message;
	private VerificationCodeResp data;

	@Override
	public String toString() {
		return "GetImgResp [code=" + code + ", message=" + message + ", data=" + data + "]";
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

	public VerificationCodeResp getData() {
		return data;
	}

	public void setData(VerificationCodeResp data) {
		this.data = data;
	}
}
