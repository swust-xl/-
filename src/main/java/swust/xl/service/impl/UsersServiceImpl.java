package swust.xl.service.impl;

import java.sql.Timestamp;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import swust.xl.dao.UserMappers;
import swust.xl.pojo.bo.adduser.request.BoAddUserRequest;
import swust.xl.pojo.bo.getuser.response.BoGetUserResp;
import swust.xl.pojo.bo.patchuser.request.BoPatchUserRequest;
import swust.xl.pojo.dto.BoMapper;
import swust.xl.pojo.vo.user.login.UserLogin;
import swust.xl.service.UsersService;
import swust.xl.util.md5.Md5Util;

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
		return BoMapper.INSTANCE.toBoGetUserRespMap(mappers.getUser(id));

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
		return BoMapper.INSTANCE.toBoGetUserRespMap(mappers.getUser(userName));

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
		String salt = Md5Util.getSalt();
		String passwordWithSalt = Md5Util.md5Hex(boAddUserRequest.getPassword() + salt);
		boAddUserRequest.setSalt(salt);
		boAddUserRequest.setPassword(passwordWithSalt);
		boAddUserRequest.setRegistDatetime(new Timestamp(System.currentTimeMillis()));
		return BoMapper.INSTANCE
				.toBoGetUserRespMap(mappers.addUser(BoMapper.INSTANCE.fromBoAddUserReqMap(boAddUserRequest)));
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
	public BoGetUserResp patchUser(BoPatchUserRequest boPatchUserRequest) {
		String salt = Md5Util.getSalt();
		String passwordWithSalt = Md5Util.md5Hex(boPatchUserRequest.getPassword() + salt);
		boPatchUserRequest.setSalt(salt);
		boPatchUserRequest.setPassword(passwordWithSalt);
		return BoMapper.INSTANCE
				.toBoGetUserRespMap(mappers.patchUser(BoMapper.INSTANCE.fromBoPatchUserReqMap(boPatchUserRequest)));
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
		return BoMapper.INSTANCE.toBoGetUserRespMap(mappers.updateLastLoginDatetime(usernameOrEmail));
	}
}