package com.module3.repository;

import java.sql.Connection;
import java.sql.Savepoint;
import java.util.List;

public interface Repository<T> {
    List<T> findAll(Class<T> entityClass);
    T findId(Class<T> entityClass, Object... keys);
    T add(T entity);
    T edit(T entity);
    boolean remove(Class<T> entityClass, Object... keys);
    //Thêm
    T addIgnoreId(T entity);
    List<T> findAllByPagination(Class<T> entityClass,Integer pageNumber);
    List<T> findRelativeByIndexes(Class<T> entityClass, Object... anys);
    List<T> findAbsoluteByIndexes(Class<T> entityClass, Object... any);
    List<T> findByIndexesPagination(Class<T> entityClass, String any,Integer pageNumber);
    List<T> findByMark(Class<T> entityClass, Object... targets);

    T authenticator(Class<T> entityClass, Object... keys);
}
