package com.reliefzk.middleware.ets.schedule.common;

import java.util.Date;

/**
 * 任务模型
 */
public class TaskModel {
    /**
     * job名称
     */
    private String name;

    /**
     * job所在组
     */
    private String group;

    /**
     * 描述
     */
    private String description;

    /**
     * job模型
     */
    private TaskType taskType;

    /**
     * job开始日期
     */
    private Date startDate;

    /**
     * job结束日期
     */
    private Date endDate;

    /**
     * job的cron表达式
     */
    private String cronExpr;

    /**
     * 是否立即执行
     */
    private boolean isStartNow;

    /**
     * job是否持久化
     *
     * 如果一个Job是非持久化的，一旦没有任何活跃的触发器关联这个Job实例时，这个实例会自动地从调度器中移除
     */
    private boolean isDurability;

    /**
     * 系统重启是否重启调度
     *
     * RequestsRecovery-如果一个Job设置了请求恢复参数，并且在调度器强制关闭过程中恰好在执行
     * （强制关闭的情况例如：运行的线程崩溃，或者服务器宕机），当调度器重启时，它会重新被执行。
     */
    private boolean shouldRecover = true;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public boolean isStartNow() {
        return isStartNow;
    }

    public void setIsStartNow(boolean isStartNow) {
        this.isStartNow = isStartNow;
    }

    public boolean isDurability() {
        return isDurability;
    }

    public void setIsDurability(boolean isDurability) {
        this.isDurability = isDurability;
    }

    public boolean isShouldRecover() {
        return shouldRecover;
    }

    public void setShouldRecover(boolean shouldRecover) {
        this.shouldRecover = shouldRecover;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getCronExpr() {
        return cronExpr;
    }

    public void setCronExpr(String cronExpr) {
        this.cronExpr = cronExpr;
    }

    public void setStartNow(boolean startNow) {
        isStartNow = startNow;
    }

    public void setDurability(boolean durability) {
        isDurability = durability;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    @Override
    public String toString() {
        return "TaskModel{" +
                "name='" + name + '\'' +
                ", group='" + group + '\'' +
                ", description='" + description + '\'' +
                ", taskType=" + taskType +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", cronExpr='" + cronExpr + '\'' +
                ", isStartNow=" + isStartNow +
                ", isDurability=" + isDurability +
                ", shouldRecover=" + shouldRecover +
                '}';
    }
}
