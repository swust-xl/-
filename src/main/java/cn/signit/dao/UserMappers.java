package cn.signit.dao;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import cn.signit.pojo.po.mysql.tables.pojos.UserPerson;
import cn.signit.pojo.vo.UserLogin;

/**
 * 
 * 用户信息操作相关Dao层接口.
 * 
 * @author xuLiang
 * @since 1.0.0
 */
@Validated
public interface UserMappers {
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
	public UserPerson getUser(@Valid @NotNull Long id);

	/**
	 * 
	 * 获取指定用户名或邮箱用户.
	 *
	 * @param userNameOrEmail
	 *            需要获取的用户名
	 * @return 获取到的用户对象
	 * @author xuLiang
	 * @since 1.0.0
	 */
	public UserPerson getUser(@Valid @NotNull String userNameOrEmail);

	/**
	 * 
	 * 添加一条用户信息记录.
	 *
	 * @param userPerson
	 *            待添加的用户信息
	 * @return 添加完成的用户信息对象
	 * @author xuLiang
	 * @since 1.0.0
	 */
	public UserPerson addUser(@Valid @NotNull UserPerson userPerson);

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
	public boolean deleteUser(@Valid @NotNull Long id);

	/**
	 * 
	 * 更新一条用户记录.
	 *
	 * @param userPerson
	 *            需要更新的用户对象
	 * @return 更新完成的用户信息对象
	 * @author xuLiang
	 * @since 1.0.0
	 */
	public UserPerson updateUser(@Valid @NotNull UserPerson userPerson);

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
	public boolean login(@Valid @NotNull UserLogin userLogin);

	/**
	 * 更新用户最后登录时间
	 * 
	 * @param userNameOrEmail
	 * @return UserPerson 更新后的用户信息
	 * @author xuLiang
	 * @since 1.0.0
	 */
	public UserPerson updateLastLoginDatetime(@Valid @NotNull String userNameOrEmail);
}
