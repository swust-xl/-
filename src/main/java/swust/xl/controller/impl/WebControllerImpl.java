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
		String path = "src/main/resources/static/2.jpg";// 指定图片路径
		VerificationCodeResp resp = new VerificationCodeResp();
		try {
			resp = usersService.getImage(500, 400, 90, 90, path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int x = Integer.valueOf(resp.getXCoordinate());
		request.getSession().setAttribute("value", x);
		return resp;
	}

	/**
	 * 验证接口
	 * 
	 * @return String
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@PostMapping("/coorverify")
	@ResponseStatus(HttpStatus.OK)
	@Override
	public ResponseEntity<Object> CoordinateVerify() {
		String userX = String.valueOf(request.getHeader("value"));
		String x = String.valueOf(request.getSession().getAttribute("value"));
		if ((Math.abs((Integer.valueOf(x) - Integer.valueOf(userX))) > 20)) {
			request.getSession().removeAttribute("value");
			return ResponseUtil.commonResp(HttpStatus.BAD_REQUEST, 0, "验证失败", null);
		}
		return ResponseUtil.errorResp(HttpStatus.OK, 1, "验证通过", null);
	}

}