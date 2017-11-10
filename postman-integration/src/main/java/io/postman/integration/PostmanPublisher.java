package io.postman.integration;

import io.postman.common.exception.EventComponentException;

/**
 * Created by caojun on 2017/11/3.
 */
public interface PostmanPublisher {
    /**
     * 发布事件
     * @param eventName
     * @param content
     * @param publisher
     * @param publishMaxNumber
     * @param returnEventName 消费后返回的消息名称
     */
    void publish(String eventName, Object content, String publisher, Integer publishMaxNumber, String returnEventName);

    /**
     * 重新发送消息
     * @param logId
     * @return
     */
    Boolean retryPublish(String logId) throws EventComponentException;
}
