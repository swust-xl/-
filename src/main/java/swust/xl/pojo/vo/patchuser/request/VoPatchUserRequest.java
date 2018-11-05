package swust.xl.pojo.vo.patchuser.request;

/**
 * 更新用户请求体
 *
 * 
 * @author xuLiang
 * @since 1.0.0
 */
public class VoPatchUserRequest {
	private String username;
	private String password;
	private String sex;

	@Override
	public String toString() {
		return "VoPatchUserRequest [username=" + username + ", password=" + password + ", sex=" + sex + "]";
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
