package cn.signit.util.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import cn.signit.pojo.vo.GetUserCommonResp;
import cn.signit.pojo.vo.GetUserResp;
import cn.signit.pojo.vo.error.response.VoErrorResp;
import cn.signit.pojo.vo.getuser.commonresponse.VoGetUserCommonResp;
import cn.signit.pojo.vo.getuser.response.VoGetUserResp;
import cn.signit.pojo.vo.verify.response.VerifyResp;

/**
 * 响应工具类
 * 
 * @author xuLiang
 * @since 1.0.0
 */
@Component
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
	 * @return ResponseEntity<Object>响应体和http状态码
	 * @author xuLiang
	 * @since 1.0.0
	 */
	public ResponseEntity<Object> errorResp(HttpStatus httpStatus, int code, String message) {
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
	public ResponseEntity<Object> commonResp(HttpStatus httpStatus, int code, String message, String sessionKey,
			VoGetUserCommonResp data) {
		GetUserCommonResp response = new GetUserCommonResp();
		response.setCode(code);
		response.setMessage(message);
		response.setSessionKey(sessionKey);
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
	public ResponseEntity<Object> getUserResp(HttpStatus httpStatus, int code, String message, VoGetUserResp data) {
		GetUserResp response = new GetUserResp();
		response.setCode(code);
		response.setMessage(message);
		response.setData(data);
		return new ResponseEntity<Object>(response, httpStatus);
	}

	/**
	 * 验证结果响应
	 * 
	 * @param httpStatus
	 *            http响应码
	 * @param code
	 *            响应码
	 * @param message
	 *            提示消息
	 * @param source
	 *            请求来源
	 * @return ResponseEntity<Object>
	 * @author xuLiang
	 * @since 1.0.0
	 */
	public ResponseEntity<Object> verifyResp(HttpStatus httpStatus, int code, String message, String source) {
		VerifyResp response = new VerifyResp();
		response.setCode(code);
		response.setMessage(message);
		response.setSource(source);
		return new ResponseEntity<Object>(response, httpStatus);
	}

}
