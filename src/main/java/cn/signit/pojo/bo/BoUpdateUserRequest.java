package cn.signit.pojo.bo;

/**
 * BO层更新用户请求体
 * 
 * @author xuLiang
 * @since 1.0.0
 */
public class BoUpdateUserRequest {

	private Long id;
	private String userName;
	private String password;
	private String email;
	private String sex;
	private String salt;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BoUpdateUserRequest [id=");
		builder.append(id);
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", password=");
		builder.append(password);
		builder.append(", email=");
		builder.append(email);
		builder.append(", sex=");
		builder.append(sex);
		builder.append(", salt=");
		builder.append(salt);
		builder.append("]");
		return builder.toString();
	}

}
