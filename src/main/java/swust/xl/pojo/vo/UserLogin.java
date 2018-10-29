package swust.xl.pojo.vo;

/**
 * 用户登陆请求体
 * 
 * @author xuLiang
 * @since 0.0.1
 */
public class UserLogin {
	private Long id;
	private String password;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
