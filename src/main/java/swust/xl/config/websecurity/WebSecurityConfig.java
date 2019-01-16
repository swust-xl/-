package swust.xl.config.websecurity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 
 * web拦截器类
 *
 * @author xuLiang
 * @since 1.0.0
 */

@Configuration
public class WebSecurityConfig implements WebMvcConfigurer {

	public static final String SESSION_KEY = "usernameOrEmail";

	@Bean
	public SecurityInterceptor getSecurityInterceptor() {
		return new SecurityInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());

		// 排除配置
		addInterceptor.excludePathPatterns("/error");
		addInterceptor.excludePathPatterns("/**");

		// 拦截配置
		// addInterceptor.addPathPatterns("/users");
	}

	private class SecurityInterceptor extends HandlerInterceptorAdapter {

		@Override
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
				throws Exception {
			if (request.getSession().getAttribute(SESSION_KEY) != null) {
				return true;
			}
			response.sendError(400, "Unauthorized");
			return false;
		}
	}
}