package io.medalytics.elearning_platform.dao.impl;

import io.medalytics.elearning_platform.dao.AbstractBaseDao;
import io.medalytics.elearning_platform.dao.RoleDao;
import io.medalytics.elearning_platform.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class RoleDaoImpl extends AbstractBaseDao<Role> implements RoleDao {
    private SimpleJdbcCall findRolesByUserId, findRolesByName, createAdminUserRole;

    @Autowired
    @Override
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);

        this.create = new SimpleJdbcCall(jdbcTemplate).withProcedureName("sp_create_role").withReturnValue();
        this.createAdminUserRole = new SimpleJdbcCall(jdbcTemplate).withProcedureName("sp_create_admin_user_role").withReturnValue();
        this.findRolesByUserId = new SimpleJdbcCall(jdbcTemplate).withProcedureName("sp_find_role_by_user_id")
                .returningResultSet(MULTIPLE_RESULT, BeanPropertyRowMapper.newInstance(Role.class));
        this.findRolesByName = new SimpleJdbcCall(jdbcTemplate).withProcedureName("sp_find_role_by_name")
                .returningResultSet(SINGLE_RESULT, BeanPropertyRowMapper.newInstance(Role.class));
    }

    @Override
    public List<Role> findRolesByUserId(Long adminUserId) throws DataAccessException {
        SqlParameterSource in  = new MapSqlParameterSource().addValue("admin_user_id", adminUserId);
        Map<String, Object> m = findRolesByUserId.execute(in);
        List<Role> result = (List<Role>) m.get(MULTIPLE_RESULT);
        return result;
    }


    @Override
    public Role findRoleByName(String name) throws DataAccessException {
        SqlParameterSource in  = new MapSqlParameterSource().addValue("name", name);
        Map<String, Object> m = findRolesByName.execute(in);
        List<Role> result = (List<Role>) m.get(SINGLE_RESULT);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public Long addAdminUserRole(long role_id, long user_id) throws DataAccessException {
        SqlParameterSource in =  new MapSqlParameterSource()
                .addValue("role_id", role_id)
                .addValue("admin_user_id", user_id);
        Map<String, Object> m = createAdminUserRole.execute(in);
        return (long)m.get("id");
    }
}
