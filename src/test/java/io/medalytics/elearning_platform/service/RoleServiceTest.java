package io.medalytics.elearning_platform.service;

import io.medalytics.elearning_platform.exception.DuplicateException;
import io.medalytics.elearning_platform.dao.impl.RoleDaoImpl;
import io.medalytics.elearning_platform.model.Role;
import io.medalytics.elearning_platform.model.request.RoleRequest;
import io.medalytics.elearning_platform.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    private RoleDaoImpl roleDao;

    @BeforeEach
    void setUp() {
        this.roleService = new RoleServiceImpl(roleDao);
    }

    @Test
    void testCreateRole(){
        Role role = new Role();
        role.setId(1L);
        role.setRoleName("ROLE_STUDENT");

        RoleRequest request = new RoleRequest();
        request.setRoleName("ROLE_STUDENT");

        when(roleDao.create(any(Role.class))).thenReturn(role);
        assertThat(roleService.createRole(request)).isEqualTo(Optional.of(role));
    }

    @Test
    void testGetRolesByUserId() {
        Role role = new Role();
        role.setId(1L);
        role.setRoleName("ROLE_STUDENT");

        List<Role> roles = Collections.singletonList(role);

        when(roleDao.findRolesByUserId(any(Long.class))).thenReturn(roles);
        assertThat(roleService.getRoleByUserId(1L)).isEqualTo(Optional.of(roles));
    }

    @Test
    void testGetRoleByName() {
        Role role = new Role();
        role.setId(1L);
        role.setRoleName("ROLE_STUDENT");

        RoleRequest request = new RoleRequest();
        request.setRoleName("ROLE_STUDENT");

        when(roleDao.findRoleByName(anyString())).thenReturn(role);
        assertThat(roleService.getRoleByName(request)).isEqualTo(Optional.of(role));
    }

    @Test
    void testRoleDuplicateException() {
        RoleRequest request = new RoleRequest();
        request.setRoleName("ROLE_STUDENT");

        Role role = new Role();
        role.setId(1L);
        role.setRoleName("ROLE_STUDENT");

        when(roleDao.findRoleByName(any(String.class))).thenThrow(new DuplicateException("Role already exists"));

        Throwable exception = Assertions.assertThrows(DuplicateException.class, () ->{
            roleService.createRole(request);
        });
        assertThat(exception.getMessage()).isEqualTo("Role already exists");
    }

//    @Test
//    void testCreateUserRole() {
//        when(roleDao.addAdminUserRole(anyLong(), anyLong())).thenReturn(anyLong());
//
//        assertThat(roleService.addAdminUserRole(1L, 1L))
//    }
}