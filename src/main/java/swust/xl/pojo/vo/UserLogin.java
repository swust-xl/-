package swust.xl.pojo.vo;

/**
 * 用户登陆请求体
 * 
 * @author xuLiang
 * @since 0.0.1
 */
public class UserLogin {
	private String username;
	private String password;

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
