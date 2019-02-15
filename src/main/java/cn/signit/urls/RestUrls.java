package cn.signit.urls;

/**
 * RESTful请求路径
 * 
 * @author xuLiang
 * @since 1.0.0
 */
public class RestUrls {

	public static final String USERS = "/users";

	public static final String USERS_PathVariable_ID = "/users/{user-id}";

	public static final String USERS_LOGIN = "/users/login";

	public static final String USERS_LOGOUT = "/users/logout";

	public static final String USERS_STATISTICS_PathVariable_USERNAME = "/users/statistics/{userName}";

	public static final String IMAGES_PREPROCESS = "/images/preprocess";

	public static final String IMAGES = "/images";

	public static final String IMAGES_VERIFY = "/images/verify";

}