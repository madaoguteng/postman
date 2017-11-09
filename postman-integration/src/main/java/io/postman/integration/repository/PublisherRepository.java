package io.postman.integration.repository;

import io.postman.common.util.Page;

import java.io.Serializable;

/**
 * Created by caojun on 2017/11/7.
 */
public interface PublisherRepository {

    Serializable save(PublishLogSnapshot log);

    void updateStatus(String logId, StatusInfoSnapshot status);

    void delete(String logId);
    /**
     * 归档指定天数之前已发送的日志
     * @param day
     * @return
     */
    void archivedBeforeDay(int day);

    PublishLogSnapshot publishLog(String logId);
    /**
     * 分页查询发送失败的日志
     * @param page
     * @return
     */
    Page<PublishLogSnapshot> listOfFail(Page page);
}
