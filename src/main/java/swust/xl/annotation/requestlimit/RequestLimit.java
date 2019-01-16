package swust.xl.annotation.requestlimit;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 限制接口访问次数
 * 
 * @see swust.xl.annotation.requestlimit.RequestLimitImpl
 * @author xuLiang
 * @since 1.0.0
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestLimit {
	/*
	 * 同一IP在n秒内能访问1次 ,默认不限制
	 */
	long value() default 0;

	String message() default "请求过于频繁";

}
