package io.medalytics.elearning_platform.dao.impl;

import io.medalytics.elearning_platform.dao.AbstractBaseDao;
import io.medalytics.elearning_platform.dao.AdminUserDao;
import io.medalytics.elearning_platform.model.AdminUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.List;
import javax.sql.DataSource;
import java.util.Map;

@Repository
public class AdminUserDaoImpl extends AbstractBaseDao<AdminUser> implements AdminUserDao {
    private SimpleJdbcCall findUserByEmail;

    @Autowired
    @Override
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);

        this.create = new SimpleJdbcCall(jdbcTemplate).withProcedureName("sp_create_user").withReturnValue();
        this.find = new SimpleJdbcCall(jdbcTemplate).withProcedureName("sp_find_user_by_id")
                .returningResultSet(SINGLE_RESULT, BeanPropertyRowMapper.newInstance(AdminUser.class));
        this.findUserByEmail = new SimpleJdbcCall(jdbcTemplate).withProcedureName("sp_find_user_by_email")
                .returningResultSet(SINGLE_RESULT, BeanPropertyRowMapper.newInstance(AdminUser.class));
    }

    public AdminUser findUserByEmail(String email) throws DataAccessException {
        SqlParameterSource in = new MapSqlParameterSource().addValue("email", email);
        Map<String, Object> m = findUserByEmail.execute(in);
        List<AdminUser> result = (List<AdminUser>) m.get(SINGLE_RESULT);
        return result.isEmpty() ? null : result.get(0);
    }
}
