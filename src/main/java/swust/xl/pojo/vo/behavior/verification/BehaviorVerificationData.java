package swust.xl.pojo.vo.behavior.verification;

/**
 * 用于分析用户行为特征的数据
 * 
 * @author xuLiang
 * @since 1.0.0
 */
public class BehaviorVerificationData {

	private String x;
	private String y;
	private Long timestamp;

	@Override
	public String toString() {
		return "BehaviorVerificationReq [x=" + x + ", y=" + y + ", time=" + timestamp + "]";
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

}
