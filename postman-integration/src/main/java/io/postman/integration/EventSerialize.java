package io.postman.integration;


import java.util.Date;

/**
 * Created by caojun on 2017/11/8.
 */
public interface EventSerialize {
    String serialize(String logId, String publisher, Date publishTime,
                     Object eventContent, String returnEventName);
}
