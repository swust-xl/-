package cn.signit.pojo.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import cn.signit.pojo.bo.BoAddUserRequest;
import cn.signit.pojo.bo.BoGetUserResp;
import cn.signit.pojo.bo.BoUpdateUserRequest;
import cn.signit.pojo.po.mysql.tables.pojos.UserPerson;
import cn.signit.util.transform.Transform;

/**
 * 创建BO与PO之间的映射
 *
 * @author xuLiang 1.0.0
 * @since 1.0.0
 */
@Mapper(uses = Transform.class)
public interface BoMapper {
	BoMapper INSTANCE = Mappers.getMapper(BoMapper.class);

	/**
	 * 数据库对象转化为BO层的对象
	 * 
	 * @param userPerson
	 * 
	 * @return 转化后的BO层对象
	 * 
	 * @author xuLiang
	 * 
	 * @since 1.0.0
	 */

	BoGetUserResp toBoGetUserResp(UserPerson userPerson);

	/**
	 * BO层的对象转化为数据库对象
	 * 
	 * @param boAddUserRequest
	 * 
	 * @return 转化后的数据库对象
	 * 
	 * @author xuLiang
	 * 
	 * @since 1.0.0
	 */
	@Mapping(target = "passwordSalt", source = "password")
	@Mapping(target = "username", source = "userName")
	UserPerson fromBoAddUserReq(BoAddUserRequest boAddUserRequest);

	/**
	 * BO层的对象转化为数据库对象
	 * 
	 * @param boPatchUserReq
	 * 
	 * @return 转化后的数据库对象
	 * 
	 * @author xuLiang
	 * 
	 * @since 1.0.0
	 */
	@Mapping(target = "passwordSalt", source = "password")
	@Mapping(target = "username", source = "userName")
	UserPerson fromBoUpdateUserReq(BoUpdateUserRequest boUpdateUserReq);

}
