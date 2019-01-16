package swust.xl.dao.impl;

import static swust.xl.pojo.po.mysql.tables.VerifyStatistics.VERIFY_STATISTICS;

import java.sql.Timestamp;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import swust.xl.dao.StatisticsMappers;
import swust.xl.pojo.po.mysql.tables.pojos.VerifyStatistics;
import swust.xl.pojo.po.mysql.tables.records.VerifyStatisticsRecord;

/**
 * 统计数据相关Dao层实现类
 * 
 * @author xuLiang
 * @since 1.0.0
 */
@Repository
public class StatisticsMappersImpl implements StatisticsMappers {

	@Autowired
	private DSLContext dsl;

	/**
	 * 初始化统计数据
	 * 
	 * @param userName
	 * @return VerifyStatistics
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@Override
	public VerifyStatistics InitUserInfo(String userName) {
		VerifyStatisticsRecord record = new VerifyStatisticsRecord();
		record = dsl.newRecord(VERIFY_STATISTICS);
		record.setUsername(userName);
		int initResult = record.store();
		if (initResult == 1) {
			return record.into(VerifyStatistics.class);
		}
		return null;
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
	public VerifyStatistics getStatistics(String userName) {
		VerifyStatisticsRecord record = dsl.selectFrom(VERIFY_STATISTICS).where(VERIFY_STATISTICS.USERNAME.eq(userName))
				.fetchOne();
		if (record != null) {
			return record.into(VerifyStatistics.class);
		}
		return null;
	}

	/**
	 * 验证次数+1
	 * 
	 * @param userName
	 * @return true-增加成功；false-增加失败
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@Override
	public boolean raiseCount(String userName) {
		VerifyStatisticsRecord record = dsl.selectFrom(VERIFY_STATISTICS).where(VERIFY_STATISTICS.USERNAME.eq(userName))
				.fetchOne();
		if (record != null) {
			record.setCount(record.getCount() + 1);
			record.store();
			return true;
		}
		return false;
	}

	/**
	 * 通过验证次数+1
	 * 
	 * @param userName
	 * @return true-增加成功；false-增加失败
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@Override
	public boolean raisePassedCount(String userName) {
		VerifyStatisticsRecord record = dsl.selectFrom(VERIFY_STATISTICS).where(VERIFY_STATISTICS.USERNAME.eq(userName))
				.fetchOne();
		if (record != null) {
			record.setPassedCount(record.getPassedCount() + 1);
			record.store();
			return true;
		}
		return false;
	}

	/**
	 * 未通过验证次数+1
	 * 
	 * @param userName
	 * @return true-增加成功；false-增加失败
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@Override
	public boolean raiseRefusedCount(String userName) {
		VerifyStatisticsRecord record = dsl.selectFrom(VERIFY_STATISTICS).where(VERIFY_STATISTICS.USERNAME.eq(userName))
				.fetchOne();
		if (record != null) {
			record.setRefusedCount(record.getRefusedCount() + 1);
			record.store();
			return true;
		}
		return false;
	}

	/**
	 * 识别出机器人次数+1
	 * 
	 * @param userName
	 * @return true-增加成功；false-增加失败
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@Override
	public boolean raiseRobotCount(String userName) {
		VerifyStatisticsRecord record = dsl.selectFrom(VERIFY_STATISTICS).where(VERIFY_STATISTICS.USERNAME.eq(userName))
				.fetchOne();
		if (record != null) {
			record.setRobotCount(record.getRobotCount() + 1);
			record.store();
			return true;
		}
		return false;
	}

	/**
	 * 更新最后调用时间
	 * 
	 * @param userName
	 * @return true-更新成功；false-更新失败
	 * @author xuLiang
	 * @since 1.0.0
	 */
	@Override
	public boolean updateLastInvokeDatetime(String userName) {
		VerifyStatisticsRecord record = dsl.selectFrom(VERIFY_STATISTICS).where(VERIFY_STATISTICS.USERNAME.eq(userName))
				.fetchOne();
		if (record != null) {
			record.setLastInvokeDatetime(new Timestamp(System.currentTimeMillis()));
			record.store();
			return true;
		}
		return false;
	}

}