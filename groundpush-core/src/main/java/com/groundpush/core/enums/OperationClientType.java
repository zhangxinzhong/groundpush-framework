package com.groundpush.core.enums;

/**
 * OperationClientType
 *
 * @author hss
 * @date 2019/9/30 10:38
 */
public enum OperationClientType {
    APP(0),
    PC(1);
    private Integer client;
    OperationClientType(Integer client){
        this.client = client;
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }
}
