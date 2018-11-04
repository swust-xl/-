package swust.xl.pojo.bo.patchuser.request;

/**
 * BO层更新用户请求体
 * 
 * @author xuLiang
 * @since 1.0.0
 */
public class BoPatchUserRequest {
	private String username;
	private String password;
	private String sex;
	private String salt;

	@Override
	public String toString() {
		return "BoPatchUserRequest [username=" + username + ", password=" + password + ", sex=" + sex + ", salt=" + salt
				+ "]";
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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

}
