package io.postman.repository.hibernate.dao;

import java.io.Serializable;

/**
 * Created by caojun on 2017/11/9.
 */
public interface CommonDao {
    Serializable save(Serializable entity);
    void update(Serializable entity);
    void delete(Serializable id);
    Serializable get(Serializable id);
}
