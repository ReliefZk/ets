package com.reliefzk.middleware.ets.server;

import com.reliefzk.middleware.demo.DemoTaskJob;
import com.reliefzk.middleware.ets.schedule.EtsTaskScheduler;
import com.reliefzk.middleware.ets.schedule.common.TaskJob;
import com.reliefzk.middleware.ets.schedule.common.TaskModel;
import com.reliefzk.middleware.ets.schedule.common.TaskRunData;
import com.reliefzk.middleware.ets.schedule.common.TaskType;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class SampleController implements ApplicationContextAware {

    @Autowired
    private EtsTaskScheduler oneTimeJobScheduler;
    @Autowired
    private EtsTaskScheduler cronJobScheduler;

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

    @RequestMapping("/one.json")
    @ResponseBody
    public String one(String id) {
        TaskModel jobModel = new TaskModel();
        jobModel.setGroup("default");
        jobModel.setName("test");
        jobModel.setIsStartNow(true);
        jobModel.setTaskType(TaskType.ONCE_TIME);
        TaskRunData runData =  new TaskRunData();
        oneTimeJobScheduler.scheduleJob(jobModel, runData, DemoTaskJob.class);

        return "xxx";
    }


    @RequestMapping("/bean.json")
    @ResponseBody
    String bean(String id) {
        Object bean = applicationContext.getBean(id);
        return bean.toString();
    }

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void setOneTimeJobScheduler(EtsTaskScheduler oneTimeJobScheduler) {
        this.oneTimeJobScheduler = oneTimeJobScheduler;
    }

    public void setCronJobScheduler(EtsTaskScheduler cronJobScheduler) {
        this.cronJobScheduler = cronJobScheduler;
    }
}
