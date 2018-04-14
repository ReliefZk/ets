package com.reliefzk.middleware.ets.schedule;

import com.reliefzk.middleware.ets.schedule.common.TaskJob;
import com.reliefzk.middleware.ets.schedule.common.TaskModel;
import com.reliefzk.middleware.ets.schedule.common.TaskRunData;
import com.reliefzk.middleware.ets.schedule.common.TaskType;
import org.quartz.Scheduler;

/**
 * quartz调度器
 *    1，cron表达式调度器
 *    3，一次性的调度器
 */
public interface QuartzJobScheduler {

    /**
     * 根据相关参数创建调度任务
     *
     * @param jobModel
     */
    boolean scheduleJob(TaskModel jobModel, TaskRunData runData, Class<? extends TaskJob> taskJobClazz);

    /**
     * 删除相关调度任务
     *
     * @param jobModel
     */
    boolean removeJob(TaskModel jobModel);

    /**
     * 获取job type
     * @return
     */
    TaskType getTaskType();

    /**
     * 获取quartz scheduler
     * @return
     */
    Scheduler getScheduler();
}
