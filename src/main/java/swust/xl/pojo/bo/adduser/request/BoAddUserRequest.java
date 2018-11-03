package swust.xl.pojo.bo.adduser.request;

import java.sql.Timestamp;

/**
 * 服务层添加用户请求.
 * 
 * 
 * @author xuLiang
 * @since 0.0.1
 */
public class BoAddUserRequest {
	private String username;
	private String password;
	private String sex;
	private String salt;
	private Timestamp registDatetime;
	 

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

	public Timestamp getRegistDatetime() {
		return registDatetime;
	}

	public void setRegistDatetime(Timestamp registDatetime) {
		this.registDatetime = registDatetime;
	}

}
