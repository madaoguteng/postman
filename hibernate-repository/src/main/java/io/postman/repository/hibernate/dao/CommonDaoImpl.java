package io.postman.repository.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

/**
 * Created by caojun on 2017/11/9.
 */
@Repository
public class CommonDaoImpl<T> implements CommonDao {
    @Autowired
    private SessionFactory sessionFactory;

    protected Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public Serializable save(Serializable entity) {
        return getSession().save(entity);
    }

    @Override
    public void update(Serializable entity) {
        getSession().update(entity);
    }

    @Override
    public void delete(Serializable id) {
        Serializable entity = get(id);
        if (entity != null)
            getSession().delete(entity);
    }

    @Override
    public Serializable get(Serializable id) {
        Class<T> entityClass = (Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return getSession().get(entityClass.getClass(), id);
    }
}
