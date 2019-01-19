package cn.signit.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import cn.signit.pojo.vo.behavior.verification.BehaviorVerificationReq;
import cn.signit.pojo.vo.user.preprocess.UserPreprocess;

/**
 * 验证相关controller
 * 
 * @author xuLiang
 * @since 1.0.0
 */
@Validated
public interface WebController {
	/**
	 * 第三方调用验证预处理
	 * 
	 * @param userLogin
	 * @return ResponseEntity<Object>
	 * @author xuLiang
	 * @since 1.0.0
	 */
	public ResponseEntity<Object> preprocess(@Valid @NotNull UserPreprocess user);

	/**
	 * 获取图片接口
	 * 
	 * @return VerificationCodeResp
	 * @author xuLiang
	 * @throws Exception
	 */
	public ResponseEntity<Object> sendImage() throws Exception;

	/**
	 * 验证接口
	 * 
	 * @return String
	 * @param request
	 *            行为验证请求体
	 * @author xuLiang
	 * @throws Exception
	 */
	public ResponseEntity<Object> coordinateVerify(BehaviorVerificationReq request);
}
