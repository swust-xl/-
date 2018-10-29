package swust.xl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import swust.xl.configuration.CustomBean;

/**
 * 
 * Spring boot启动类
 * 
 * @author xuLiang
 * @since 0.0.1
 */
@Import(CustomBean.class)
@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
