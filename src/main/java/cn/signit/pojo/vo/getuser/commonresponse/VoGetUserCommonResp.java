package cn.signit.pojo.vo.getuser.commonresponse;

/**
 * 返回部分用户信息响应体
 * 
 * @author xuLiang
 * @since 1.0.0
 */
public class VoGetUserCommonResp {

	private String userName;
	private String isSystem;
	private Long lastLoginDatetime;

	@Override
	public String toString() {
		return "UserCommonResp [userName=" + userName + ", isSystem=" + isSystem + ", lastLoginDatetime="
				+ lastLoginDatetime + "]";
	}

	public String getUsername() {
		return userName;
	}

	public void setUsername(String userName) {
		this.userName = userName;
	}

	public String getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(String isSystem) {
		this.isSystem = isSystem;
	}

	public Long getLastLoginDatetime() {
		return lastLoginDatetime;
	}

	public void setLastLoginDatetime(Long lastLoginDatetime) {
		this.lastLoginDatetime = lastLoginDatetime;
	}
}
