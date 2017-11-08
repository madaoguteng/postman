package io.postman.integration.domain.model.publisher;

import java.util.Date;

/**
 * Created by caojun on 2017/11/7.
 * this is status description for publishing. it is a value object.
 */
public class StatusInfo {
    /**
     * 发布状态
     */
    private PublishStatus status;
    private Date updateTime;
    /**
     * 发布次数
     */
    private Integer publishNumber;
    /**
     * 错误消息
     */
    private String errorMsg;

    public StatusInfo(){
        this(null, null, null, null);
    }

    private StatusInfo(PublishStatus status, Integer publishNumber){
        this.publishNumber = publishNumber;
    }

    public StatusInfo(PublishStatus status, Date updateTime, Integer publishNumber, String errorMsg) {
        this.status = status != null ? status : PublishStatus.PUBLISHING;
        this.updateTime = updateTime != null ? updateTime : new Date();
        this.publishNumber = publishNumber != null ? publishNumber : 0;
        this.errorMsg = errorMsg;
    }

    public static StatusInfo initStatusInfo(){
        return new StatusInfo(PublishStatus.PUBLISHING, 0);
    }

    public PublishStatus status() {
        return status;
    }

    public Date updateTime() {
        return updateTime;
    }

    public Integer publishNumber() {
        return publishNumber;
    }

    public String errorMsg() {
        return errorMsg;
    }
}

