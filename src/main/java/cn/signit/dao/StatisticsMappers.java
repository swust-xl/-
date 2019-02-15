package cn.signit.dao;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import cn.signit.pojo.po.mysql.tables.pojos.VerifyStatistics;

/**
 * 
 * 用户统计数据操作相关Dao层接口.
 * 
 * @author xuLiang
 * @since 1.0.0
 */
@Validated
public interface StatisticsMappers {
	/**
	 * 初始化统计数据
	 * 
	 * @param userName
	 * @return 更新后的统计数据
	 * @author xuLiang
	 * @since 1.0.0
	 */
	public VerifyStatistics initUserInfo(@Valid @NotBlank String userName);

	/**
	 * 用户名字更改时,数据统计表的用户名一同更改
	 * 
	 * @param newName
	 * @return true-更改成功；false-更改失败
	 * @author xuLiang
	 * @since 1.0.0
	 */
	public boolean changeUserName(@Valid @NotBlank String oldName, @Valid @NotBlank String newName);

	/**
	 * 根据用户名查询所有统计数据
	 * 
	 * @param userName
	 *            用户名
	 * @return VerifyStatistics 验证统计数据
	 * @author xuLiang
	 * @since 1.0.0
	 */
	public VerifyStatistics getStatistics(@Valid @NotBlank String userName);

	/**
	 * 验证次数+1
	 * 
	 * @param userName
	 * @return true-增加成功；false-增加失败
	 * @author xuLiang
	 * @since 1.0.0
	 */
	public boolean raiseCount(@Valid @NotBlank String userName);

	/**
	 * 通过验证次数+1
	 * 
	 * @param userName
	 * @return true-增加成功；false-增加失败
	 * @author xuLiang
	 * @since 1.0.0
	 */
	public boolean raisePassedCount(@Valid @NotBlank String userName);

	/**
	 * 未通过验证次数+1
	 * 
	 * @param userName
	 * @return true-增加成功；false-增加失败
	 * @author xuLiang
	 * @since 1.0.0
	 */
	public boolean raiseRefusedCount(@Valid @NotBlank String userName);

	/**
	 * 识别出机器人次数+1
	 * 
	 * @param userName
	 * @return true-增加成功；false-增加失败
	 * @author xuLiang
	 * @since 1.0.0
	 */
	public boolean raiseRobotCount(@Valid @NotBlank String userName);

	/**
	 * 更新最后调用时间
	 * 
	 * @param userName
	 * @return true-更新成功；false-更新失败
	 * @author xuLiang
	 * @since 1.0.0
	 */
	public boolean updateLastInvokeDatetime(@Valid @NotBlank String userName);

}
