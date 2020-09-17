package io.medalytics.elearning_platform.service;

import io.medalytics.elearning_platform.model.AdminUser;
import io.medalytics.elearning_platform.model.request.AdminUserRequest;

import java.text.ParseException;
import java.util.Optional;

public interface AdminUserService {
    Optional<AdminUser> createAdminUser(AdminUserRequest request) throws ParseException;
    Optional<AdminUser> findUserByEmail(String email);
}
