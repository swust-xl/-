package swust.xl.pojo.vo;

import swust.xl.pojo.vo.getuser.commonresponse.VoGetUserCommonResp;

/**
 * 请求正常响应体
 * 
 * @author xuLiang
 * @since 1.0.0
 */
public class GetUserCommonResp {
	private int code;
	private String message;
	private VoGetUserCommonResp data;

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

	public VoGetUserCommonResp getData() {
		return data;
	}

	public void setData(VoGetUserCommonResp data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "GetUserCommonResp [code=" + code + ", message=" + message + ", data=" + data + "]";
	}
}
