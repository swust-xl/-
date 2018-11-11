package swust.xl.pojo.vo.behavior.verification;

import java.util.Arrays;

/**
 * 行为验证请求体
 * 
 * @author xuLiang
 * @since 1.0.0
 */
public class BehaviorVerificationReq {

	private BehaviorVerificationData[] data;
	private String userResult;

	@Override
	public String toString() {
		return "BehaviorVerificationReq [data=" + Arrays.toString(data) + ", count=" + userResult + "]";
	}

	public BehaviorVerificationData[] getData() {
		return data;
	}

	public void setData(BehaviorVerificationData[] data) {
		this.data = data;
	}

	public String getUserResult() {
		return userResult;
	}

	public void setUserResult(String userResult) {
		this.userResult = userResult;
	}

}
