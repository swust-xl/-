package swust.xl.util.validate;

import swust.xl.pojo.vo.behavior.verification.BehaviorVerificationData;

/**
 * 验证处理相关工具类
 * 
 * @author xuLiang
 * @since 1.0.0
 */
public class ValidateUtil {
	/**
	 * 判断用户是否是机器人
	 * 
	 * @param data
	 *            数据集合
	 * @param count
	 *            数据的组数
	 * @return true-是机器人f；false-不是机器人
	 */
	public static boolean isRobot(BehaviorVerificationData[] data) {
		boolean robot = false;
		int flag = 0;
		for (int i = 0; i < data.length - 2; i++) {
			double preV = (Integer.valueOf(data[i + 1].getX())
					- Integer.valueOf(data[i].getX()) / (data[i + 1].getTimestamp() - data[i].getTimestamp()));
			double V = (Integer.valueOf(data[i + 2].getX())
					- Integer.valueOf(data[i + 1].getX()) / (data[i + 2].getTimestamp() - data[i + 1].getTimestamp()));
			double preA = preV / (data[i + 1].getTimestamp() - data[i].getTimestamp());
			double A = V / (data[i + 2].getTimestamp() - data[i + 1].getTimestamp());
			if (A == preA || data[data.length - 1].getTimestamp() - data[0].getTimestamp() < 80) {
				robot = true;
				break;
			}
			if (data[i + 1].getY().equals(data[i].getY())) {
				flag++;
			}
			if (V == preV) {
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
	 * @return boolean
	 */
	public static boolean Validate(Object valueFromSession, String valueFromUser, int valueToValidate) {
		if ((Math.abs(
				(Integer.valueOf(valueFromSession.toString()) - Integer.valueOf(valueFromUser))) > valueToValidate)) {
			return false;
		}
		return true;
	}
}
