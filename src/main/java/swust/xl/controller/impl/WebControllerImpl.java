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
import swust.xl.pojo.vo.VerifyResp;
import swust.xl.service.UsersService;

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
	 * @throws Exception
	 */
	@GetMapping("/getimg")
	@ResponseStatus(HttpStatus.OK)
	@Override
	public VerificationCodeResp sendImage() {
		String path = "src/main/resources/static/1.jpg";// 指定图片路径
		VerificationCodeResp resp = new VerificationCodeResp();
		try {
			resp = usersService.getImage(1920, 1080, 300, 300, path);
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
	 * @throws Exception
	 */
	@PostMapping("/coorverify")
	@ResponseStatus(HttpStatus.OK)
	@Override
	public ResponseEntity<Object> CoordinateVerify() {
		VerifyResp response = new VerifyResp();
		String userX = String.valueOf(request.getHeader("value"));
		String x = String.valueOf(request.getSession().getAttribute("value"));
		if ((Math.abs((Integer.valueOf(x) - Integer.valueOf(userX))) > 30)) {
			request.getSession().removeAttribute("value");
			response.setCode(1);
			response.setMessage("验证通过");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		response.setCode(0);
		response.setMessage("验证失败");
		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
	}

}