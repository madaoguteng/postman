package io.postman.repository.hibernate;

import io.postman.integration.Publisher;
import io.postman.integration.domain.model.publisher.PublishLog;
import io.postman.integration.domain.model.publisher.PublishStatus;
import io.postman.integration.repository.PublishLogSnapshot;
import io.postman.integration.repository.PublisherRepository;
import io.postman.integration.repository.StatusInfoSnapshot;
import io.postman.integration.repository.SummarySnapshot;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by caojun on 2017/11/11.
 * 必须将PublishLogDaoImpl类标注@Transactional注解，否则测试方法会找不到事物
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-hibernate.xml"})
/*@Transactional
@TransactionConfiguration(defaultRollback = true)*/
public class HibernateRepositoryTest {
    @Autowired
    private PublisherRepository repository;

    @Test
    public void saveTest(){
        String eventName = "repository.event.test";
        String publisher = "jtest";
        Object eventContent = "this is a junit test save";
        PublishLogSnapshot snapshot = new PublishLogSnapshot(UUID.randomUUID().toString(),
                new SummarySnapshot(eventName, new Date(), 3, publisher),
                new StatusInfoSnapshot(PublishStatus.PUBLISHING,new Date(), 0, "no error test"),
                eventContent);
        String id = (String)repository.save(snapshot);

        PublishLogSnapshot pSnapshot =  repository.publishLog(id);
        assertNotNull(pSnapshot);
        System.out.println("save id ="+id);
    }

    @Test
    public void publishLogTest(){
        String id = "95747984-319e-4903-8601-f8915358e6a9";
        PublishLogSnapshot snapshot = repository.publishLog(id);
        assertNotNull(snapshot);
        //System.out.println(snapshot);
    }

    @Test
    public void updateStatusTest(){
        String logId = "95747984-319e-4903-8601-f8915358e6a9";
        String errorMsg = "test error";
        StatusInfoSnapshot status = new StatusInfoSnapshot(PublishStatus.FAIL, new Date(), 1, errorMsg);
        repository.updateStatus(logId, status);
        PublishLogSnapshot snapshot = repository.publishLog(logId);
        assertNotNull(snapshot.status());
        assertEquals(PublishStatus.FAIL, snapshot.status().status());
        assertEquals(1, snapshot.status().publishNumber().intValue());
        assertEquals(errorMsg, snapshot.status().errorMsg());
        assertNotNull(snapshot.status().updateTime());
    }

    @Test
    public void listOfFailTest(){
        List<PublishLogSnapshot> logs = repository.listOfFail(10, 1);
        assertNotNull(logs);
        assertTrue(logs.size()>0);
        System.out.println(" logs size = "+logs.size());
    }

    @Test
    public void archivedBeforeDayTest(){
        repository.archivedBeforeDay(0);
        assertTrue(true);
    }

    @Test
    public void deleteTest(){
        String logId = "95747984-319e-4903-8601-f8915358e6a9";
        repository.delete(logId);
        assertNull(repository.publishLog(logId));
    }
}
