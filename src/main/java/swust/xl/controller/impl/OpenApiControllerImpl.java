package swust.xl.controller.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import swust.xl.pojo.vo.UserLogin;
import swust.xl.pojo.vo.VerificationCodeResp;
import swust.xl.service.UsersService;
import swust.xl.util.response.ResponseUtil;
import swust.xl.util.session.SessionUtil;
import swust.xl.util.validate.ValidateUtil;

public class OpenApiControllerImpl {

	public static final int ORIGIN_IMAGE_WIDTH = 500;
	public static final int ORIGIN_IMAGE_HEIGHT = 400;
	public static final int CUTTED_IMAGE_WIDTH = 90;
	public static final int CUTTED_IMAGE_HEIGHT = 90;
	public static final int ALLOWED_DEVIATION_RANGE = 20;
	public static final String PICTURE_PATH = "src/main/resources/static/1.jpg";
	public static final String USER_iNVOKE_SESSION_KEY = "access-to-invoke";

	@Autowired
	private UsersService usersService;
	@Autowired
	private HttpServletRequest request;

	/**
	 * 第三方调用验证预处理
	 * 
	 * @param userLogin
	 * @return ResponseEntity<Object>
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@PostMapping("/openapi/preprocess")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object> openApiPreprocess(@RequestBody UserLogin userLogin) {
		if (!SessionUtil.checkAttribute(request.getSession(), USER_iNVOKE_SESSION_KEY)) {
			if (usersService.verifyLogin(new UserLogin(userLogin.getUsername(), userLogin.getPassword()))) {
				request.getSession().setAttribute(USER_iNVOKE_SESSION_KEY, userLogin.getUsername());
				request.getSession().setMaxInactiveInterval(24 * 3600);
			}
			return ResponseUtil.errorResp(HttpStatus.UNAUTHORIZED, 0, "无效的用户凭证");
		}
		return ResponseUtil.openApiResp(HttpStatus.OK, 1, "用户凭证验证通过");
	}

	/**
	 * 开放平台获取图片和验证值接口
	 * 
	 * @return ResponseEntity<Object>
	 * @throws Exception
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@GetMapping("/openapi/getimg")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object> openApiSendImage() throws Exception {
		if (SessionUtil.checkAttribute(request.getSession(), USER_iNVOKE_SESSION_KEY)) {
			VerificationCodeResp response = new VerificationCodeResp();
			response = usersService.getImage(ORIGIN_IMAGE_WIDTH, ORIGIN_IMAGE_HEIGHT, CUTTED_IMAGE_WIDTH,
					CUTTED_IMAGE_HEIGHT, PICTURE_PATH);
			request.getSession().setAttribute("validate-value", response.getxCoordinate());
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		return ResponseUtil.errorResp(HttpStatus.BAD_REQUEST, 0, "拒绝访问");
	}

	/**
	 * 开放平台验证接口
	 * 
	 * @return ResponseEntity<Object>
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@PostMapping("/openapi/coorverify")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Object> openApiCoordinateVerify() {
		if (SessionUtil.checkAttribute(request.getSession(), USER_iNVOKE_SESSION_KEY)) {
			if (ValidateUtil.Validate(request.getSession().getAttribute("validate-value"),
					request.getHeader("user-validate-value"), ALLOWED_DEVIATION_RANGE)) {
				return ResponseUtil.commonResp(HttpStatus.OK, 1, "验证通过", null);
			}
			request.getSession().removeAttribute("validate-value");
			return ResponseUtil.errorResp(HttpStatus.BAD_REQUEST, 0, "验证失败");
		}
		return ResponseUtil.errorResp(HttpStatus.BAD_REQUEST, 0, "拒绝访问");

	}
}
