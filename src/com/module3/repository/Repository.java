package com.module3.repository;

import java.util.List;

public interface Repository<T> {
    List<T> fìndAllByPagination(Class<T> entityClass,Integer pageNumber);
    List<T> findAll(Class<T> entityClass);
    T findId(Class<T> entityClass, Object... keys);
    T add(T entity);
    T edit(T entity);
    boolean remove(Class<T> entityClass, Object... keys);
}
