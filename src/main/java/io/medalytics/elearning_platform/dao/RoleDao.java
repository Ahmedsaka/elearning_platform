package io.medalytics.elearning_platform.dao;

import io.medalytics.elearning_platform.model.Role;

import java.util.List;

public interface RoleDao extends BaseDao<Role> {
    List<Role> findRolesByUserId(Long adminUserId);
    Role findRoleByName(String name);
    Long addAdminUserRole(long role_id, long user_id);
}
