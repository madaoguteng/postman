package io.postman.repository.hibernate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by caojun on 2017/11/9.
 */
@Entity
@Table(name = "PUBLISH_LOG")
public class PublishLogPO implements java.io.Serializable {

    private static long serialVersionUID = 4910225916550731446L;

    private String logId;
    /**
     * 事件名称
     */
    private String eventName;
    /**
     * 首次发布时间
     */
    private Date publishTime;
    /**
     * 发布最大次数限制
     */
    private Integer publishMaxNumber;
    /**
     * 发布者(事件归属业务标识)
     */
    private String publisher;
    /**
     * 发布状态
     */
    private String status;
    private Date updateTime;
    /**
     * 发布次数
     */
    private Integer publishNumber;
    /**
     * 错误消息
     */
    private String errorMsg;
    /**
     * 事件内容
     */
    private String eventContent;

    public PublishLogPO() {
    }

    public PublishLogPO(String logId, String eventName, Date publishTime, Integer publishMaxNumber,
                        String publisher, String status, Date updateTime, Integer publishNumber,
                        String errorMsg, String eventContent) {
        this.logId = logId;
        this.eventName = eventName;
        this.publishTime = publishTime;
        this.publishMaxNumber = publishMaxNumber;
        this.publisher = publisher;
        this.status = status;
        this.updateTime = updateTime;
        this.publishNumber = publishNumber;
        this.errorMsg = errorMsg;
        this.eventContent = eventContent;
    }

    @Id
    @Column(name = "id", unique = true, nullable = false)
    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }
    @Column(name = "msg_name", length = 100, nullable = false)
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    @Column(name = "PUBLISH_TIME", nullable = false)
    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }
    @Column(name = "RETRY_LIMIT")
    public Integer getPublishMaxNumber() {
        return publishMaxNumber;
    }

    public void setPublishMaxNumber(Integer publishMaxNumber) {
        this.publishMaxNumber = publishMaxNumber;
    }
    @Column(name = "MSG_PUBLISHER", length = 100)
    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getPublishNumber() {
        return publishNumber;
    }

    public void setPublishNumber(Integer publishNumber) {
        this.publishNumber = publishNumber;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    @Column(name = "MSG_BODY1", length = 100)
    public String getEventContent() {
        return eventContent;
    }

    public void setEventContent(String eventContent) {
        this.eventContent = eventContent;
    }
}
