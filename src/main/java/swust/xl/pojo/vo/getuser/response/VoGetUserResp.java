package swust.xl.pojo.vo.getuser.response;

/**
 * 查询用户控制器响应数据.
 *
 * @author xuLiang
 * @since 1.0.0
 */
public class VoGetUserResp {
	private Long id;
	private String userName;
	private String email;
	private String sex;
	private String isSystem;
	private String salt;
	private String passwordSalt;
	private Long registDatetime;
	private Long lastLoginDatetime;

	@Override
	public String toString() {
		return "VoGetUserResp [id=" + id + ", userName=" + userName + ", email=" + email + ", sex=" + sex
				+ ", isSystem=" + isSystem + ", salt=" + salt + ", passwordSalt=" + passwordSalt + ", registDatetime="
				+ registDatetime + ", lastLoginDatetime=" + lastLoginDatetime + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return userName;
	}

	public void setUsername(String userName) {
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

	public String getPasswordSalt() {
		return passwordSalt;
	}

	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}

	public String getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(String isSystem) {
		this.isSystem = isSystem;
	}

	public Long getRegistDatetime() {
		return registDatetime;
	}

	public void setRegistDatetime(Long registDatetime) {
		this.registDatetime = registDatetime;
	}

	public Long getLastLoginDatetime() {
		return lastLoginDatetime;
	}

	public void setLastLoginDatetime(Long lastLoginDatetime) {
		this.lastLoginDatetime = lastLoginDatetime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}