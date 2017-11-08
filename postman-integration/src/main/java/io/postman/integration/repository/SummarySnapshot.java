package io.postman.integration.repository;

import io.postman.common.exception.EventComponentException;
import io.postman.integration.domain.model.publisher.Summary;

import java.util.Date;

/**
 * Created by caojun on 2017/11/7.
 * this is status description for publishing summary. it is a value object.
 */
public class SummarySnapshot {
    /**
     * 事件名称
     */
    private final String eventName;
    /**
     * 首次发布时间
     */
    private final Date publishTime;
    /**
     * 发布最大次数限制
     */
    private final Integer publishMaxNumber;
    /**
     * 发布者(事件归属业务标识)
     */
    private String publisher;
    /**
     * 返回消息的消息名称
     */
    private String returnEventName;

    public SummarySnapshot(String eventName, Date publishTime, Integer publishMaxNumber, String publisher, String returnEventName) {
        this.eventName = eventName;
        this.publishTime = publishTime;
        this.publishMaxNumber = publishMaxNumber;
        this.publisher = publisher;
        this.returnEventName = returnEventName;
    }

    public SummarySnapshot(Summary summary){
        if (summary == null)
            throw new EventComponentException("error when create SummarySnapshot, summary is null");
        this.eventName = summary.eventName();
        this.publishTime = summary.publishTime();
        this.publishMaxNumber = summary.publishMaxNumber();
        this.publisher = summary.publisher();
        this.returnEventName = summary.returnEventName();
    }

    public Summary transTo(){
        return new Summary(this.eventName, this.publishTime, this.publishMaxNumber, this.publisher, this.returnEventName);
    }
    public String eventName() {
        return eventName;
    }

    public Date publishTime() {
        return publishTime;
    }

    public Integer publishMaxNumber() {
        return publishMaxNumber;
    }

    public String publisher() {
        return publisher;
    }

    public String returnEventName() {
        return returnEventName;
    }
}

