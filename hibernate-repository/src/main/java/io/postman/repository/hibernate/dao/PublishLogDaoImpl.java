package io.postman.repository.hibernate.dao;

import com.alibaba.fastjson.JSON;
import io.postman.common.exception.RepositoryException;
import io.postman.common.util.Page;
import io.postman.common.util.StringUtil;
import io.postman.integration.repository.PublishLogSnapshot;
import io.postman.integration.repository.PublisherRepository;
import io.postman.integration.repository.StatusInfoSnapshot;
import io.postman.integration.repository.SummarySnapshot;
import io.postman.repository.hibernate.model.PublishLogPO;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

/**
 * Created by caojun on 2017/11/9.
 */
@Repository
public class PublishLogDaoImpl extends CommonDaoImpl<PublishLogPO> implements PublisherRepository {
    @Override
    public Serializable save(PublishLogSnapshot log) {
        return super.save(snapshotToPO(log));
    }

    @Override
    public void updateStatus(String logId, StatusInfoSnapshot status) {
        if (StringUtil.isEmptyOrNull(logId) || status == null)
            throw new RepositoryException("error no updateStatus, params is null");
        /*String hql = "update PublishLogPO p set p.status='"+status.status().toString()
                + "', p.="*/
        CriteriaBuilder crb=getSession().getCriteriaBuilder();
        CriteriaQuery<PublishLogPO> crq=crb.createQuery(PublishLogPO.class);
        Root<PublishLogPO> root=crq.from(PublishLogPO.class);
        crq.select(root);
        //crq.where(crb.like(root.get("address"),address));
        //return currentSession().createQuery(crq).getResultList();
    }

    @Override
    public void delete(String logId) {
        //return 0;
    }

    @Override
    public void archivedBeforeDay(int day) {
        //return 0;
    }

    @Override
    public PublishLogSnapshot publishLog(String logId) {
        return null;
    }

    @Override
    public Page<PublishLogSnapshot> listOfFail(Page page) {
        return null;
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

}
