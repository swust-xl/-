package swust.xl.pojo.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import swust.xl.pojo.bo.adduser.request.BoAddUserRequest;
import swust.xl.pojo.bo.getuser.response.BoGetUserResponse;
import swust.xl.pojo.bo.patchuser.request.BoPatchUserRequest;
import swust.xl.pojo.vo.adduser.requset.VoAddUserRequest;
import swust.xl.pojo.vo.getuser.response.VoGetUserResponse;
import swust.xl.pojo.vo.patchuser.request.VoPatchUserRequest;

/**
 * 创建VO与BO之间的映射
 *
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

	VoGetUserResponse fromBoToVoGetUserResponseMap(BoGetUserResponse boGetUserResponse);

	/**
	 * VO层转化为BO层对象
	 * 
	 * @param voAddUserRequest
	 * @return 转化后的BO层对象
	 *
	 * 
	 * @author xuLiang
	 * @since 1.0.0
	 */
	BoAddUserRequest fromVoToBoAddUserRequestMap(VoAddUserRequest voAddUserRequest);

	/**
	 * VO层转化为BO层对象
	 * 
	 * @param voPatchUserRequest
	 * @return 转化后的BO层对象
	 *
	 * 
	 * @author xuLiang
	 * @since 1.0.0
	 */
	BoPatchUserRequest fromVoToBoPatchUserRequestMap(VoPatchUserRequest voPatchUserRequest);

}
