package com.module3.repository;

import java.util.List;

public interface Repository<T> {
    List<T> findAll(Class<T> entityClass);
    T findId(Class<T> entityClass, Object... keys);
    T add(T entity);
    T edit(T entity);
    boolean remove(Class<T> entityClass, Object... keys);
    List<T> findAllByPagination(Class<T> entityClass,Integer pageNumber);
    List<T> findByIndexes(Class<T> entityClass, String any);
    List<T> findByIndexesPagination(Class<T> entityClass, String any,Integer pageNumber);

    T authenticator(Class<T> entityClass, Object... keys);
}
