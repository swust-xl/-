package swust.xl.pojo.vo;

import swust.xl.pojo.vo.getuser.response.VoGetUserResp;

/**
 * 查询用户统一响应体.
 *
 * @author xuLiang
 * @since 1.0.0
 */
public class GetUserResp {

	private int code;
	private String message;
	private VoGetUserResp data;

	public GetUserResp(int code, String message, VoGetUserResp data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public GetUserResp() {
	}

	@Override
	public String toString() {
		return "GetUserResp [code=" + code + ", message=" + message + ", data=" + data + "]";
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

	public VoGetUserResp getData() {
		return data;
	}

	public void setData(VoGetUserResp data) {
		this.data = data;
	}

}
