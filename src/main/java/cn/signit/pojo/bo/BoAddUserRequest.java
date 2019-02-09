package cn.signit.pojo.bo;

import java.sql.Timestamp;

/**
 * 服务层添加用户请求.
 * 
 * @author xuLiang
 * @since 1.0.0
 */
public class BoAddUserRequest {
	private String userName;
	private String password;
	private String email;
	private String sex;
	private String salt;
	private Timestamp registDatetime;

	@Override
	public String toString() {
		return "BoAddUserRequest [userName=" + userName + ", password=" + password + ", email=" + email + ", sex=" + sex
				+ ", salt=" + salt + ", registDatetime=" + registDatetime + "]";
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

	public Timestamp getRegistDatetime() {
		return registDatetime;
	}

	public void setRegistDatetime(Timestamp registDatetime) {
		this.registDatetime = registDatetime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
