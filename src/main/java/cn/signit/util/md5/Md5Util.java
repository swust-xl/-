package cn.signit.util.md5;

import java.security.MessageDigest;
import java.util.Random;

import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Component;

/**
 * md5加密工具类
 * 
 * @author xuLiang
 * @since 1.0.0
 */
@Component
public class Md5Util {

	public static final int SALT_LENGTH = 10;

	/**
	 * 随机生成salt
	 */
	public static String getSalt() {
		Random r = new Random();
		StringBuilder sb = new StringBuilder(SALT_LENGTH);
		sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
		int len = sb.length();
		if (len < SALT_LENGTH) {
			for (int i = 0; i < SALT_LENGTH - len; i++) {
				sb.append("0");
			}
		}
		return sb.toString();
	}

	/**
	 * 获取十六进制字符串形式的MD5摘要
	 */
	public static String md5Hex(String src) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] bs = md5.digest(src.getBytes());
			return new String(new Hex().encode(bs));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
