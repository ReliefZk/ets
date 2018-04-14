package com.reliefzk.middleware.ets.schedule.common;

/**
 * 任务类型
 */
public enum TaskType {
    /**
     * cron表达式类型
     */
    CRON,
    /**
     * 只执行一次
     */
    ONCE_TIME,
    /**
     * 心跳(每间隔一段时间执行)
     */
    BEAT_HEAD,
}
