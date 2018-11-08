package swust.xl.controller.impl;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import swust.xl.controller.WebController;
import swust.xl.pojo.vo.VerificationCodeResp;
import swust.xl.service.UsersService;
import swust.xl.util.response.ResponseUtil;

/**
 * 验证相关controller实现类
 * 
 * @author xuLiang
 * @since 1.0.0
 */
@RestController
@SessionAttributes("value")
public class WebControllerImpl implements WebController {

	public static final int ORIGIN_IMAGE_WIDTH = 500;
	public static final int ORIGIN_IMAGE_HEIGHT = 400;
	public static final int CUTTED_IMAGE_WIDTH = 90;
	public static final int CUTTED_IMAGE_HEIGHT = 90;
	public static final int ALLOWED_ERROR_RANGE = 20;
	public static final String PICTURE_PATH = "src/main/resources/static/1.jpg";

	@Autowired
	private UsersService usersService;
	@Autowired
	private HttpServletRequest request;

	/**
	 * 获取图片接口
	 * 
	 * @return VerificationCodeResp
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@GetMapping("/getimg")
	@ResponseStatus(HttpStatus.OK)
	@Override
	public VerificationCodeResp sendImage() {
		VerificationCodeResp resp = new VerificationCodeResp();
		try {
			resp = usersService.getImage(ORIGIN_IMAGE_WIDTH, ORIGIN_IMAGE_HEIGHT, CUTTED_IMAGE_WIDTH,
					CUTTED_IMAGE_HEIGHT, PICTURE_PATH);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int x = Integer.valueOf(resp.getxCoordinate());
		request.getSession().setAttribute("value", x);
		return resp;
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
	public ResponseEntity<Object> coordinateVerify() {
		String userX = String.valueOf(request.getHeader("value"));
		String x = String.valueOf(request.getSession().getAttribute("value"));
		if ((Math.abs((Integer.valueOf(x) - Integer.valueOf(userX))) > ALLOWED_ERROR_RANGE)) {
			request.getSession().removeAttribute("value");
			return ResponseUtil.commonResp(HttpStatus.BAD_REQUEST, 0, "验证失败", null);
		}
		return ResponseUtil.errorResp(HttpStatus.OK, 1, "验证通过", null);
	}

}