package swust.xl.controller.impl;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import swust.xl.pojo.vo.VerificationCodeResp;
import swust.xl.service.UsersService;

/**
 * 测试controller
 * 
 * @author xuLiang
 * @since 1.0.0
 */
@Controller
@SessionAttributes("value")
public class WebpageController {
	@Autowired
	UsersService usersService;

	/**
	 * 测试页面
	 * 
	 * @param request
	 */
	@GetMapping("/index")
	@ResponseStatus(HttpStatus.OK)
	public String Index(HttpServletRequest request) {
		request.getSession().setMaxInactiveInterval(3600);
		return "index";
	}

	/**
	 * 获取图片接口
	 * 
	 * @param request
	 * @return VerificationCodeResp
	 * @throws Exception
	 */
	@GetMapping("/getImg")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public VerificationCodeResp sendImage(HttpServletRequest request) throws Exception {
		String path = "src/main/resources/static/1.jpg";// 指定图片路径
		VerificationCodeResp resp = usersService.getImage(1920, 1080, 300, 300, path);
		int x = Integer.valueOf(resp.getXCoordinate());
		request.getSession().setAttribute("value", x);
		return resp;
	}

	/**
	 * 验证接口
	 * 
	 * @param request
	 * @return null
	 * @throws Exception
	 */
	@PostMapping("/coordinateVerify")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public String CoordinateVerify(HttpServletRequest request) throws Exception {
		String userX = String.valueOf(request.getHeader("value"));
		String x = String.valueOf(request.getSession().getAttribute("value"));
		if ((Math.abs((Integer.valueOf(x) - Integer.valueOf(userX))) > 30)) {
			request.getSession().removeAttribute("value");
			throw new Exception("验证失败");
		}
		return "验证成功";
	}

}