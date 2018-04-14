package com.reliefzk.middleware.ets.schedule.listener;

import com.alibaba.fastjson.JSON;

import com.reliefzk.middleware.ets.util.log.LogUtil;
import com.reliefzk.middleware.ets.util.log.Logger;
import com.reliefzk.middleware.ets.util.log.LoggerFactory;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.listeners.SchedulerListenerSupport;

public class TaskSchedulerListener extends SchedulerListenerSupport {
    private static Logger LOGGER = LoggerFactory.getLogger(TaskSchedulerListener.class);
    /**
     * @param jobDetail
     */
    public void jobAdded(JobDetail jobDetail) {
    }

    /**
     * @param jobKey
     */
    public void jobDeleted(JobKey jobKey) {
        LogUtil.info(LOGGER, "job: {} is removed!", JSON.toJSONString(jobKey));
    }

}
