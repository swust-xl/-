package swust.xl.util.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import swust.xl.pojo.vo.GetUserCommonResp;
import swust.xl.pojo.vo.GetUserResp;
import swust.xl.pojo.vo.VoErrorResp;
import swust.xl.pojo.vo.getuser.commonresponse.VoGetUserCommonResp;
import swust.xl.pojo.vo.getuser.response.VoGetUserResp;
import swust.xl.pojo.vo.openapi.OpenApiResp;

/**
 * 响应工具类
 * 
 * @author xuLiang
 * @since 1.0.0
 */
public class ResponseUtil {
	/**
	 * 错误响应
	 * 
	 * @param httpStatus
	 *            http响应码
	 * @param code
	 *            响应码
	 * @param message
	 *            提示消息
	 * @param error
	 *            错误相关信息
	 * @return ResponseEntity<Object>响应体和http状态码
	 * @author xuLiang
	 * @since 1.0.0
	 */
	public static ResponseEntity<Object> errorResp(HttpStatus httpStatus, int code, String message) {
		VoErrorResp errorResp = new VoErrorResp();
		errorResp.setCode(code);
		errorResp.setMessage(message);
		return new ResponseEntity<Object>(errorResp, httpStatus);
	}

	/**
	 * 正常响应
	 * 
	 * @param httpStatus
	 *            http响应码
	 * @param code
	 *            响应码
	 * @param message
	 *            提示消息
	 * @param data
	 *            返回信息
	 * @return ResponseEntity<Object>响应体和http状态码
	 * @author xuLiang
	 * @since 1.0.0
	 */
	public static ResponseEntity<Object> commonResp(HttpStatus httpStatus, int code, String message,
			VoGetUserCommonResp data) {
		GetUserCommonResp response = new GetUserCommonResp();
		response.setCode(code);
		response.setMessage(message);
		response.setData(data);
		return new ResponseEntity<Object>(response, httpStatus);
	}

	/**
	 * 获取用户完整信息响应
	 * 
	 * @param httpStatus
	 *            http响应码
	 * @param code
	 *            响应码
	 * @param message
	 *            提示消息
	 * @param data
	 *            返回信息
	 * @return ResponseEntity<Object>响应体和http状态码
	 * @author xuLiang
	 * @since 1.0.0
	 */
	public static ResponseEntity<Object> getUserResp(HttpStatus httpStatus, int code, String message,
			VoGetUserResp data) {
		GetUserResp response = new GetUserResp();
		response.setCode(code);
		response.setMessage(message);
		response.setData(data);
		return new ResponseEntity<Object>(response, httpStatus);
	}

	/**
	 * 开放平台用户响应
	 * 
	 * @param httpStatus
	 *            http响应码
	 * @param code
	 *            响应码
	 * @param message
	 *            提示消息
	 * @return ResponseEntity<Object>
	 * @author xuLiang
	 * @since 1.0.0
	 */
	public static ResponseEntity<Object> openApiResp(HttpStatus httpStatus, int code, String message) {
		OpenApiResp response = new OpenApiResp();
		response.setCode(code);
		response.setMessage(message);
		return new ResponseEntity<Object>(response, httpStatus);
	}
}
