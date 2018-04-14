package com.reliefzk.middleware.ets.schedule;

import java.util.Date;
import java.util.TimeZone;
import com.reliefzk.middleware.ets.schedule.common.TaskModel;
import com.reliefzk.middleware.ets.schedule.common.TaskType;
import com.reliefzk.middleware.ets.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

/**
 * 正则执行调度
 */
public class CronJobScheduler extends EtsTaskScheduler {

    public CronJobScheduler() {
        super(TaskType.CRON);
    }

    @Override
    protected Trigger genJobTrigger(JobDetail jobDetail, TaskModel jobModel, JobDataMap jobDataMap, TimeZone timeZone) {
        Date startDate = jobModel.getStartDate();
        Date nowDate = new Date();
        /**
         * 如果是同一天 为了防止循环任务在立即执行 譬如 0 0 9 * * ? 这种每天9点钟执行一次
         * 而开始时间是 2016-01-01 就会立即执行 和过期策略有关
         */
        if(StringUtils.equals(DateUtil.formatShort(startDate), DateUtil.formatShort(nowDate))){
            startDate = nowDate;
        }
        //trigger
        TriggerBuilder<CronTrigger> triggerBuilder = TriggerBuilder.newTrigger()
                .withIdentity(jobModel.getName(), jobModel.getGroup())
                .withDescription(jobModel.getDescription())
                .usingJobData(jobDataMap)
                .startAt(startDate)
                .endAt(jobModel.getEndDate())
                .forJob(jobDetail)
                .withSchedule(CronScheduleBuilder.cronSchedule(jobModel.getCronExpr()).inTimeZone(timeZone).withMisfireHandlingInstructionFireAndProceed());
        if(jobModel.isStartNow()){
            triggerBuilder.startNow();
        }
        return triggerBuilder.build();
    }

}
