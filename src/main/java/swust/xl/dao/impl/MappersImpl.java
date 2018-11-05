package swust.xl.dao.impl;

import static swust.xl.pojo.po.mysql.tables.UserPerson.USER_PERSON;

import java.sql.Timestamp;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import swust.xl.dao.Mappers;
import swust.xl.pojo.po.mysql.tables.pojos.UserPerson;
import swust.xl.pojo.po.mysql.tables.records.UserPersonRecord;
import swust.xl.pojo.vo.UserLogin;
import swust.xl.util.md5.md5Util;

/**
 * 
 * 用户信息操作相关Dao层实现类.
 * 
 *
 * @author xuLiang
 * @since 1.0.0
 */
@Repository
public class MappersImpl implements Mappers {

	@Autowired
	private DSLContext dsl;

	/**
	 * 
	 * 根据id查询用户信息
	 *
	 * @param id
	 *            需要获取的用户id
	 * @return 获取到的用户对象
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@Override
	public UserPerson getUser(Long id) {
		UserPersonRecord record = dsl.selectFrom(USER_PERSON).where(USER_PERSON.ID.eq(id)).fetchOne();
		if (record != null) {
			UserPerson userPerson = record.into(UserPerson.class);
			return userPerson;
		}
		return null;
	}

	/**
	 * 根据用户名查询用户信息
	 * 
	 * @param username
	 * @return 获取到的用户对象
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@Override
	public UserPerson getUser(String username) {
		UserPersonRecord record = dsl.selectFrom(USER_PERSON).where(USER_PERSON.USERNAME.eq(username)).fetchOne();
		if (record != null) {
			UserPerson userPerson = record.into(UserPerson.class);
			return userPerson;
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
	 * @since 1.0.0
	 */
	@Override
	public Boolean deleteUser(Long id) {
		Assert.notNull(id, "待删除的Users全局ID不能为空");
		int num = dsl.deleteFrom(USER_PERSON).where(USER_PERSON.ID.eq(id)).execute();
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
	 * @since 1.0.0
	 */
	@Override
	public UserPerson addUser(UserPerson userPerson) {
		UserPersonRecord userRecord = new UserPersonRecord();
		userRecord = dsl.newRecord(USER_PERSON);
		userRecord.from(userPerson);
		int addResult = userRecord.store();
		if (addResult == 1) {
			return userRecord.into(UserPerson.class);
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
	 * @since 1.0.0
	 */
	@Override
	public UserPerson patchUser(UserPerson userPerson) {
		UserPersonRecord usersRecord = dsl.selectFrom(USER_PERSON)
				.where(USER_PERSON.USERNAME.eq(userPerson.getUsername())).fetchOne();
		usersRecord.setSex(userPerson.getSex());
		usersRecord.setPasswordSalt(userPerson.getPasswordSalt());
		usersRecord.setSalt(userPerson.getSalt());
		usersRecord.store();
		return usersRecord.into(UserPerson.class);
	}

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
	@Override
	public boolean findByIdAndPassword(UserLogin userLogin) {
		UserPersonRecord record = dsl.selectFrom(USER_PERSON)
				.where(USER_PERSON.USERNAME.eq(userLogin.getUsername())
						.and(USER_PERSON.PASSWORD_SALT.eq(
								md5Util.md5Hex(userLogin.getPassword() + getUser(userLogin.getUsername()).getSalt()))))
				.fetchOne();
		if (record != null) {
			return true;
		}
		return false;
	}

	/**
	 * 更新用户最后登录时间
	 * 
	 * @param username
	 * @return Timestamp更新后的时间
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@Override
	public Timestamp updateLastLoginDatetime(String username) {
		UserPersonRecord record = dsl.selectFrom(USER_PERSON).where(USER_PERSON.USERNAME.eq(username)).fetchOne();
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		record.setLastLoginDatetime(ts);
		record.store();
		return ts;
	}

}
