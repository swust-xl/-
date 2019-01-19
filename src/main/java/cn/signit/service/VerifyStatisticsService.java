package cn.signit.service;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import cn.signit.pojo.po.mysql.tables.pojos.VerifyStatistics;

/**
 * 
 * 用户统计数据操作相关服务层接口
 *
 * @author xuLiang
 * @since 1.0.0
 */
@Validated
public interface VerifyStatisticsService {
	/**
	 * 初始化统计数据
	 * 
	 * @param userName
	 * @return VerifyStatistics
	 * @author xuLiang
	 * @since 1.0.0
	 */
	public VerifyStatistics initUserInfo(@Valid @NotBlank String userName);

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
