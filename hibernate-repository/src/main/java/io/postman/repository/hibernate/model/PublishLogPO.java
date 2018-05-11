package io.postman.repository.hibernate.model;

import io.postman.common.exception.RepositoryException;
import io.postman.common.util.StringUtil;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Clob;
import java.util.Date;

import static io.postman.common.exception.Assert.assertLessThan;

/**
 * Created by caojun on 2017/11/9.
 */
@Data
@Entity
@Table(name = "publish_log")
public class PublishLogPO implements java.io.Serializable {

    private static long serialVersionUID = 4910225916550731446L;
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private String id;
    /**
     * 事件名称
     */
    @Column(name = "topic", length = 100, nullable = false)
    private String eventName;
    /**
     * 首次发布时间
     */
    @Column(name = "publish_time", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date publishTime;
    /**
     * 更新时间
     */
    @Column(name = "update_time")
    @Temporal(TemporalType.DATE)
    private Date updateTime;
    /**
     * 发布者(事件归属业务标识)
     */
    @Column(name = "publisher", length = 100)
    private String publisher;
    /**
     * 发布状态 PUBLISHING|PUBLISHED|FAIL 发布中|已发布|发布失败
     */
    @Column(name = "status")
    private String status;
    /**
     * 发布次数
     */
    @Column(name = "retry_count")
    private Integer publishNumber;
    /**
     * 错误消息
     */
    @Column(name = "error_msg")
    private String errorMsg;
    /**
     * 事件内容
     */
    @Column(name = "content", length = 1000)
    private String eventContent;

    public PublishLogPO() {
    }

    public PublishLogPO(String logId, String eventName, Date publishTime, Integer publishMaxNumber,
                        String publisher, String status, Date updateTime, Integer publishNumber,
                        String errorMsg, String eventContent) {
        this.id = logId;
        this.eventName = eventName;
        this.publishTime = publishTime;
        this.publisher = publisher;
        this.status = status;
        this.updateTime = updateTime;
        this.publishNumber = publishNumber;
        assertLessThan(errorMsg, 1000, "errorMsg is longer than max length 1000 bytes ");
        this.errorMsg = errorMsg;
        assertLessThan(eventContent, 1000, "eventContent is longer than max length 1000 bytes ");
        this.eventContent = eventContent;
    }

    public void setEventContent(String eventContent) {
        assertLessThan(eventContent, 1000, "eventContent is longer max length 4000 bytes ");
        this.eventContent = eventContent;
    }

    public void setErrorMsg(String errorMsg) {
        assertLessThan(errorMsg, 1000, "errorMsg is longer max length 4000 bytes ");
        this.errorMsg = errorMsg;
    }
}
