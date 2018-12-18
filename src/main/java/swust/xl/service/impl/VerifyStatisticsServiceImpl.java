package swust.xl.service.impl;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import swust.xl.dao.StatisticsMappers;
import swust.xl.pojo.po.mysql.tables.pojos.VerifyStatistics;
import swust.xl.service.VerifyStatisticsService;

@Service
public class VerifyStatisticsServiceImpl implements VerifyStatisticsService {

	@Autowired
	private StatisticsMappers statisticsMappers;

	/**
	 * 初始化统计数据
	 * 
	 * @param userName
	 * @return VerifyStatistics
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public VerifyStatistics InitUserInfo(@Valid @NotBlank String userName) {
		return statisticsMappers.InitUserInfo(userName);
	}

	/**
	 * 根据用户名查询所有统计数据
	 * 
	 * @param userName
	 *            用户名
	 * @return VerifyStatistics 验证统计数据
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@Override
	public VerifyStatistics getStatistics(@Valid @NotBlank String userName) {
		return statisticsMappers.getStatistics(userName);
	}

	/**
	 * 验证次数+1
	 * 
	 * @param userName
	 * @return true-增加成功；false-增加失败
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean raiseCount(@Valid @NotBlank String userName) {
		return statisticsMappers.raiseCount(userName);
	}

	/**
	 * 通过验证次数+1
	 * 
	 * @param userName
	 * @return true-增加成功；false-增加失败
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean raisePassedCount(@Valid @NotBlank String userName) {
		return statisticsMappers.raisePassedCount(userName);
	}

	/**
	 * 未通过验证次数+1
	 * 
	 * @param userName
	 * @return true-增加成功；false-增加失败
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean raiseRefusedCount(@Valid @NotBlank String userName) {
		return statisticsMappers.raiseRefusedCount(userName);
	}

	/**
	 * 识别出机器人次数+1
	 * 
	 * @param userName
	 * @return true-增加成功；false-增加失败
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean raiseRobotCount(@Valid @NotBlank String userName) {
		return statisticsMappers.raiseRobotCount(userName);
	}

	/**
	 * 更新最后调用时间
	 * 
	 * @param userName
	 * @return true-更新成功；false-更新失败
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean updateLastInvokeDatetime(@Valid @NotBlank String userName) {
		return statisticsMappers.updateLastInvokeDatetime(userName);
	}

}