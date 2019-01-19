package cn.signit.controller.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cn.signit.controller.WebController;
import cn.signit.pojo.dto.VoMapper;
import cn.signit.pojo.vo.GetImgResp;
import cn.signit.pojo.vo.GetUserCommonResp;
import cn.signit.pojo.vo.VerificationCodeResp;
import cn.signit.pojo.vo.behavior.verification.BehaviorVerificationReq;
import cn.signit.pojo.vo.user.login.UserLogin;
import cn.signit.pojo.vo.user.preprocess.UserPreprocess;
import cn.signit.pojo.vo.verify.response.VerifyResp;
import cn.signit.service.UsersService;
import cn.signit.service.VerifyStatisticsService;
import cn.signit.service.WebService;
import cn.signit.util.response.ResponseUtil;
import cn.signit.util.session.SessionUtil;
import cn.signit.util.validate.ValidateUtil;
import io.swagger.annotations.ApiOperation;

/**
 * 验证相关controller实现类
 * 
 * @author xuLiang
 * @since 1.0.0
 */
@RestController
public class WebControllerImpl implements WebController {

	@Value("${origin.image.width}")
	private static int ORIGIN_IMAGE_WIDTH;
	@Value("${origin.image.height}")
	private static int ORIGIN_IMAGE_HEIGHT;
	@Value("${cutted.image.width}")
	private static int CUTTED_IMAGE_WIDTH;
	@Value("${cutted.image.height}")
	private static int CUTTED_IMAGE_HEIGHT;
	@Value("${picture.path}")
	private static String PICTURE_PATH;
	@Value("${allowed.deviation.range}")
	private static int ALLOWED_DEVIATION_RANGE;
	private static final String VALUE_SAVED_IN_SESSION = "value-in-session";
	private static final String USER_INVOKE_SESSION_KEY = "access-to-invoke";

	@Autowired
	private VerifyStatisticsService verifyStatisticsService;
	@Autowired
	private WebService webService;
	@Autowired
	private UsersService usersService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private ResponseUtil responseUtil;
	@Autowired
	private SessionUtil sessionUtil;
	@Autowired
	private ValidateUtil validateUtil;

	/**
	 * 第三方调用验证预处理
	 * 
	 * @param userLogin
	 * @return ResponseEntity<Object>
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@ApiOperation(value = "调用获取图片接口预处理", notes = "用户通过预处理后默认session保存30天", httpMethod = "POST", response = GetUserCommonResp.class)
	@PostMapping("/images/preprocess")
	@Override
	public ResponseEntity<Object> preprocess(@RequestBody UserPreprocess user) {
		if (!sessionUtil.checkAttribute(request.getSession(), USER_INVOKE_SESSION_KEY)) {
			if (usersService.verifyLogin(new UserLogin(user.getUserName(), user.getPassword()))) {
				request.getSession().setAttribute(USER_INVOKE_SESSION_KEY, user.getUserName());
				request.getSession().setMaxInactiveInterval(24 * 3600);
				return responseUtil.commonResp(HttpStatus.OK, 1, "用户凭证验证通过", null,
						VoMapper.INSTANCE.fromBoToVoGetUserCommonResponseMap(usersService.getUser(user.getUserName())));
			}
			return responseUtil.errorResp(HttpStatus.UNAUTHORIZED, 0, "无效的用户凭证");
		}
		return responseUtil.commonResp(HttpStatus.OK, 1, "用户凭证生效中", null,
				VoMapper.INSTANCE.fromBoToVoGetUserCommonResponseMap(usersService.getUser(user.getUserName())));
	}

	/**
	 * 获取图片接口
	 * 
	 * @return VerificationCodeResp
	 * @author xuLiang
	 * @throws Exception
	 * @since 1.0.0
	 */
	@ApiOperation(value = "获取图片", notes = "获取图片", httpMethod = "GET", response = VerificationCodeResp.class)
	@GetMapping("/images")
	@Override
	public ResponseEntity<Object> sendImage() throws IOException {
		if (sessionUtil.checkAttribute(request.getSession(), USER_INVOKE_SESSION_KEY)) {
			verifyStatisticsService.raiseCount(request.getSession().getAttribute(USER_INVOKE_SESSION_KEY).toString());
			verifyStatisticsService
					.updateLastInvokeDatetime(request.getSession().getAttribute(USER_INVOKE_SESSION_KEY).toString());
			GetImgResp response = new GetImgResp();
			response.setCode(1);
			response.setMessage("获取成功");
			response.setData(webService.getImage(ORIGIN_IMAGE_WIDTH, ORIGIN_IMAGE_HEIGHT, CUTTED_IMAGE_WIDTH,
					CUTTED_IMAGE_HEIGHT, PICTURE_PATH));
			request.getSession().setAttribute(VALUE_SAVED_IN_SESSION, response.getData().getxCoordinate());
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		return responseUtil.errorResp(HttpStatus.BAD_REQUEST, 0, "拒绝访问");
	}

	/**
	 * 验证接口
	 * 
	 * @return ResponseEntity<Object>
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@ApiOperation(value = "行为验证", notes = "行为验证", httpMethod = "POST", response = VerifyResp.class)
	@PostMapping("/images/verify")
	@Override
	public ResponseEntity<Object> coordinateVerify(@RequestBody BehaviorVerificationReq behaviorVerificationReq) {
		if (validateUtil.isRobot(behaviorVerificationReq.getData())) {
			verifyStatisticsService
					.raiseRobotCount(request.getSession().getAttribute(USER_INVOKE_SESSION_KEY).toString());
			return responseUtil.errorResp(HttpStatus.BAD_REQUEST, 0, "验证失败");
		}
		if (validateUtil.validate(request.getSession().getAttribute(VALUE_SAVED_IN_SESSION),
				behaviorVerificationReq.getLastXPosition(), ALLOWED_DEVIATION_RANGE)) {
			request.getSession().removeAttribute(VALUE_SAVED_IN_SESSION);
			return responseUtil.verifyResp(HttpStatus.OK, 1, "验证成功", request.getHeader("User-Agent"));
		}
		verifyStatisticsService
				.raiseRefusedCount(request.getSession().getAttribute(USER_INVOKE_SESSION_KEY).toString());
		return responseUtil.errorResp(HttpStatus.BAD_REQUEST, 0, "验证失败");
	}

}