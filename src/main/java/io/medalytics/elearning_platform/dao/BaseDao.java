package io.medalytics.elearning_platform.dao;

import java.util.List;

public interface BaseDao<T> {
    T create(T model);
    T find(long id);
    List<T> findAll();
    boolean update(T model);
    T delete(long id);
}
