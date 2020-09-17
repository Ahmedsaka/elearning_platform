package io.medalytics.elearning_platform.service;

import io.medalytics.elearning_platform.model.Role;
import io.medalytics.elearning_platform.model.request.RoleRequest;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    Optional<Role> createRole(RoleRequest request);
    Optional<Role> getRoleByName(RoleRequest request);
    Optional<List<Role>> getRoleByUserId(long userId);
    Optional<Long> createAdminUserRole(Long role_id, Long user_id);
}
