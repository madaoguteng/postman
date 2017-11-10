package io.postman.repository.hibernate.model;

import io.postman.common.exception.RepositoryException;
import io.postman.common.util.StringUtil;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Clob;
import java.util.Date;

/**
 * Created by caojun on 2017/11/9.
 */
@Entity
@Table(name = "PUBLISH_LOG")
public class PublishLogPO implements java.io.Serializable {

    private static long serialVersionUID = 4910225916550731446L;
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private String logId;
    /**
     * 事件名称
     */
    @Column(name = "msg_name", length = 100, nullable = false)
    private String eventName;
    /**
     * 首次发布时间
     */
    @Column(name = "PUBLISH_TIME", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date publishTime;
    /**
     * 发布最大次数限制
     */
    @Column(name = "RETRY_LIMIT")
    private Integer publishMaxNumber;
    /**
     * 发布者(事件归属业务标识)
     */
    @Column(name = "MSG_PUBLISHER", length = 100)
    private String publisher;
    /**
     * 发布状态
     */
    @Column(name = "STATUS")
    private String status;
    @Column(name = "UPDATE_TIME")
    @Temporal(TemporalType.DATE)
    private Date updateTime;
    /**
     * 发布次数
     */
    @Column(name = "RETRY_COUNT")
    private Integer publishNumber;
    /**
     * 错误消息
     */
    @Column(name = "ERROR_MSG")
    private String errorMsg;
    /**
     * 事件内容
     */
    @Column(name = "MSG_BODY1", length = 100)
    @Lob
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
        if (StringUtil.notEmpty(errorMsg) && errorMsg.getBytes().length>4000)
            throw new RepositoryException("errorMsg is longer max length 4000 bytes ");
        this.errorMsg = errorMsg;
        this.eventContent = eventContent;
    }

    public String getLogId() {
        return logId;
    }
    public void setLogId(String logId) {
        this.logId = logId;
    }
    public String getEventName() {
        return eventName;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    public Date getPublishTime() {
        return publishTime;
    }
    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }
    public Integer getPublishMaxNumber() {
        return publishMaxNumber;
    }
    public void setPublishMaxNumber(Integer publishMaxNumber) {
        this.publishMaxNumber = publishMaxNumber;
    }
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
        if (StringUtil.notEmpty(errorMsg) && errorMsg.getBytes().length>4000)
            throw new RepositoryException("errorMsg is longer max length 4000 bytes ");
        this.errorMsg = errorMsg;
    }
    public String getEventContent() {
        return eventContent;
    }
    public void setEventContent(String eventContent) {
        this.eventContent = eventContent;
    }
}
