package io.postman.integration.domain.application;

import com.alibaba.fastjson.JSON;
import io.postman.common.exception.EventComponentException;
import io.postman.integration.EventSerialize;
import io.postman.integration.repository.PublishLogSnapshot;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by caojun on 2017/11/8.
 */
@Service("eventSerialize")
public class PostmanEventSerialize implements EventSerialize {
    @Override
    public String serialize(PublishLogSnapshot publishLog) {
        if (publishLog == null || publishLog.summary() == null)
            throw new EventComponentException("publishLog or summary is null");
        EventContextDTO event = new EventContextDTO(publishLog.logId(), publishLog.summary().publisher(),
                publishLog.summary().publishTime(), publishLog.eventContent(), publishLog.summary().returnEventName());
        return JSON.toJSONString(event);
    }

    private class EventContextDTO<T> implements java.io.Serializable {

        private static final long serialVersionUID = 1L;
        /**
         * 消息ID
         */
        private String id;
        /**
         * 消息业务ID
         **/
        private String msgPublisher;
        /**
         * 发布时间
         **/
        private Date publishTime;
        /**
         * 消息体（Json字符串）
         **/
        private T msgBody;
        /**
         * 返回消息的名称
         */
        private String callbackTopicName;

        public EventContextDTO(){}

        public EventContextDTO(String id, String msgPublisher, Date publishTime, T msgBody, String callbackTopicName) {
            this.id = id;
            this.msgPublisher = msgPublisher;
            this.publishTime = publishTime;
            this.msgBody = msgBody;
            this.callbackTopicName = callbackTopicName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMsgPublisher() {
            return msgPublisher;
        }

        public void setMsgPublisher(String msgPublisher) {
            this.msgPublisher = msgPublisher;
        }

        public Date getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(Date publishTime) {
            this.publishTime = publishTime;
        }

        public T getMsgBody() {
            return msgBody;
        }

        public void setMsgBody(T msgBody) {
            this.msgBody = msgBody;
        }

        public String getCallbackTopicName() {
            return callbackTopicName;
        }

        public void setCallbackTopicName(String callbackTopicName) {
            this.callbackTopicName = callbackTopicName;
        }
    }
}
