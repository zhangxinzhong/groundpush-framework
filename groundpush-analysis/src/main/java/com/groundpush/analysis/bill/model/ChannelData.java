package com.groundpush.analysis.bill.model;

import java.io.Serializable;

/**
 * .
 * <p>
 * 工程名： groundpush-framework
 *
 * @author luzq
 * @version 1.0
 * @createDate 2019-09-19 11:12
 * @since JDK  1.8
 */
public class ChannelData implements Serializable {
    private int id;
    private int channelId;
    private int taskId;
    private String fileName;
    private String mapping;
    private String createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMapping() {
        return mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
