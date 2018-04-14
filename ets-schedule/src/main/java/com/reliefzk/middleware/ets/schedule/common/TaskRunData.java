package com.reliefzk.middleware.ets.schedule.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.alibaba.fastjson.JSON;

/**
 * 调度执行过程使用的数据
 */
public class TaskRunData implements Serializable {

    private Map<String, Object> rundata = new HashMap<String, Object>();

    public void put(String key, Object data){
        rundata.put(key, data);
    }

    public <T> T get(String key){
        Object obj = rundata.get(key);
        if(obj == null){
            return null;
        }
        return (T)obj;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
