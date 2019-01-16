package swust.xl.controller.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import io.swagger.annotations.ApiOperation;
import swust.xl.controller.WebController;
import swust.xl.pojo.dto.VoMapper;
import swust.xl.pojo.vo.GetImgResp;
import swust.xl.pojo.vo.GetUserCommonResp;
import swust.xl.pojo.vo.UserLogin;
import swust.xl.pojo.vo.VerificationCodeResp;
import swust.xl.pojo.vo.behavior.verification.BehaviorVerificationReq;
import swust.xl.pojo.vo.verify.response.VerifyResp;
import swust.xl.service.UsersService;
import swust.xl.service.VerifyStatisticsService;
import swust.xl.service.WebService;
import swust.xl.util.response.ResponseUtil;
import swust.xl.util.session.SessionUtil;
import swust.xl.util.validate.ValidateUtil;

/**
 * 验证相关controller实现类
 * 
 * @author xuLiang
 * @since 1.0.0
 */
@RestController
@SessionAttributes("verify-value")
public class WebControllerImpl implements WebController {

	@Value("${origin.image.width}")
	private int ORIGIN_IMAGE_WIDTH;
	@Value("${origin.image.height}")
	private int ORIGIN_IMAGE_HEIGHT;
	@Value("${cutted.image.width}")
	private int CUTTED_IMAGE_WIDTH;
	@Value("${cutted.image.height}")
	private int CUTTED_IMAGE_HEIGHT;
	@Value("${picture.path}")
	private String PICTURE_PATH;
	@Value("${allowed.deviation.range}")
	private int ALLOWED_DEVIATION_RANGE;
	public static final String USER_iNVOKE_SESSION_KEY = "access-to-invoke";

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
	public ResponseEntity<Object> Preprocess(@RequestBody UserLogin userLogin) {
		if (!sessionUtil.checkAttribute(request.getSession(), USER_iNVOKE_SESSION_KEY)) {
			if (usersService.verifyLogin(userLogin)) {
				request.getSession().setAttribute(USER_iNVOKE_SESSION_KEY, userLogin.getUsernameOrEmail());
				request.getSession().setMaxInactiveInterval(24 * 3600);
				return responseUtil.commonResp(HttpStatus.OK, 1, "用户凭证验证通过", null, VoMapper.INSTANCE
						.fromBoToVoGetUserCommonResponseMap(usersService.getUser(userLogin.getUsernameOrEmail())));
			}
			return responseUtil.errorResp(HttpStatus.UNAUTHORIZED, 0, "无效的用户凭证");
		}
		return responseUtil.commonResp(HttpStatus.OK, 1, "用户凭证生效中", null, VoMapper.INSTANCE
				.fromBoToVoGetUserCommonResponseMap(usersService.getUser(userLogin.getUsernameOrEmail())));
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
	public ResponseEntity<Object> sendImage() throws Exception {
		if (sessionUtil.checkAttribute(request.getSession(), USER_iNVOKE_SESSION_KEY)) {
			verifyStatisticsService.raiseCount(request.getSession().getAttribute(USER_iNVOKE_SESSION_KEY).toString());
			verifyStatisticsService
					.updateLastInvokeDatetime(request.getSession().getAttribute(USER_iNVOKE_SESSION_KEY).toString());
			GetImgResp response = new GetImgResp();
			response.setCode(1);
			response.setMessage("获取成功");
			response.setData(webService.getImage(ORIGIN_IMAGE_WIDTH, ORIGIN_IMAGE_HEIGHT, CUTTED_IMAGE_WIDTH,
					CUTTED_IMAGE_HEIGHT, PICTURE_PATH));
			request.getSession().setAttribute("validate-value", response.getData().getxCoordinate());
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
					.raiseRobotCount(request.getSession().getAttribute(USER_iNVOKE_SESSION_KEY).toString());
			return responseUtil.errorResp(HttpStatus.BAD_REQUEST, 0, "验证失败");
		}
		if (validateUtil.Validate(request.getSession().getAttribute("validate-value"),
				behaviorVerificationReq.getLastXPosition(), ALLOWED_DEVIATION_RANGE)) {
			request.getSession().removeAttribute("validate-value");
			return responseUtil.verifyResp(HttpStatus.OK, 1, "验证成功", request.getHeader("User-Agent"));
		}
		verifyStatisticsService
				.raiseRefusedCount(request.getSession().getAttribute(USER_iNVOKE_SESSION_KEY).toString());
		return responseUtil.errorResp(HttpStatus.BAD_REQUEST, 0, "验证失败");
	}

}