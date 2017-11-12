package io.postman.integration.repository;

import io.postman.common.exception.EventComponentException;
import io.postman.integration.domain.model.publisher.PublishStatus;
import io.postman.integration.domain.model.publisher.StatusInfo;

import java.util.Date;

/**
 * Created by caojun on 2017/11/7.
 * this is status description for publishing. it is a value object.
 */
public class StatusInfoSnapshot {
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

    public StatusInfoSnapshot(StatusInfo statusInfo){
        if (statusInfo == null)
            throw new EventComponentException("error when create StatusInfoSnapshot, statusInfo is null");
        this.status = statusInfo.status();
        this.publishNumber = statusInfo.publishNumber();
        this.updateTime = statusInfo.updateTime();
        this.errorMsg = statusInfo.errorMsg();
    }

    public StatusInfoSnapshot(PublishStatus status, Date updateTime, Integer publishNumber, String errorMsg) {
        this.status = status;
        this.updateTime = updateTime;
        this.publishNumber = publishNumber;
        this.errorMsg = errorMsg;
    }

    public StatusInfo transTo(){
        return new StatusInfo(this.status, this.updateTime, this.publishNumber, this.errorMsg);
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

    @Override
    public String toString() {
        return "StatusInfoSnapshot{" +
                "status=" + status +
                ", updateTime=" + updateTime +
                ", publishNumber=" + publishNumber +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}

