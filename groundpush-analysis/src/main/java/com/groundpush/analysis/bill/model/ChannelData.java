package com.groundpush.analysis.bill.model;

import java.io.Serializable;
import java.util.Date;

/**
 * .
 * <p>
 * 工程名： groundpush-framework
 *
 * @author luzq
 * @version 1.0
 * @createDate 2019-09-22 20:59
 * @since JDK  1.8
 */
public class ChannelData implements Serializable {
    private int id;
    private int channelId;
    private int taskId;
    private String uniqueCode;
    private Date channelTime;
    private boolean isEffective;
    private boolean isExistOrder;
    private String description;
    private Date createTime;
    private byte isShow;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public Date getChannelTime() {
        return channelTime;
    }

    public void setChannelTime(Date channelTime) {
        this.channelTime = channelTime;
    }

    public boolean isEffective(Boolean isEffective) {
        return this.isEffective;
    }

    public void setEffective(boolean effective) {
        isEffective = effective;
    }

    public boolean isExistOrder() {
        return isExistOrder;
    }

    public void setExistOrder(boolean existOrder) {
        isExistOrder = existOrder;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public byte getIsShow() {
        return isShow;
    }

    public void setIsShow(byte isShow) {
        this.isShow = isShow;
    }
}
