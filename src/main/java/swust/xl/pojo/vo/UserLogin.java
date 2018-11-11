package swust.xl.pojo.vo;

/**
 * 用户登陆请求体
 * 
 * @author xuLiang
 * @since 1.0.0
 */
public class UserLogin {
	private String username;
	private String password;

	public UserLogin(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserLogin [username=" + username + ", password=" + password + "]";
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
