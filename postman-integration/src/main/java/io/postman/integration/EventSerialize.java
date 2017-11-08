package io.postman.integration;

import io.postman.integration.repository.PublishLogSnapshot;

/**
 * Created by caojun on 2017/11/8.
 */
public interface EventSerialize {
    String serialize(PublishLogSnapshot publishLog);
}
