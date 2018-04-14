package com.reliefzk.middleware.ets.schedule.common;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * task job 抽象类
 */
public abstract class TaskJob extends QuartzTaskBean {

    /**
     * 执行的job类型
     */
    public TaskType taskType;

    public TaskJobExecutor taskJobExecutor;

    public TaskJob(TaskType taskType){
        this.taskType = taskType;
    }

    @Override
    public void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        TaskRunData runData = (TaskRunData) jobDataMap.get("");
        if(runData != null){
            taskJobExecutor.execute(runData);
        }
    }
}
