package cn.signit.util.scheduledthreadpoolexecutorfactory;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

/**
 * 用来返回ScheduledThreadPoolExecutor，避免创建多个线程池和线程工厂
 *  this class is used as:
 * {@code @Autowired  ScheduledThreadPoolExecutorFactory executorFactory;}
 * 
 * @author xuLiang
 * @since 1.0.0
 */
@Component
public class ScheduledThreadPoolExecutorFactory implements ThreadFactory {

	@Override
	public Thread newThread(Runnable r) {
		Thread thread = new Thread(r, "ScheduledTask");
		return thread;
	}

	private final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(Integer.MAX_VALUE, this);

	public ScheduledThreadPoolExecutor newExecutor(int corePoolSize) {
		return newExecutor(corePoolSize, Integer.MAX_VALUE, false);
	}

	public ScheduledThreadPoolExecutor newExecutor(int corePoolSize, long keepAliveTime,
			boolean allowCoreThreadTimeOut) {
		executor.setCorePoolSize(corePoolSize);
		executor.setKeepAliveTime(keepAliveTime, TimeUnit.SECONDS);
		executor.allowCoreThreadTimeOut(allowCoreThreadTimeOut);
		return executor;
	}

}