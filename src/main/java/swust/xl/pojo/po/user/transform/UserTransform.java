package swust.xl.pojo.po.user.transform;

/**
 * PO层和VO层不同类型属性之间转换
 *
 * @author xuLiang
 * @since 1.0.0
 */
public class UserTransform {
	/**
	 * 将byte类数据转换成String类
	 * 
	 * @param inPutBytes
	 *            待转换的属性数据
	 * @return 转换后的属性数据
	 * @author xuLiang
	 * @since 1.0.0
	 */
	public String byteToString(byte[] inPutBytes) {
		if (inPutBytes == null) {
			return null;
		}
		String output = new String(inPutBytes);
		return output;

	}
}
