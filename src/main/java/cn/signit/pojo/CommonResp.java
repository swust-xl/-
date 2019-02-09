package cn.signit.pojo;

/**
 * 通用响应体
 * 
 * @author xuLiang
 * @since 1.0.0
 */
public class CommonResp<T> {

	private int code;
	private String message;
	private T data;

	public CommonResp() {
		super();
	}

	public CommonResp(int code, String message, T data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}

	@Override
	public String toString() {
		return "CommonResp [code=" + code + ", message=" + message + ", data=" + data + "]";
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}