package cn.signit.pojo.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import cn.signit.pojo.bo.BoAddUserRequest;
import cn.signit.pojo.bo.BoGetUserResp;
import cn.signit.pojo.bo.BoUpdateUserRequest;
import cn.signit.pojo.vo.VoAddUserRequest;
import cn.signit.pojo.vo.VoGetUserResp;
import cn.signit.pojo.vo.VoUpdateUserRequest;

/**
 * 创建VO与BO之间的映射
 *
 * @author xuLiang
 * @since 1.0.0
 */
@Mapper
public interface VoMapper {
	VoMapper INSTANCE = Mappers.getMapper(VoMapper.class);

	/**
	 * BO层转化为VO层对象
	 * 
	 * @param boGetUserResponse
	 * @return 转化后的VO层对象
	 * 
	 * @author xuLiang
	 * @since 1.0.0
	 */

	VoGetUserResp fromBoToVoGetUserResponse(BoGetUserResp boGetUserResponse);

	/**
	 * VO层转化为BO层对象
	 * 
	 * @param voAddUserRequest
	 * @return 转化后的BO层对象
	 * @author xuLiang
	 * @since 1.0.0
	 */
	BoAddUserRequest fromVoToBoAddUserRequest(VoAddUserRequest voAddUserRequest);

	/**
	 * VO层转化为BO层对象
	 * 
	 * @param voPatchUserRequest
	 * @return 转化后的BO层对象
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@Mapping(target = "password", source = "newPassword")
	BoUpdateUserRequest fromVoToBoUpdateUserRequest(VoUpdateUserRequest voUpdateUserRequest);

}
