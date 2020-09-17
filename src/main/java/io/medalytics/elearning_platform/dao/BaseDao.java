package io.medalytics.elearning_platform.dao;

public interface BaseDao<T> {
    T create(T model);
    T find(long id);
    boolean update(T model);
    T delete(long id);
}
