package swust.xl.dao.impl;

import static swust.xl.pojo.po.mysql.tables.Users.USERS;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import swust.xl.dao.Mappers;
import swust.xl.pojo.po.mysql.tables.pojos.Users;
import swust.xl.pojo.po.mysql.tables.records.UsersRecord;
import swust.xl.pojo.vo.UserLogin;

/**
 * 
 * 用户信息操作相关Dao层实现类.
 * 
 *
 * @author xuLiang
 * @since 0.0.1
 */
@Repository
public class MappersImpl implements Mappers {

	@Autowired
	private DSLContext dsl;

	/**
	 * 
	 * 获取指定id用户.
	 *
	 * @param id
	 *            需要获取的用户id
	 * @return 获取到的用户对象
	 * @author xuLiang
	 * @since 0.0.1
	 */
	@Override
	public Users getUser(Long id) {
		UsersRecord record = dsl.selectFrom(USERS).where(USERS.ID.eq(id)).fetchOne();
		if (record != null) {
			Users users = record.into(Users.class);
			return users;
		}
		return null;

	}

	/**
	 * 
	 * 删除一条指定id的用户信息记录.
	 *
	 * @param id
	 *            需要删除的用户id
	 * @return 删除结果：true-删除成功，成功删除一条用户记录；false-删除失败，没有删除任何记录
	 * @author xuLiang
	 * @since 0.0.1
	 */
	@Override
	public Boolean deleteUser(Long id) {
		Assert.notNull(id, "待删除的Users全局ID不能为空");
		int num = dsl.deleteFrom(USERS).where(USERS.ID.eq(id)).execute();
		Assert.isTrue(num == 1, "根据id删除Users失败");
		return true;
	}

	/**
	 * 
	 * 添加一条用户信息记录.
	 *
	 * @param Users
	 *            待添加的用户信息
	 * @return 添加完成的用户信息对象
	 * @author xuLiang
	 * @since 0.0.1
	 */
	@Override
	public Users addUser(Users users) {
		UsersRecord userRecord = new UsersRecord();
		userRecord = dsl.newRecord(USERS);
		userRecord.from(users);
		int addResult = userRecord.store();
		if (addResult == 1) {
			return userRecord.into(Users.class);
		}
		return null;
	}

	/**
	 * 
	 * 更新一条用户记录.
	 *
	 * @param Users
	 *            需要更新的用户对象
	 * @return 更新完成的用户信息对象
	 * @author xuLiang
	 * @since 0.0.1
	 */
	@Override
	public Users patchUser(Users users) {

		UsersRecord UsersRecord = dsl.newRecord(USERS);
		UsersRecord.from(users);
		UsersRecord.changed("user_id", false);
		UsersRecord.store();
		return UsersRecord.into(Users.class);
	}

	/**
	 * 
	 * 查找登陆用户账号密码是否匹配
	 * 
	 * @param userLogin
	 *            用户登陆请求体
	 * @return true or false
	 * @author xuLiang
	 * @since 0.0.1
	 */
	@Override
	public boolean findByIdAndPassword(UserLogin userLogin) {
		UsersRecord record = dsl.selectFrom(USERS)
				.where(USERS.ID.eq(userLogin.getId()).and(USERS.PASSWORD.eq(userLogin.getPassword()))).fetchOne();
		if (record != null) {
			return true;
		}
		return false;
	}

}
