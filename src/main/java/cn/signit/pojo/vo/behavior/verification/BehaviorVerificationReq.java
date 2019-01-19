package cn.signit.pojo.vo.behavior.verification;

import java.util.Arrays;

/**
 * 行为验证请求体
 * 
 * @author xuLiang
 * @since 1.0.0
 */
public class BehaviorVerificationReq {

	private BehaviorVerificationData<Integer>[] data;
	private String lastXPosition;

	@Override
	public String toString() {
		return "BehaviorVerificationReq [data=" + Arrays.toString(data) + ", lastXPosition=" + lastXPosition + "]";
	}

	public BehaviorVerificationData<Integer>[] getData() {
		return data;
	}

	public void setData(BehaviorVerificationData<Integer>[] data) {
		this.data = data;
	}

	public String getLastXPosition() {
		return lastXPosition;
	}

	public void setLastXPosition(String lastXPosition) {
		this.lastXPosition = lastXPosition;
	}

}
