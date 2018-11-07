package swust.xl.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import swust.xl.pojo.vo.VerificationCodeResp;

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
	VerificationCodeResp sendImage();

	/**
	 * 验证接口
	 * 
	 * @return String
	 * @author xuLiang
	 * @throws Exception
	 */
	ResponseEntity<Object> coordinateVerify();
}
