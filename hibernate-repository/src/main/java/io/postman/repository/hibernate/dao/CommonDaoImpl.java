package io.postman.repository.hibernate.dao;

import io.postman.common.exception.RepositoryException;
import io.postman.common.util.StringUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

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
    public Serializable save(Object entity) {
        return getSession().save(entity);
    }

    @Override
    public void saveOrUpdate(Object entity) {
        getSession().saveOrUpdate(entity);
    }

    @Override
    public void update(Object entity) {
        getSession().update(entity);
    }

    @Override
    public void merge(Object entity) {
        getSession().merge(entity);
    }

    @Override
    public void delete(Serializable id) {
        T entity = get(id);
        if (entity != null)
            getSession().delete(entity);
    }

    @Override
    public T get(Serializable id) {
        Class<T> entityClass = (Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        System.out.println("T class name is "+entityClass.toString());
        return getSession().get(entityClass, id);
    }


    @Override
    public int executeHQL(String hql) {
        if (StringUtil.isEmptyOrNull(hql))
            throw new RepositoryException("error on executeHQL, hql is empty");
        return getSession().createQuery(hql).executeUpdate();
    }

    @Override
    public List<T> queryHQL(String hql) {
        Class<T> entityClass = (Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return getSession().createNamedQuery(hql, entityClass).list();
    }
}
