package io.postman.integration.domain.application;

import io.postman.common.exception.EventComponentException;
import io.postman.common.util.LoggerUtil;
import io.postman.common.util.Page;
import io.postman.common.util.StringUtil;
import io.postman.integration.EventSerialize;
import io.postman.integration.PostmanPublisher;
import io.postman.integration.Publisher;
import io.postman.integration.domain.model.publisher.PublishLog;
import io.postman.integration.domain.model.publisher.PublishStatus;
import io.postman.integration.repository.PublishLogSnapshot;
import io.postman.integration.repository.PublisherRepository;
import io.postman.integration.repository.StatusInfoSnapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caojun on 2017/11/8.
 * a implements of interface PostmanPublisher
 */
@Service("postmanPublisher")
public class PostmanPublisherImpl implements PostmanPublisher {
    private Logger logger = LoggerFactory.getLogger(PostmanPublisherImpl.class);
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private Publisher publisher;
    @Autowired
    private EventSerialize eventSerialize;

    /**
     * 发布事件
     * 1、持久化事件发布日志，2、发布消息到MQ
     * @param eventName
     * @param content
     * @param publisher
     * @param publishMaxNumber
     * @param returnEventName 消费后返回的消息名称
     */
    @Override
    @Transactional
    public void publish(String eventName, Object content, String publisher, Integer publishMaxNumber, String returnEventName) {
        PublishLog log = new PublishLog(eventName, null, publishMaxNumber, publisher);
        //将数据序列化成待发送的消息体
        Serializable eventContext = eventSerialize.serialize(log.logId().id(), publisher, log.summary().publishTime(),
                content, returnEventName);
        log.setEventContent(eventContext);
        PublishLogSnapshot snapshot = new PublishLogSnapshot(log);

        //持久化事件发布日志
        publisherRepository.save(snapshot);
        this.publisher.sendNIO(eventName, null, eventContext,
                (String metadata, Exception exception) -> {
                    if (exception == null)
                        log.publishedStatus();
                    else {
                        log.failStatus(exception.getMessage());
                        LoggerUtil.error(logger, "error when publish event by nio，eventName={}, logId={}, metadata={}, message：{}",
                                eventName, snapshot.logId(), metadata, LoggerUtil.getStackInfo(exception));
                    }
                    publisherRepository.updateStatus(snapshot.logId(), new StatusInfoSnapshot(log.status()));
                }
        );

    }

    @Transactional
    @Override
    public Boolean retryPublish(String logId) throws EventComponentException {
        if (StringUtil.isEmptyOrNull(logId))
            throw new EventComponentException("logId is null");
        PublishLogSnapshot snapshot = publisherRepository.publishLog(logId);
        if (snapshot != null)
            throw new EventComponentException("publishLog not exists");
        PublishLog log = snapshot.transTo();
        sendBIO(log, (String)log.eventContent());
        return PublishStatus.PUBLISHED.equals(log.status().status());
    }

    private void sendBIO(PublishLog log, String eventContent){
        try {
            publisher.sendBIO(log.summary().eventName(), null, eventContent);
            log.publishedStatus();
        } catch (Exception e) {
            log.failStatus(e.getMessage());
            LoggerUtil.error(logger, "error when publish event by bio，eventName={}, logId={}, message：{}",
                    log.summary().eventName(), log.logId().id(), LoggerUtil.getStackInfo(e));
        }
        publisherRepository.updateStatus(log.logId().id(), new StatusInfoSnapshot(log.status()));
    }

    /**
     * 轮训重试发布事件
     */
    public void rotationRetryPublish(){
        List<PublishLogSnapshot> logs = publisherRepository.listOfFail(1, 20);
        if (logs == null || logs.size() == 0) return;
        logs.forEach(snapshot -> {
            sendBIO(snapshot.transTo(), (String)snapshot.eventContent());
        });
    }
}
