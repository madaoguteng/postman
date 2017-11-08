package io.postman.integration.repository;

import io.postman.common.util.Page;

/**
 * Created by caojun on 2017/11/7.
 */
public interface PublisherRepository {

    int save(PublishLogSnapshot log);

    int updateStatus(String logId, StatusInfoSnapshot status);

    int delete(String logId);
    /**
     * 归档指定天数之前已发送的日志
     * @param day
     * @return
     */
    int archivedBeforeDay(int day);

    PublishLogSnapshot publishLog(String logId);
    /**
     * 分页查询发送失败的日志
     * @param page
     * @return
     */
    Page<PublishLogSnapshot> listOfFail(Page page);
}
