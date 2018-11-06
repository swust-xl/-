package swust.xl.pojo.vo.getuser.commonresponse;

/**
 * 返回部分用户信息响应体
 * 
 * @author xuLiang
 * @since 1.0.0
 */
public class VoGetUserCommonResp {

	private String username;
	private int isSystem;
	private String lastLoginDatetime;

	@Override
	public String toString() {
		return "UserCommonResp [username=" + username + ", isSystem=" + isSystem + ", lastLoginDatetime="
				+ lastLoginDatetime + "]";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(int isSystem) {
		this.isSystem = isSystem;
	}

	public String getLastLoginDatetime() {
		return lastLoginDatetime;
	}

	public void setLastLoginDatetime(String lastLoginDatetime) {
		this.lastLoginDatetime = lastLoginDatetime;
	}
}
