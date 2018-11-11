package swust.xl.controller.impl;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import swust.xl.controller.WebController;
import swust.xl.pojo.vo.VerificationCodeResp;
import swust.xl.pojo.vo.behavior.verification.BehaviorVerificationReq;
import swust.xl.service.UsersService;
import swust.xl.util.response.ResponseUtil;
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

	public static final int ORIGIN_IMAGE_WIDTH = 300;
	public static final int ORIGIN_IMAGE_HEIGHT = 150;
	public static final int CUTTED_IMAGE_WIDTH = 40;
	public static final int CUTTED_IMAGE_HEIGHT = 40;
	public static final String PICTURE_PATH = "src/main/resources/static/1.jpg";
	public static final int ALLOWED_DEVIATION_RANGE = 20;

	@Autowired
	private UsersService usersService;
	@Autowired
	private HttpServletRequest request;

	/**
	 * 获取图片接口
	 * 
	 * @return VerificationCodeResp
	 * @author xuLiang
	 * @throws Exception
	 * @since 1.0.0
	 */
	@GetMapping("/getimg")
	@ResponseStatus(HttpStatus.OK)
	@Override
	public VerificationCodeResp sendImage() throws Exception {
		VerificationCodeResp response = new VerificationCodeResp();
		response = usersService.getImage(ORIGIN_IMAGE_WIDTH, ORIGIN_IMAGE_HEIGHT, CUTTED_IMAGE_WIDTH,
				CUTTED_IMAGE_HEIGHT, PICTURE_PATH);
		request.getSession().setAttribute("validate-value", response.getxCoordinate());
		return response;
	}

	/**
	 * 验证接口
	 * 
	 * @return ResponseEntity<Object>
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@PostMapping("/coorverify")
	@ResponseStatus(HttpStatus.OK)
	@Override
	public ResponseEntity<Object> coordinateVerify(@RequestBody BehaviorVerificationReq behaviorVerificationReq) {
		if (ValidateUtil.isRobot(behaviorVerificationReq.getData())) {
			return ResponseUtil.errorResp(HttpStatus.BAD_REQUEST, 0, "验证失败");
		}
		if (ValidateUtil.Validate(request.getSession().getAttribute("validate-value"),
				behaviorVerificationReq.getUserResult(), ALLOWED_DEVIATION_RANGE)) {
			return ResponseUtil.commonResp(HttpStatus.OK, 1, "验证通过", null);
		}
		request.getSession().removeAttribute("validate-value");
		return ResponseUtil.errorResp(HttpStatus.BAD_REQUEST, 0, "验证失败");
	}

}