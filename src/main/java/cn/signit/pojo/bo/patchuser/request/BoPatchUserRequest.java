package cn.signit.pojo.bo.patchuser.request;

/**
 * BO层更新用户请求体
 * 
 * @author xuLiang
 * @since 1.0.0
 */
public class BoPatchUserRequest {
	private String userName;
	private String password;
	private String email;
	private String sex;
	private String salt;

	@Override
	public String toString() {
		return "BoPatchUserRequest [userName=" + userName + ", password=" + password + ", email=" + email + ", sex="
				+ sex + ", salt=" + salt + "]";
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
