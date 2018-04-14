package com.reliefzk.middleware.ets.schedule.listener;

import com.reliefzk.middleware.ets.util.log.LogUtil;
import com.reliefzk.middleware.ets.util.log.Logger;
import com.reliefzk.middleware.ets.util.log.LoggerFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.listeners.JobListenerSupport;

/**
 *job监听器
 */
public class TaskJobListener extends JobListenerSupport {
    private static Logger LOGGER = LoggerFactory.getLogger(TaskJobListener.class);

    @Override
    public String getName() {
        return "mob-job-listener";
    }

    /**
     * 任务被执行前
     * @param context
     */
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        String jobDesc = context.getJobDetail().getDescription();
        LogUtil.info(LOGGER, "Job:{} is to be execute", jobDesc);
    }

    /**
     * 任务被执行后
     * @param context
     * @param jobException
     */
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        String jobDesc = context.getJobDetail().getDescription();
        LogUtil.info(LOGGER, "job: {} complete execute!", jobDesc);
    }

}
