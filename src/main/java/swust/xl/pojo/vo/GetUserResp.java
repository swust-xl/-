package swust.xl.pojo.vo;

import swust.xl.pojo.vo.getuser.response.VoGetUserResponse;

/**
 * 查询用户统一响应体.
 *
 *
 * @author xuLiang
 * @since 1.0.0
 */
public class GetUserResp {

	private int code;
	private String message;
	private VoGetUserResponse data;

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

	public VoGetUserResponse getData() {
		return data;
	}

	public void setData(VoGetUserResponse data) {
		this.data = data;
	}

}
