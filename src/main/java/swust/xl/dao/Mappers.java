package swust.xl.dao;

import java.sql.Timestamp;

import org.springframework.validation.annotation.Validated;

import swust.xl.pojo.po.mysql.tables.pojos.UserPerson;
import swust.xl.pojo.vo.UserLogin;

/**
 * 
 * 用户信息操作相关Dao层接口.
 * 
 *
 * @author xuLiang
 * @since 1.0.0
 */
@Validated
public interface Mappers {
	/**
	 * 
	 * 获取指定id用户.
	 *
	 * @param id
	 *            需要获取的用户id
	 * @return 获取到的用户对象
	 * @author xuLiang
	 * @since 1.0.0
	 */
	UserPerson getUser(Long id);

	/**
	 * 
	 * 获取指定用户名用户.
	 *
	 * @param id
	 *            需要获取的用户id
	 * @return 获取到的用户对象
	 * @author xuLiang
	 * @since 1.0.0
	 */
	UserPerson getUser(String username);

	/**
	 * 
	 * 添加一条用户信息记录.
	 *
	 * @param users
	 *            待添加的用户信息
	 * @return 添加完成的用户信息对象
	 * @author xuLiang
	 * @since 1.0.0
	 */
	UserPerson addUser(UserPerson userPerson);

	/**
	 * 
	 * 删除一条指定id的用户信息记录.
	 *
	 * @param id
	 *            需要删除的用户id
	 * @return 删除结果：true-删除成功，成功删除一条用户记录；false-删除失败，没有删除任何记录
	 * @author xuLiang
	 * @since 1.0.0
	 */
	Boolean deleteUser(Long id);

	/**
	 * 
	 * 更新一条用户记录.
	 *
	 * @param Users
	 *            需要更新的用户对象
	 * @return 更新完成的用户信息对象
	 * @author xuLiang
	 * @since 1.0.0
	 */
	UserPerson patchUser(UserPerson userPerson);

	/**
	 * 
	 * 查找登陆用户账号密码是否匹配
	 * 
	 * @param userLogin
	 *            用户登陆请求体
	 * @return true or false
	 * @author xuLiang
	 * @since 1.0.0
	 */
	boolean findByIdAndPassword(UserLogin userLogin);

	/**
	 * 更新用户最后登录时间
	 * 
	 * @param username
	 * @return Timestamp更新后的时间
	 * @author xuLiang
	 * @since 1.0.0
	 */
	Timestamp updateLastLoginDatetime(String username);
}
