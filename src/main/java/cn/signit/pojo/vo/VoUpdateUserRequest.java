package cn.signit.pojo.vo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

/**
 * 更新用户请求体
 *
 * 
 * @author xuLiang
 * @since 1.0.0
 */
public class VoUpdateUserRequest {

	@NotNull
	private Long id;
	@NotBlank(message = "用户名不能为空")
	private String userName;
	@NotBlank(message = "旧密码不能为空")
	private String oldPassword;
	@NotBlank(message = "新密码不能为空")
	private String newPassword;
	@NotBlank(message = "邮箱不能为空")
	@Email(message = "邮箱格式错误")
	private String email;
	@Nullable
	private String sex;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
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
		builder.append("VoUpdateUserRequest [id=");
		builder.append(id);
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", oldPassword=");
		builder.append(oldPassword);
		builder.append(", newPassword=");
		builder.append(newPassword);
		builder.append(", email=");
		builder.append(email);
		builder.append(", sex=");
		builder.append(sex);
		builder.append("]");
		return builder.toString();
	}

}