package cn.signit.service.impl;

import java.sql.Timestamp;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.signit.dao.StatisticsMappers;
import cn.signit.dao.UserMappers;
import cn.signit.exceptions.UserAlreadyExistsException;
import cn.signit.exceptions.UserInfoException;
import cn.signit.pojo.bo.BoAddUserRequest;
import cn.signit.pojo.bo.BoGetUserResp;
import cn.signit.pojo.bo.BoUpdateUserRequest;
import cn.signit.pojo.dto.BoMapper;
import cn.signit.pojo.vo.UserLogin;
import cn.signit.service.UsersService;
import cn.signit.util.md5.Md5Util;

/**
 * 
 * 用户信息操作相关服务层实现.
 *
 * @author xuLiang
 * @since 1.0.0
 */
@Service
public class UsersServiceImpl implements UsersService {

	@Autowired
	private UserMappers mappers;
	@Autowired
	private StatisticsMappers statisticsMappers;

	/**
	 * 
	 * 根据id获取一条用户信息记录.
	 *
	 * @param id
	 *            待获取用户id
	 * @return 一条得到的用户信息记录
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@Override
	public BoGetUserResp getUser(@Valid @NotNull Long id) {
		return BoMapper.INSTANCE.toBoGetUserResp(mappers.getUser(id));

	}

	/**
	 * 根据用户名获取一条用户信息记录.
	 * 
	 * @param username
	 *            待获取用户的用户名
	 * @return 一条得到的用户信息记录
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@Override
	public BoGetUserResp getUser(@Valid @NotNull String userName) {
		return BoMapper.INSTANCE.toBoGetUserResp(mappers.getUser(userName));

	}

	/**
	 * 
	 * 添加一条用户信息记录.
	 *
	 * @param boAddUserRequest
	 *            待添加的用户信息
	 * @return 添加完成的用户信息对象
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public BoGetUserResp addUser(BoAddUserRequest boAddUserRequest) {
		if (mappers.getUser(boAddUserRequest.getUserName()) != null
				|| mappers.getUser(boAddUserRequest.getEmail()) != null) {
			throw new UserAlreadyExistsException("用户名或邮箱");
		}
		String salt = Md5Util.getSalt();
		String passwordWithSalt = Md5Util.md5Hex(boAddUserRequest.getPassword() + salt);
		boAddUserRequest.setSalt(salt);
		boAddUserRequest.setPassword(passwordWithSalt);
		boAddUserRequest.setRegistDatetime(new Timestamp(System.currentTimeMillis()));
		statisticsMappers.initUserInfo(boAddUserRequest.getUserName());
		return BoMapper.INSTANCE.toBoGetUserResp(mappers.addUser(BoMapper.INSTANCE.fromBoAddUserReq(boAddUserRequest)));
	}

	/**
	 * 
	 * 删除一条用户信息记录.
	 *
	 * @param id
	 *            待删除的用户信息id
	 * @return 删除结果：true-删除成功，成功删除一条用户记录；false-删除失败，没有删除任何记录
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean deleteUser(Long id) {
		return mappers.deleteUser(id);
	}

	/**
	 * 
	 * 更新一个用户信息记录.
	 * 
	 * @param userPerson
	 *            需要更新的用户对象
	 * 
	 * @return 更新后的用户对象
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public BoGetUserResp updateUser(BoUpdateUserRequest boUpdateUserRequest) {
		if (!mappers.login(new UserLogin(boUpdateUserRequest.getUserName(), boUpdateUserRequest.getPassword()))) {
			throw new UserInfoException("用户名或密码错误");
		}
		String salt = Md5Util.getSalt();
		String passwordWithSalt = Md5Util.md5Hex(boUpdateUserRequest.getPassword() + salt);
		boUpdateUserRequest.setSalt(salt);
		boUpdateUserRequest.setPassword(passwordWithSalt);
		statisticsMappers.changeUserName(mappers.getUser(boUpdateUserRequest.getId()).getUsername(),
				boUpdateUserRequest.getUserName());
		return BoMapper.INSTANCE
				.toBoGetUserResp(mappers.updateUser(BoMapper.INSTANCE.fromBoUpdateUserReq(boUpdateUserRequest)));
	}

	/**
	 * 
	 * 验证用户登陆
	 * 
	 * @param userLogin
	 *            用户登陆请求体
	 * @return true ， false
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@Override
	public boolean verifyLogin(UserLogin userLogin) {
		if (!mappers.login(userLogin)) {
			throw new UserInfoException("用户名或密码错误");
		}
		userLogin.setPassword(
				Md5Util.md5Hex(userLogin.getPassword() + mappers.getUser(userLogin.getUsernameOrEmail()).getSalt()));
		return mappers.login(userLogin);
	}

	/**
	 * 更新用户最后登录时间
	 * 
	 * @param username
	 * @return UserPerson
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public BoGetUserResp updateLastLoginDatetime(String usernameOrEmail) {
		return BoMapper.INSTANCE.toBoGetUserResp(mappers.updateLastLoginDatetime(usernameOrEmail));
	}
}