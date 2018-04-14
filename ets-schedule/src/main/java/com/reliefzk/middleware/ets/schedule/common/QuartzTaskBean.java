package com.reliefzk.middleware.ets.schedule.common;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;

/**
 * quartz job pip
 */
public abstract class QuartzTaskBean implements Job {
    /**
     * job执行管道
     */
    protected TaskJobExecutor jobPipeline;

    public TaskJobExecutor getJobPipeline() {
        return jobPipeline;
    }

    public void setJobPipeline(TaskJobExecutor jobPipeline) {
        this.jobPipeline = jobPipeline;
    }

    /**
     * This implementation applies the passed-in job data map as bean property
     * values, and delegates to {@code executeInternal} afterwards.
     * @see #executeInternal
     */
    public final void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            SchedulerContext schCtx = context.getScheduler().getContext();
            JobDataMap dataMap = context.getMergedJobDataMap();
            /**
             * set jobpipeline
             */
            //TaskJobExecutor jobPipeline = (TaskJobExecutor)schCtx.get("");
        }
        catch (SchedulerException ex) {
            throw new JobExecutionException(ex);
        }
        executeInternal(context);
    }

    /**
     * Execute the actual job. The job data map will already have been
     * applied as bean property values by execute. The contract is
     * exactly the same as for the standard Quartz execute method.
     * @see #execute
     */
    protected abstract void executeInternal(JobExecutionContext context) throws JobExecutionException;

}
