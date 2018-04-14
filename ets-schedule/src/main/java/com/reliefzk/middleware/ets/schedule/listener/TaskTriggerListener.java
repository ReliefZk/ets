package com.reliefzk.middleware.ets.schedule.listener;

import com.reliefzk.middleware.ets.util.log.Logger;
import com.reliefzk.middleware.ets.util.log.LoggerFactory;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.listeners.TriggerListenerSupport;

/**
 * Trigger监听器
 */
public class TaskTriggerListener extends TriggerListenerSupport {

    private static Logger LOGGER = LoggerFactory.getLogger(TaskTriggerListener.class);

    @Override
    public String getName() {
        return "ets-trigger-listener";
    }

    /**
     * 触发执行
     * @param trigger
     * @param context
     */
    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {

    }

    /**
     * 触发完成
     *
     * @param trigger
     * @param context
     * @param triggerInstructionCode
     */
    @Override
    public void triggerComplete(
            Trigger trigger,
            JobExecutionContext context,
            Trigger.CompletedExecutionInstruction triggerInstructionCode) {

    }

}
