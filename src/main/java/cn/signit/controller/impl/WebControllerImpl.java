package cn.signit.controller.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cn.signit.controller.WebController;
import cn.signit.exceptions.NoAccessException;
import cn.signit.pojo.CommonResp;
import cn.signit.pojo.dto.VoMapper;
import cn.signit.pojo.vo.UserLogin;
import cn.signit.pojo.vo.UserPreprocess;
import cn.signit.pojo.vo.VerificationCodeResp;
import cn.signit.pojo.vo.behavior.verification.BehaviorVerificationReq;
import cn.signit.service.UsersService;
import cn.signit.service.VerifyStatisticsService;
import cn.signit.service.WebService;
import cn.signit.urls.RestUrls;
import cn.signit.util.session.SessionUtil;
import cn.signit.util.validate.ValidateUtil;

/**
 * 验证相关controller实现类
 * 
 * @author xuLiang
 * @since 1.0.0
 */
@RestController
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
	private SessionUtil sessionUtil;
	@Autowired
	private ValidateUtil validateUtil;

	/**
	 * 第三方调用验证预处理
	 * 
	 * @param userLogin
	 * @return CommonResp<?>
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@PostMapping(RestUrls.IMAGES_PREPROCESS)
	@ResponseStatus(HttpStatus.OK)
	@Override
	public CommonResp<?> preprocess(@RequestBody UserPreprocess user) {
		if (!sessionUtil.checkAttribute(request.getSession(), USER_INVOKE_SESSION_KEY)) {
			if (usersService.verifyLogin(new UserLogin(user.getUserName(), user.getPassword()))) {
				request.getSession().setAttribute(USER_INVOKE_SESSION_KEY, user.getUserName());
				request.getSession().setMaxInactiveInterval(24 * 3600);
				return new CommonResp<>(1, "用户凭证验证通过",
						VoMapper.INSTANCE.fromBoToVoGetUserResponse(usersService.getUser(user.getUserName())));
			}
			throw new NoAccessException("无效的用户凭证");
		}
		return new CommonResp<>(1, "用户凭证生效中",
				VoMapper.INSTANCE.fromBoToVoGetUserResponse(usersService.getUser(user.getUserName())));
	}

	/**
	 * 获取图片接口
	 * 
	 * @return CommonResp<?>
	 * @author xuLiang
	 * @throws Exception
	 * @since 1.0.0
	 */
	@GetMapping(RestUrls.IMAGES)
	@ResponseStatus(HttpStatus.OK)
	@Override
	public CommonResp<?> sendImage() {
		if (sessionUtil.checkAttribute(request.getSession(), USER_INVOKE_SESSION_KEY)) {
			verifyStatisticsService.raiseCount(request.getSession().getAttribute(USER_INVOKE_SESSION_KEY).toString());
			verifyStatisticsService
					.updateLastInvokeDatetime(request.getSession().getAttribute(USER_INVOKE_SESSION_KEY).toString());
			CommonResp<VerificationCodeResp> response = new CommonResp<>();
			response.setCode(1);
			response.setMessage("获取成功");
			try {
				response.setData(webService.getImage(ORIGIN_IMAGE_WIDTH, ORIGIN_IMAGE_HEIGHT, CUTTED_IMAGE_WIDTH,
						CUTTED_IMAGE_HEIGHT, PICTURE_PATH));
			} catch (IOException e) {
				e.printStackTrace();
			}
			request.getSession().setAttribute(VALUE_SAVED_IN_SESSION, response.getData().getxCoordinate());
			return response;
		}
		throw new NoAccessException("拒绝访问");
	}

	/**
	 * 验证接口
	 * 
	 * @return CommonResp<?>
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@PostMapping(RestUrls.IMAGES_VERIFY)
	@ResponseStatus(HttpStatus.OK)
	@Override
	public CommonResp<?> coordinateVerify(@RequestBody BehaviorVerificationReq behaviorVerificationReq) {
		if (validateUtil.isRobot(behaviorVerificationReq.getData())) {
			verifyStatisticsService
					.raiseRobotCount(request.getSession().getAttribute(USER_INVOKE_SESSION_KEY).toString());
			return new CommonResp<>(0, "验证失败", null);
		}
		if (validateUtil.validate(request.getSession().getAttribute(VALUE_SAVED_IN_SESSION),
				behaviorVerificationReq.getLastXPosition(), ALLOWED_DEVIATION_RANGE)) {
			request.getSession().removeAttribute(VALUE_SAVED_IN_SESSION);
			return new CommonResp<>(1, "验证成功", null);
		}
		verifyStatisticsService
				.raiseRefusedCount(request.getSession().getAttribute(USER_INVOKE_SESSION_KEY).toString());
		return new CommonResp<>(0, "验证失败", null);
	}

}