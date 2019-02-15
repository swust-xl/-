package cn.signit.util.validate;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import cn.signit.pojo.vo.behavior.verification.BehaviorVerificationData;

/**
 * 验证处理相关工具类
 * 
 * @author xuLiang
 * @since 1.0.0
 */
@Component
@SuppressWarnings("unused")
public class ValidateUtil {

	/**
	 * 判断用户是否是机器人
	 * 
	 * @param data
	 *            数据集合
	 * @return true-是机器人f；false-不是机器人
	 * @author xuLiang
	 * @since 1.0.0
	 * @deprecated doule精度丢失,改用BigDecimal则没有此问题,replaced by
	 *             {@code cn.signit.util.validate.ValidateUtil.judgeRobot(BehaviorVerificationData<Integer>[] data)}
	 */
	@Deprecated
	public boolean judgeRobot(BehaviorVerificationData<Integer>[] data) {
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
	 * 判断用户是否是机器人
	 * 
	 * @param data
	 *            数据集合
	 * @return true-是机器人f；false-不是机器人
	 * @author xuLiang
	 * @since 1.0.0
	 */
	public boolean isRobot(BehaviorVerificationData<Integer>[] data) {
		boolean robot = false;
		int flag = 0;
		for (int i = 0; i < data.length - 2; i++) {
			double velocity = div(data[i + 1].getX() - data[i].getX(),
					data[i + 1].getTimestamp() - data[i].getTimestamp(), 1);
			double nextVelocity = div(data[i + 2].getX() - data[i + 1].getX(),
					data[i + 2].getTimestamp() - data[i + 1].getTimestamp(), 1);
			double acceleration = div(velocity, data[i + 1].getTimestamp() - data[i].getTimestamp(), 1);
			double nextAcceleration = div(nextVelocity, data[i + 2].getTimestamp() - data[i + 1].getTimestamp(), 1);
			double lastHalfVelocity = div(data[data.length - 1].getX() - data[(data.length - 1) / 2].getX(),
					data[data.length - 1].getTimestamp() - data[(data.length - 1) / 2].getTimestamp(), 1);
			double firstHalfVelocity = div(data[(data.length - 1) / 2].getX() - data[0].getX(),
					data[(data.length - 1) / 2].getTimestamp() - data[0].getTimestamp(), 1);
			double velocityDifference = Math.abs(sub(nextVelocity, velocity));
			double accelerationDifference = Math.abs(sub(nextAcceleration, acceleration));
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
	 * BigDecimal类型的加减乘除,使用BigDecimal代替double避免精度丢失
	 * 
	 * @author xuLiang
	 * @since 1.0.0
	 */
	private static double add(double d1, double d2) {
		BigDecimal p1 = new BigDecimal(Double.toString(d1));
		BigDecimal p2 = new BigDecimal(Double.toString(d2));
		return p1.add(p2).doubleValue();
	}

	private static double sub(double d1, double d2) {
		BigDecimal p1 = new BigDecimal(Double.toString(d1));
		BigDecimal p2 = new BigDecimal(Double.toString(d2));
		return p1.subtract(p2).doubleValue();
	}

	private static double mul(double d1, double d2) {
		BigDecimal p1 = new BigDecimal(Double.toString(d1));
		BigDecimal p2 = new BigDecimal(Double.toString(d2));
		return p1.multiply(p2).doubleValue();
	}

	private static double div(double d1, double d2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("Parameter error");
		}
		BigDecimal p1 = new BigDecimal(Double.toString(d1));
		BigDecimal p2 = new BigDecimal(Double.toString(d2));
		return p1.divide(p2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
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
		if (valueFromSession == null || valueFromUser == null) {
			throw new NullPointerException();
		}
		if ((Math.abs(
				(Integer.valueOf(valueFromSession.toString()) - Integer.valueOf(valueFromUser))) > valueToValidate)) {
			return false;
		}
		return true;
	}

}