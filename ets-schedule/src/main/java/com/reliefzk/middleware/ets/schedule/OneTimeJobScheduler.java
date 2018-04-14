package com.reliefzk.middleware.ets.schedule;

import java.util.TimeZone;

import com.reliefzk.middleware.ets.schedule.common.TaskModel;
import com.reliefzk.middleware.ets.schedule.common.TaskType;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

/**
 * 执行一次调度
 */
public class OneTimeJobScheduler extends EtsTaskScheduler {

    public OneTimeJobScheduler() {
        super(TaskType.ONCE_TIME);
    }

    @Override
    protected Trigger genJobTrigger(JobDetail jobDetail, TaskModel jobModel, JobDataMap jobDataMap, TimeZone timeZone) {
        //trigger
        Trigger trigger =TriggerBuilder.newTrigger()
                .withIdentity(jobModel.getName(), jobModel.getGroup())
                .withDescription(jobModel.getDescription())
                .usingJobData(jobDataMap)
                .forJob(jobDetail)
                .startNow()
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(1).withMisfireHandlingInstructionFireNow())
                .build();
        return trigger;
    }
}
