package io.postman.repository.hibernate.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caojun on 2017/11/9.
 */
public interface CommonDao<T> {
    Serializable save(T entity);
    void saveOrUpdate(T entity);
    void update(T entity);
    void merge(T entity);
    void delete(Serializable id);
    int executeHQL(String hql);
    T get(Serializable id);
    List<T> queryHQL(String hql);
}
