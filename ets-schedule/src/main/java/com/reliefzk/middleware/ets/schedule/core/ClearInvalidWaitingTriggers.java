package com.reliefzk.middleware.ets.schedule.core;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.reliefzk.middleware.ets.schedule.jobstore.MixinRedisTriggerState;
import com.reliefzk.middleware.ets.schedule.redis.RedisRepository;
import com.reliefzk.middleware.ets.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 检查trigger有效性
 */
public class ClearInvalidWaitingTriggers implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(ClearInvalidWaitingTriggers.class);

    private static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    @Autowired
    private RedisRepository redisRepository;

    private void doTask() {
        try {
            long endScore = System.currentTimeMillis() - getIntervalMs();
            long count = redisRepository.zremByScore(MixinRedisTriggerState.WAITING.getKey(), 0, endScore);
            logger.info("RemoveInvalidTrigger start: 0, endDate: {}, count: {}", DateUtil.formatDateStrYyyyMmDdHhMmSs(new Date(endScore)), count);
        } catch (Exception e) {
            logger.error("ZremByScoreError", e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    doTask();
                } catch (Exception e) {
                    logger.error("TaskScheduleError", e);
                }

            }
        };
        executor.scheduleAtFixedRate(runnable, this.getInitialDelay(), this.getIntervalMs(),
                        TimeUnit.MILLISECONDS);
    }


    /**
     * 周期设置，单位毫秒
     *
     * @return
     */
    public long getIntervalMs(){
        return 10 * 60 * 1000;
    }

    /**
     * 起始延迟，单位毫秒
     *
     * @return
     */
    public long getInitialDelay(){
        return 2 * 60 * 1000;
    }
}
