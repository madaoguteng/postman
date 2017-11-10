package io.postman.integration.repository;

import io.postman.common.exception.EventComponentException;
import io.postman.integration.domain.model.publisher.PublishLog;

/**
 * Created by caojun on 2017/11/7.
 * Domain model of publish event log, it is an aggregates root.
 */
public class PublishLogSnapshot<T> {
    private String logId;
    private SummarySnapshot summary;
    private StatusInfoSnapshot status;
    /**
     * 事件内容
     */
    private T eventContent;

    public PublishLogSnapshot(PublishLog<T> publishLog){
        if (publishLog == null)
            throw new EventComponentException("error when create PublishLogSnapshot, publishLog is null");
        this.logId = publishLog.logId().id();
        if(publishLog.summary() != null)
            this.summary = new SummarySnapshot(publishLog.summary());
        if (publishLog.status() != null)
            this.status = new StatusInfoSnapshot(publishLog.status());
        this.eventContent = publishLog.eventContent();
    }

    public PublishLogSnapshot(String logId, SummarySnapshot summary, StatusInfoSnapshot status, T eventContent){
        this.logId = logId;
        this.summary = summary;
        this.status = status;
        this.eventContent = eventContent;
    }

    public PublishLog transTo(){
        return new PublishLog(this.logId,
                this.summary!=null ? this.summary.transTo():null,
                this.status!=null ? this.status.transTo():null);
    }

    public String logId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public SummarySnapshot summary() {
        return summary;
    }

    public void setSummary(SummarySnapshot summary) {
        this.summary = summary;
    }

    public StatusInfoSnapshot status() {
        return status;
    }

    public void setStatus(StatusInfoSnapshot status) {
        this.status = status;
    }

    public T eventContent() {
        return eventContent;
    }

    public void eventContent(T eventContent) {
        this.eventContent = eventContent;
    }
}
