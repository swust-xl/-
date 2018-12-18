package swust.xl.pojo.vo.patchuser.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import org.springframework.lang.Nullable;

/**
 * 更新用户请求体
 *
 * 
 * @author xuLiang
 * @since 1.0.0
 */
public class VoPatchUserRequest {

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

	@Override
	public String toString() {
		return "VoPatchUserRequest [userName=" + userName + ", oldPassword=" + oldPassword + ", newPassword="
				+ newPassword + ", email=" + email + ", sex=" + sex + "]";
	}

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

}
