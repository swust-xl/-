package swust.xl.pojo.vo.user.preprocess;

/**
 * 用户预处理请求体
 * 
 * @author xuLiang
 * @since 1.0.0
 */
public class UserPreprocess {

	private String userName;
	private String password;

	@Override
	public String toString() {
		return "UserPreprocess [userName=" + userName + ", password=" + password + "]";
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
