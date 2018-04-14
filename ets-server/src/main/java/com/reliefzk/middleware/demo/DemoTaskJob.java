package com.reliefzk.middleware.demo;

import com.reliefzk.middleware.ets.schedule.common.TaskJob;
import com.reliefzk.middleware.ets.schedule.common.TaskRunData;
import com.reliefzk.middleware.ets.schedule.common.TaskType;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class DemoTaskJob extends TaskJob {

    public DemoTaskJob() {
        super(TaskType.ONCE_TIME);
    }


    @Override
    public void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.err.println("---------");
    }

}
