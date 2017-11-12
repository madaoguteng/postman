package io.postman.repository.hibernate.dao;

import com.alibaba.fastjson.JSON;
import io.postman.common.exception.RepositoryException;
import io.postman.common.util.StringUtil;
import io.postman.integration.domain.model.publisher.PublishStatus;
import io.postman.integration.repository.PublishLogSnapshot;
import io.postman.integration.repository.PublisherRepository;
import io.postman.integration.repository.StatusInfoSnapshot;
import io.postman.integration.repository.SummarySnapshot;
import io.postman.repository.hibernate.model.PublishLogPO;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by caojun on 2017/11/9.
 */
@Repository
public class PublishLogDaoImpl extends CommonDaoImpl<PublishLogPO> implements PublisherRepository {
    @Transactional
    @Override
    public Serializable save(PublishLogSnapshot log) {
        return super.save(snapshotToPO(log));
    }

    @Override
    public void updateStatus(String logId, StatusInfoSnapshot status) {
        if (StringUtil.isEmptyOrNull(logId) || status == null)
            throw new RepositoryException("error no updateStatus, params is null");
        String hql = "update PublishLogPO p set p.status=:status, "
                + "p.updateTime=:updateTime, p.publishNumber =:publishNumber"
                +(StringUtil.notEmpty(status.errorMsg()) ? ",p.errorMsg = :errorMsg" : "")
                +" where p.logId=:logId";
        Query query= getSession().createQuery(hql)
                .setParameter("status", status.status().toString())
                .setParameter("updateTime", status.updateTime(), TemporalType.DATE)
                .setParameter("publishNumber", status.publishNumber())
                .setParameter("logId", logId);
        if (StringUtil.notEmpty(status.errorMsg()))
            query.setParameter("errorMsg",status.errorMsg());

        query.executeUpdate();
    }

    @Override
    public void delete(String logId) {
        if (StringUtil.isEmptyOrNull(logId))
            throw new RepositoryException("error no delete, logId is null");
        super.delete(logId);
    }

    @Override
    public void archivedBeforeDay(int day) {
        long time = new Date().getTime() - day * 24*3600*1000;

        getSession().createQuery("delete from PublishLogPO where status=:status and publishTime < :publishTime")
                .setParameter("status", PublishStatus.PUBLISHED.toString())
                .setParameter("publishTime", new Date(time), TemporalType.DATE)
                .executeUpdate();
    }

    @Override
    public PublishLogSnapshot publishLog(String logId) {
        return poToSnapshot(super.get(logId));
    }

    @Override
    public List<PublishLogSnapshot> listOfFail(Integer pageSize, Integer pageNo) {
        return getSession().createQuery("from PublishLogPO where status=:status order by publishTime desc")
                .setParameter("status", PublishStatus.FAIL.toString())
                .setFirstResult((pageNo - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    private PublishLogPO snapshotToPO(PublishLogSnapshot snapshot){
        if (snapshot == null)
            throw new RepositoryException("transaction to PO object error, snapshot is null");
        PublishLogPO po = new PublishLogPO();
        po.setLogId(snapshot.logId());
        if (snapshot.eventContent() != null)
            po.setEventContent(JSON.toJSONString(snapshot.eventContent()));
        if (snapshot.summary()!=null){
            SummarySnapshot summary = snapshot.summary();
            po.setEventName(summary.eventName());
            po.setPublisher(summary.publisher());
            po.setPublishMaxNumber(summary.publishMaxNumber());
            po.setPublishTime(summary.publishTime());
        }
        if (snapshot.status() != null){
            StatusInfoSnapshot status = snapshot.status();
            po.setPublishNumber(status.publishNumber());
            po.setErrorMsg(status.errorMsg());
            po.setStatus(status.status().toString());
            po.setUpdateTime(status.updateTime());
        }
        return po;
    }

    private PublishLogSnapshot poToSnapshot(PublishLogPO po){
        if (po == null || StringUtil.isEmptyOrNull(po.getLogId()))
            throw new RepositoryException("error on poToSnapshot, po or logId is null");
        SummarySnapshot summary = new SummarySnapshot(po.getEventName(), po.getPublishTime(), po.getPublishMaxNumber(), po.getPublisher());
        StatusInfoSnapshot status = new StatusInfoSnapshot(PublishStatus.valueOf(po.getStatus()),po.getUpdateTime(), po.getPublishNumber(), po.getErrorMsg());
        return new PublishLogSnapshot(po.getLogId(), summary, status, po.getEventContent());
    }
}
