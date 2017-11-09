package io.postman.repository.hibernate;

import io.postman.common.util.Page;
import io.postman.integration.repository.PublishLogSnapshot;
import io.postman.integration.repository.PublisherRepository;
import io.postman.integration.repository.StatusInfoSnapshot;

/**
 * Created by caojun on 2017/11/9.
 */
public class HibernatePublishLogRepository implements PublisherRepository {
    @Override
    public int save(PublishLogSnapshot log) {
        return 0;
    }

    @Override
    public int updateStatus(String logId, StatusInfoSnapshot status) {
        return 0;
    }

    @Override
    public int delete(String logId) {
        return 0;
    }

    @Override
    public int archivedBeforeDay(int day) {
        return 0;
    }

    @Override
    public PublishLogSnapshot publishLog(String logId) {
        return null;
    }

    @Override
    public Page<PublishLogSnapshot> listOfFail(Page page) {
        return null;
    }
}
