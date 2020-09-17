package io.medalytics.elearning_platform.service.impl;

import io.medalytics.elearning_platform.exception.DuplicateException;
import io.medalytics.elearning_platform.exception.NotFoundException;
import io.medalytics.elearning_platform.dao.RoleDao;
import io.medalytics.elearning_platform.model.Role;
import io.medalytics.elearning_platform.model.request.RoleRequest;
import io.medalytics.elearning_platform.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public Optional<Role> createRole(RoleRequest request) {
        if (Optional.ofNullable(roleDao.findRoleByName(request.getRoleName())).isPresent()) {
            if (roleDao.findRoleByName(request.getRoleName()).getRoleName().equals(request.getRoleName())){
                throw new DuplicateException("Role already exists");
            }
        }
        return Optional.ofNullable(
                roleDao.create(
                        Role.builder()
                                .roleName(request.getRoleName())
                                .build()
                )
        );
    }

    @Override
    public Optional<Role> getRoleByName(RoleRequest request) {
        return Optional.of(Optional.ofNullable(roleDao.findRoleByName(request.getRoleName())))
                .orElse(null);
    }

    @Override
    public Optional<List<Role>> getRoleByUserId(long userId) {
        return Optional.ofNullable(
                roleDao.findRolesByUserId(userId)
        );
    }

    @Override
    public Optional<Long> createAdminUserRole(Long role_id, Long user_id) {
        return Optional.of(roleDao.addAdminUserRole(role_id, user_id));
    }
}
