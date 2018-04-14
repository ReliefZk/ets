package com.reliefzk.middleware.ets.schedule.common;

/**
 * 定时调度任务执行器
 */
public interface TaskJobExecutor {

    /**
     * 执行pipeline
     * @param runData job执行过程中需要使用的参数
     */
    void execute(TaskRunData runData);

    /**
     * jop类型
     * @return
     */
    TaskType getTaskType();

}
