package cn.signit.annotation.requestlimit;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 限制接口访问次数
 * 
 * @see cn.signit.annotation.requestlimit.RequestLimitImpl
 * @author xuLiang
 * @since 1.0.0
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestLimit {
	/**
	 * 时间段内允许访问的最大次数，默认不限制
	 */
	int count() default Integer.MAX_VALUE;

	/**
	 * 时间段，单位为秒，默认值60秒
	 */
	long time() default 60;

	String message() default "请求过于频繁";

}