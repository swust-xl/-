package swust.xl.pojo.vo.adduser.requset;

/**
 * 添加用户请求体.
 *
 * @author xuLiang
 * @since 0.0.1
 */
public class VoAddUserRequest {
	private String username;
	private String password;
	private String sex;

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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

}
