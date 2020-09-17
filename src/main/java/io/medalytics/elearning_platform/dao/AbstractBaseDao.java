package io.medalytics.elearning_platform.dao;

import io.medalytics.elearning_platform.model.BaseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

public abstract class AbstractBaseDao<T extends BaseModel> implements BaseDao<T> {
    protected JdbcTemplate jdbcTemplate;
    protected SimpleJdbcCall create, find,update, findAll;
    protected final String SINGLE_RESULT = "object";
    protected final String MULTIPLE_RESULT = "list";
    protected static final String RESULT_COUNT = "count";
    public abstract void setDataSource(DataSource dataSource);


    @Override
    public T create(T model) throws DataAccessException {
        SqlParameterSource in = new BeanPropertySqlParameterSource(model);
        Map<String, Object> m = create.execute(in);
        long id = (long) m.get("id");
        model.setId(id);
        return model;
    }

    @Override
    public T find(long id) throws DataAccessException {
        SqlParameterSource in = new MapSqlParameterSource().addValue("id", id);
        Map<String, Object> m = find.execute(in);
        List<T> list = (List<T>) m.get(SINGLE_RESULT);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public boolean update(T model) throws DataAccessException {
        return false;
    }

    @Override
    public T delete(long id) throws DataAccessException {
        return null;
    }
}
