package cn.signit.controller.advice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.signit.pojo.ErrorResp;

/**
 * 全局异常处理
 * 
 * @author xuLiang
 * @since 1.0.0
 */
@ControllerAdvice
public class ControllerAdvise {

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ErrorResp exceptionHandler(Exception e, HttpServletRequest request) {
		ErrorResp resp = new ErrorResp();
		resp.setTimestamp(System.currentTimeMillis());
		resp.setStatus(400);
		resp.setError("Bad Request");
		resp.setMessage(e.getMessage());
		resp.setPath(request.getRequestURI());
		return resp;
	}

}