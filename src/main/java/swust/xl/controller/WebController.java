package swust.xl.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import swust.xl.pojo.vo.behavior.verification.BehaviorVerificationReq;

/**
 * 验证相关controller
 * 
 * @author xuLiang
 * @since 1.0.0
 */
@Validated
public interface WebController {
	/**
	 * 获取图片接口
	 * 
	 * @return VerificationCodeResp
	 * @author xuLiang
	 * @throws Exception
	 */
	ResponseEntity<Object> sendImage() throws Exception;

	/**
	 * 验证接口
	 * 
	 * @return String
	 * @author xuLiang
	 * @throws Exception
	 */
	ResponseEntity<Object> coordinateVerify(@RequestBody BehaviorVerificationReq request);
}
