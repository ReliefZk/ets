package com.reliefzk.middleware.ets.schedule;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import com.alibaba.fastjson.JSON;

import com.reliefzk.middleware.ets.schedule.common.TaskJob;
import com.reliefzk.middleware.ets.schedule.common.TaskJobExecutor;
import com.reliefzk.middleware.ets.schedule.common.TaskModel;
import com.reliefzk.middleware.ets.schedule.common.TaskRunData;
import com.reliefzk.middleware.ets.schedule.common.TaskType;
import com.reliefzk.middleware.ets.schedule.redis.RedisRepository;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.ListenerManager;
import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.quartz.SchedulerListener;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * ets 调度器
 */
public abstract class EtsTaskScheduler implements QuartzJobScheduler, ApplicationContextAware, InitializingBean {

    private static Logger LOGGER = LoggerFactory.getLogger(EtsTaskScheduler.class);
    /**
     * job 类型
     */
    protected TaskType taskType;

    public EtsTaskScheduler(TaskType taskType){
        this.taskType = taskType;
    }
    /**
     * kvstore repository
     */
    protected RedisRepository   redisRepository;

    private   SchedulerListener taskSchedulerListener;

    private   JobListener       taskJobListener;

    private   TriggerListener   taskTriggerListener;
    /**
     * scheduler 时区
     */
    protected TimeZone          timeZone;

    /**
     * JobType : JobPipelineName map
     */
    private Map<TaskType, String> jobPipelineMap = new HashMap<TaskType, String>();

    /**
     * 调度器
     */
    private Scheduler scheduler;

    @Override
    public boolean scheduleJob(TaskModel jobModel, TaskRunData runData, Class<? extends TaskJob> taskJobClazz) {
        try {
            JobDataMap jobDataMap = genJobDataMap(runData);
            JobDetail jobDetail = genJobDetail(jobModel, taskJobClazz, jobDataMap);
            Trigger trigger = genJobTrigger(jobDetail, jobModel, jobDataMap, timeZone);
            /**
             * double check local scheduler exists
             */
            if(!scheduler.checkExists(jobDetail.getKey())){
                Date nextDate = scheduler.scheduleJob(jobDetail, trigger);
                LOGGER.info("schedule job:{} success! next date: {}", JSON.toJSONString(jobDetail.getKey()), nextDate);
            } else {
                Date nextDate = scheduler.rescheduleJob(trigger.getKey(), trigger);
                LOGGER.info("reschedule job:{} success! next date: {}", JSON.toJSONString(jobDetail.getKey()), nextDate);
            }
        } catch (Exception e) {
            LOGGER.error("schedule job[{}] err!", jobModel, e);
            return false;
        }
        return true;
    }

    @Override
    public boolean removeJob(TaskModel jobModel) {
        JobKey jobKey = new JobKey(jobModel.getName(), jobModel.getGroup());
        try {
            if(scheduler.checkExists(jobKey)){
                boolean state = scheduler.deleteJob(jobKey);
                LOGGER.info("delete job:{} result: {}", JSON.toJSONString(jobKey), state);
            } else {
                LOGGER.error("job:{} not exits!", JSON.toJSONString(jobKey));
            }
        } catch (Exception e) {
            LOGGER.error("delete job:{} error.", JSON.toJSONString(jobKey) , e);
            return false;
        }
        return true;
    }

    public JobDataMap genJobDataMap(TaskRunData runData){
        JobDataMap jobDataMap = new JobDataMap();
        return jobDataMap;
    }

    /**
     * spring context
     */
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        Map<String, TaskJobExecutor> jobMap = applicationContext.getBeansOfType(TaskJobExecutor.class);
        for(String name : jobMap.keySet()){
            TaskJobExecutor jobPipeline = jobMap.get(name);
            jobPipelineMap.put(jobPipeline.getTaskType(), name);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        addScheduerListener();
        initJobPipeline();
    }

    private void initJobPipeline() throws SchedulerException {
        SchedulerContext schedulerContext = scheduler.getContext();
        Map<String, TaskJobExecutor> jobMap = applicationContext.getBeansOfType(TaskJobExecutor.class);
        for(String name : jobMap.keySet()){
            TaskJobExecutor jobPipeline = jobMap.get(name);
            Object obj = schedulerContext.get(name);
            if(obj == null){
                schedulerContext.put(name, jobPipeline);
            }
        }
    }


    public void addScheduerListener(){
        if(scheduler != null){
            ListenerManager listenerManager = null;
            try {
                listenerManager = scheduler.getListenerManager();
            } catch (SchedulerException e) {
                LOGGER.error("get scheduler listener manager err!", e);
            }
            if(listenerManager != null){
                listenerManager.addSchedulerListener(taskSchedulerListener);
                listenerManager.addJobListener(taskJobListener);
                listenerManager.addTriggerListener(taskTriggerListener);
            }
        }
    }

    @Override
    public TaskType getTaskType(){
        return taskType;
    }

    @Override
    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }


    protected JobDetail genJobDetail(TaskModel jobModel, Class<? extends TaskJob> jobClazz, JobDataMap jobDataMap) {
        // job detail
        JobDetail jobDetail = JobBuilder.newJob(jobClazz)
                .withIdentity(jobModel.getName(), jobModel.getGroup())
                .withDescription(jobModel.getDescription())
                .requestRecovery(jobModel.isShouldRecover())
                .storeDurably(jobModel.isDurability())
                .usingJobData(jobDataMap)
                .build();
        return jobDetail;
    }

    /**
     *
     * @param jobDetail
     * @param jobModel
     * @param jobDataMap
     * @return
     */
    protected abstract Trigger genJobTrigger(JobDetail jobDetail, TaskModel jobModel, JobDataMap jobDataMap, TimeZone timeZone);

    public void setRedisRepository(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }

    public void setTaskSchedulerListener(SchedulerListener taskSchedulerListener) {
        this.taskSchedulerListener = taskSchedulerListener;
    }

    public void setTaskJobListener(JobListener taskJobListener) {
        this.taskJobListener = taskJobListener;
    }

    public void setTaskTriggerListener(TriggerListener taskTriggerListener) {
        this.taskTriggerListener = taskTriggerListener;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }
}
