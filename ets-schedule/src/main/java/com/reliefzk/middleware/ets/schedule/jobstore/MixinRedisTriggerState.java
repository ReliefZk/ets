package com.reliefzk.middleware.ets.schedule.jobstore;

import org.quartz.Trigger;

/**
 * Created by kui.zhouk on 16/2/29.
 */
public enum MixinRedisTriggerState {
    WAITING("waiting_triggers", Trigger.TriggerState.NORMAL),
    PAUSED("paused_triggers", Trigger.TriggerState.PAUSED),
    BLOCKED("blocked_triggers", Trigger.TriggerState.BLOCKED),
    PAUSED_BLOCKED("paused_blocked_triggers", Trigger.TriggerState.PAUSED),
    ACQUIRED("acquired_triggers", Trigger.TriggerState.NORMAL),
    COMPLETED("completed_triggers", Trigger.TriggerState.COMPLETE),
    ERROR("error_triggers", Trigger.TriggerState.ERROR);

    private final String key;

    private final Trigger.TriggerState triggerState;

    MixinRedisTriggerState(String key, Trigger.TriggerState triggerState) {
        this.key = key;
        this.triggerState = triggerState;
    }

    public String getKey() {
        return key;
    }

    public Trigger.TriggerState getTriggerState() {
        return triggerState;
    }

    public static MixinRedisTriggerState toState(String key){
        for (MixinRedisTriggerState state : MixinRedisTriggerState.values()) {
            if(state.getKey().equals(key)){
                return state;
            }
        }
        return null;
    }
}
