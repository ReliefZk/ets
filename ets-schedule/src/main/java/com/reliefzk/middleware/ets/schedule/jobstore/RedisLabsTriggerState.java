package com.reliefzk.middleware.ets.schedule.jobstore;

/**
 * Created by kui.zhouk on 16/3/2.
 */
public enum RedisLabsTriggerState {

    WAITING("waiting_triggers"),
    PAUSED("paused_triggers"),
    BLOCKED("blocked_triggers"),
    PAUSED_BLOCKED("paused_blocked_triggers"),
    ACQUIRED("acquired_triggers"),
    COMPLETED("completed_triggers"),
    ERROR("error_triggers");

    private final String key;

    private RedisLabsTriggerState(String key) {
        this.key = key;
    }

    public String getKey() { return key; }

    public static RedisLabsTriggerState toState(String key){
        for (RedisLabsTriggerState state : RedisLabsTriggerState.values())
            if (state.getKey().equals(key))
                return state;

        return null;
    }
}
