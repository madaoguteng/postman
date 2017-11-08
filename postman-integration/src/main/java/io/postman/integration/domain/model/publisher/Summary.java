package io.postman.integration.domain.model.publisher;

import io.postman.common.exception.EventComponentException;
import io.postman.common.util.StringUtil;

import java.util.Date;

/**
 * Created by caojun on 2017/11/7.
 * this is status description for publishing summary. it is a value object.
 */
public class Summary {
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

    public Summary(String eventName, Date publishTime, Integer publishMaxNumber, String publisher, String returnEventName) {
        if (StringUtil.isEmptyOrNull(eventName)) throw new EventComponentException("eventName is null !");
        this.eventName = eventName;
        this.publishTime = publishTime != null ? publishTime : new Date();
        this.publishMaxNumber = publishMaxNumber != null ? publishMaxNumber : 3;
        this.publisher = publisher;
        this.returnEventName = returnEventName;
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

