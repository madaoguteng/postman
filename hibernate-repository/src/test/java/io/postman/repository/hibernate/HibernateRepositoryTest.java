package io.postman.repository.hibernate;

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
import java.util.UUID;

import static org.junit.Assert.assertNotNull;

/**
 * Created by caojun on 2017/11/11.
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
    }

    @Test
    public void getTest(){
        String id = "00a01425-5826-42f2-9d7e-3b1234a6e44c";
        PublishLogSnapshot snapshot = repository.publishLog(id);
        assertNotNull(snapshot);
        System.out.println(snapshot.toString());
    }
}
