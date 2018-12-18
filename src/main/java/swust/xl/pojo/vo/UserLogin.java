package swust.xl.pojo.vo;

/**
 * 用户登陆请求体
 * 
 * @author xuLiang
 * @since 1.0.0
 */
public class UserLogin {
	private String usernameOrEmail;
	private String password;

	public UserLogin() {
		this(null, null);
	}

	public UserLogin(String usernameOrEmail, String password) {
		this.usernameOrEmail = usernameOrEmail;
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserLogin [usernameOrEmail=" + usernameOrEmail + ", password=" + password + "]";
	}

	public String getUsernameOrEmail() {
		return usernameOrEmail;
	}

	public void setUsernameOrEmail(String usernameOrEmail) {
		this.usernameOrEmail = usernameOrEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
