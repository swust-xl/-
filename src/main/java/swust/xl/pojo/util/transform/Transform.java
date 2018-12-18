package swust.xl.pojo.util.transform;

import java.sql.Timestamp;

/**
 * 不同类型变量之间转换
 *
 * @author xuLiang
 * @since 1.0.0
 */
public class Transform {
	/**
	 * 数据库0,1转换成false和true
	 * 
	 * @param inPutBytes
	 *            待转换的属性数据
	 * @return 转换后的属性数据
	 * @author xuLiang
	 * @since 1.0.0
	 */
	public String ByteToString(Byte inPutBytes) {
		if (inPutBytes == null || inPutBytes == 0) {
			return "false";
		}
		return "true";
	}

	/**
	 * 将timestamp类数据转换成Long
	 * 
	 * @param timestamp
	 *            待转换的属性数据
	 * @return 转换后的属性数据
	 * @author xuLiang
	 * @since 1.0.0
	 */
	public Long TimestampToLong(Timestamp timestamp) {
		if (timestamp == null) {
			return null;
		}
		Long outPut = timestamp.getTime();
		return outPut;
	}

}
