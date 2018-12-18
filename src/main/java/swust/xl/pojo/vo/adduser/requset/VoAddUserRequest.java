package swust.xl.pojo.vo.adduser.requset;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import org.springframework.lang.Nullable;

/**
 * 添加用户请求体.
 *
 * @author xuLiang
 * @since 1.0.0
 */
public class VoAddUserRequest {

	@NotBlank(message = "用户名不能为空")
	private String userName;
	@NotBlank(message = "密码不能为空")
	private String password;
	@NotBlank(message = "邮箱不能为空")
	@Email(message = "邮箱格式错误")
	private String email;
	@Nullable
	private String sex;

	@Override
	public String toString() {
		return "VoAddUserRequest [userName=" + userName + ", password=" + password + ", email=" + email + ", sex=" + sex
				+ "]";
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
