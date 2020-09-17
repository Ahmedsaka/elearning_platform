package io.medalytics.elearning_platform.dao;

import io.medalytics.elearning_platform.model.AdminUser;

public interface AdminUserDao extends BaseDao<AdminUser> {
    AdminUser findUserByEmail(String email);
}
