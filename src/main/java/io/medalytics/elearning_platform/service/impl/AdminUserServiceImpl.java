package io.medalytics.elearning_platform.service.impl;

import io.medalytics.elearning_platform.exception.DuplicateException;
import io.medalytics.elearning_platform.dao.AdminUserDao;
import io.medalytics.elearning_platform.exception.NotFoundException;
import io.medalytics.elearning_platform.model.AdminUser;
import io.medalytics.elearning_platform.model.Role;
import io.medalytics.elearning_platform.model.request.AdminUserRequest;
import io.medalytics.elearning_platform.model.request.RoleRequest;
import io.medalytics.elearning_platform.service.AdminUserService;
import io.medalytics.elearning_platform.service.RoleService;
import io.medalytics.elearning_platform.util.DateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Optional;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    private AdminUserDao adminUserDao;
    private RoleService roleService;

    @Autowired
    public AdminUserServiceImpl(AdminUserDao adminUserDao, RoleService roleService) {
        this.roleService = roleService;
        this.adminUserDao = adminUserDao;
    }

    @Override
    public Optional<AdminUser> createAdminUser(AdminUserRequest request) throws ParseException {
        if (adminUserDao.findUserByEmail(request.getEmail()) != null){
            throw new DuplicateException(String.format("AdminUser %s already exists", request.getEmail()));
        }

        Role role = null;
        Optional<Role> optionalRole = roleService.getRoleByName(RoleRequest.builder().roleName(request.getRoleName()).build());
        if (optionalRole.isPresent()){
            role = optionalRole.get();
        }else throw new NotFoundException("Role not found");

        AdminUser adminUser = adminUserDao.create(
                AdminUser.builder()
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .email(request.getEmail())
                        .password(request.getPassword())
                        .dateOfBirth(DateFormatter.formatDate(request.getDateOfBirth()))
                        .build());
        long role_id = role.getId();
        roleService.createAdminUserRole(role_id, adminUser.getId());
        return Optional.of(adminUser);
    }

    @Override
    public Optional<AdminUser> findUserByEmail(String email) {
        return Optional.ofNullable(adminUserDao.findUserByEmail(email));
    }
}
