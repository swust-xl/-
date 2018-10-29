package swust.xl.pojo.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import swust.xl.pojo.bo.adduser.request.BoAddUserRequest;
import swust.xl.pojo.bo.getuser.response.BoGetUserResponse;
import swust.xl.pojo.bo.patchuser.request.BoPatchUserRequest;
import swust.xl.pojo.po.mysql.tables.pojos.Users;
import swust.xl.pojo.po.user.transform.UserTransform;

/**
 * 创建BO与PO之间的映射
 * </p>
 *
 * @author xuLiang 0.0.1
 * @since 0.0.1
 */
@Mapper(uses = UserTransform.class)
public interface BoMapper {
	BoMapper INSTANCE = Mappers.getMapper(BoMapper.class);

	/*
	 * 数据库对象转化为BO层的对象
	 * 
	 * @param Users
	 * 
	 * @return 转化后的BO层对象
	 * 
	 * @author xuLiang
	 * 
	 * @since 0.0.1
	 */

	BoGetUserResponse toBoGetUserRespMap(Users Users);

	/*
	 * BO层的对象转化为数据库对象
	 * 
	 * @param BoAddUserRequest
	 * 
	 * @return 转化后的数据库对象
	 * 
	 * @author xuLiang
	 * 
	 * @since 0.0.1
	 */

	Users fromBoAddUserReqMap(BoAddUserRequest boAddUserRequest);
	/*
	 * BO层的对象转化为数据库对象
	 * 
	 * @param boPatchUserReq
	 * 
	 * @return 转化后的数据库对象
	 * 
	 * @author xuLiang
	 * 
	 * @since 0.0.1
	 */

	Users fromBoPatchUserReqMap(BoPatchUserRequest boPatchUserReq);

}
