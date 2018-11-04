package swust.xl.pojo.vo.getuser.response;

/**
 * 查询用户控制器响应数据.
 *
 * @author xuLiang
 * @since 1.0.0
 */
public class VoGetUserResponse {
	private Long id;
	private String username;
	private String sex;
	private int isSystem;
	private String salt;
	private String passwordSalt;
	private String registDatetime;
	private String lastLoginDatetime;

	@Override
	public String toString() {
		return "VoGetUserResponse [id=" + id + ", username=" + username + ", sex=" + sex + ", isSystem=" + isSystem
				+ ", salt=" + salt + ", passwordSalt=" + passwordSalt + ", registDatetime=" + registDatetime
				+ ", lastLoginDatetime=" + lastLoginDatetime + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public int getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(int isSystem) {
		this.isSystem = isSystem;
	}

	public String getPasswordSalt() {
		return passwordSalt;
	}

	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}

	public String getRegistDatetime() {
		return registDatetime;
	}

	public void setRegistDatetime(String registDatetime) {
		this.registDatetime = registDatetime;
	}

	public String getLastLoginDatetime() {
		return lastLoginDatetime;
	}

	public void setLastLoginDatetime(String lastLoginDatetime) {
		this.lastLoginDatetime = lastLoginDatetime;
	}

}