package io.postman.integration.domain.model.publisher;

import java.util.Date;

/**
 * Created by caojun on 2017/11/7.
 * Domain model of publish event log, it is an aggregates root.
 */
public class PublishLog {
    private PublishLogId logId;
    private Summary summary;
    private StatusInfo status;
    /**
     * 事件内容
     */
    private Object eventContent;

    public PublishLog(String eventName, Date publishTime, Integer publishMaxNumber, String publisher,
                      Object eventContent) {
        this.logId = new PublishLogId();
        this.summary = new Summary(eventName, publishTime, publishMaxNumber, publisher);
        this.eventContent = eventContent;
        this.status = StatusInfo.initStatusInfo();
    }

    public PublishLog(String eventName, Date publishTime, Integer publishMaxNumber, String publisher) {
        this.logId = new PublishLogId();
        this.summary = new Summary(eventName, publishTime, publishMaxNumber, publisher);
        this.status = StatusInfo.initStatusInfo();
    }

    public PublishLog(String id, Summary summary, StatusInfo status){
        this.logId = new PublishLogId(id);
        this.summary = summary;
        this.status = status;
    }

    public void failStatus(String errorMsg){
        Integer publishNumber = (this.status.publishNumber() != null) ? this.status.publishNumber()+1 : 1;
        errorMsg = "第"+publishNumber+"次发送失败："+errorMsg;
        this.status = new StatusInfo(PublishStatus.FAIL, null,
                publishNumber, errorMsg);
    }

    public void publishedStatus(){
        this.status = new StatusInfo(PublishStatus.PUBLISHED, null,
                (this.status.publishNumber() != null) ? this.status.publishNumber()+1 : 1, null);
    }

    public PublishLogId logId() {
        return logId;
    }

    public Summary summary() {
        return summary;
    }

    public StatusInfo status() {
        return status;
    }

    public Object eventContent() {
        return eventContent;
    }

    public void setEventContent(Object eventContent) {
        this.eventContent = eventContent;
    }
}
