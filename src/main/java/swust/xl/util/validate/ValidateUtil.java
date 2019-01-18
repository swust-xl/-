package swust.xl.util.validate;

import org.springframework.stereotype.Component;

import swust.xl.pojo.vo.behavior.verification.BehaviorVerificationData;

/**
 * 验证处理相关工具类
 * 
 * @author xuLiang
 * @since 1.0.0
 */
@Component
public class ValidateUtil {
	/**
	 * 判断用户是否是机器人;
	 * 
	 * @param data
	 *            数据集合
	 * @return true-是机器人f；false-不是机器人
	 */
	public boolean isRobot(BehaviorVerificationData<Integer>[] data) {
		boolean robot = false;
		int flag = 0;
		for (int i = 0; i < data.length - 2; i++) {
			/**
			 * 匀变速运动基本公式： ①v=v0+at ②s=v0t+½at² ③v²-v0²=2as
			 */
			double velocity = (data[i + 1].getX()
					- data[i].getX() / (data[i + 1].getTimestamp() - data[i].getTimestamp()));
			double nextVelocity = (data[i + 2].getX()
					- data[i + 1].getX() / (data[i + 2].getTimestamp() - data[i + 1].getTimestamp()));
			double acceleration = velocity / (data[i + 1].getTimestamp() - data[i].getTimestamp());
			double nextAcceleration = nextVelocity / (data[i + 2].getTimestamp() - data[i + 1].getTimestamp());
			double lastHalfVelocity = (data[data.length - 1].getX() - data[(data.length - 1) / 2].getX()
					/ (data[data.length - 1].getTimestamp() - data[(data.length - 1) / 2].getTimestamp()));
			double firstHalfVelocity = (data[(data.length - 1) / 2].getX()
					- data[0].getX() / (data[(data.length - 1) / 2].getTimestamp() - data[0].getTimestamp()));
			// 速度差值
			double velocityDifference = Math.abs(nextVelocity - velocity);
			// 加速度差值
			double accelerationDifference = Math.abs(nextAcceleration - acceleration);
			if (accelerationDifference == 0) {
				robot = true;
				break;
			}
			if (firstHalfVelocity > lastHalfVelocity || data[i + 1].getY().equals(data[i].getY())
					|| velocityDifference == 0 || accelerationDifference == 0) {
				flag++;
			}
		}
		if (robot || flag > 3) {
			return true;
		}
		return false;
	}

	/**
	 * 判断用户是否通过验证
	 * 
	 * @param valueFromSession
	 *            存放在session中的值
	 * @param valueFromUser
	 *            用户提交的值
	 * @param valueToValidate
	 *            用于判断的临界值
	 * @return true-通过验证；false-不通过
	 * @author xuLiang
	 * @since 1.0.0
	 */
	public boolean validate(Object valueFromSession, String valueFromUser, int valueToValidate) {
		if ((Math.abs(
				(Integer.valueOf(valueFromSession.toString()) - Integer.valueOf(valueFromUser))) > valueToValidate)) {
			return false;
		}
		return true;
	}
}
