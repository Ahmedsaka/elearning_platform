package io.medalytics.elearning_platform.service;

import io.medalytics.elearning_platform.exception.DuplicateException;
import io.medalytics.elearning_platform.dao.impl.AdminUserDaoImpl;
import io.medalytics.elearning_platform.model.AdminUser;
import io.medalytics.elearning_platform.model.request.AdminUserRequest;
import io.medalytics.elearning_platform.service.impl.AdminUserServiceImpl;
import io.medalytics.elearning_platform.service.impl.RoleServiceImpl;
import io.medalytics.elearning_platform.util.DateFormatter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class AdminAdminUserServiceTest {

    @InjectMocks
    private AdminUserServiceImpl adminUserService;

    @Mock
    private AdminUserDaoImpl adminUserDao;

    @Mock
    private RoleServiceImpl roleService;

    @BeforeEach
    void setUp() {
        this.adminUserService = new AdminUserServiceImpl(adminUserDao, roleService);
    }

    @Test
    void testCreateUser() throws ParseException {
        AdminUserRequest request = AdminUserRequest.builder()
                .firstName("Ahmed")
                .lastName("Saka")
                .email("ahmedsaka91@gmail.com")
                .password("Password12")
                .roleName("ROLE_ADMIN")
                .dateOfBirth("09/08/1991")
                .build();

        AdminUser adminUser = AdminUser.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .dateOfBirth(DateFormatter.formatDate(request.getDateOfBirth()))
                .password(request.getPassword())
                .build();

        when(adminUserDao.create(any(AdminUser.class))).thenReturn(adminUser);

        assertThat(adminUserService.createAdminUser(request)).isEqualTo(Optional.of(adminUser));
    }

    @Test
    void testFindUserByEmail() throws ParseException{
        AdminUserRequest request = AdminUserRequest.builder()
                .firstName("Ahmed")
                .lastName("Saka")
                .email("ahmedsaka91@gmail.com")
                .password("Password12")
                .roleName("ROLE_ADMIN")
                .dateOfBirth("09/08/1991")
                .build();

        AdminUser adminUser = AdminUser.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .dateOfBirth(DateFormatter.formatDate(request.getDateOfBirth()))
                .password(request.getPassword())
                .build();

        when(adminUserDao.findUserByEmail(anyString())).thenReturn(adminUser);
        assertThat(adminUserService.findUserByEmail(request.getEmail())).isEqualTo(Optional.of(adminUser));
    }

    @Test
    void testCreateUserWithDuplicateException(){
        AdminUserRequest request = AdminUserRequest.builder()
                .firstName("Ahmed")
                .lastName("Saka")
                .email("ahmedsaka91@gmail.com")
                .password("Password12")
                .roleName("ROLE_ADMIN")
                .dateOfBirth("09/08/1991")
                .build();

        when(adminUserDao.create(any(AdminUser.class))).thenThrow(new DuplicateException(String.format("AdminUser %s already exists", request.getEmail())));

        Throwable exception = Assertions.assertThrows(DuplicateException.class, () ->{
            adminUserService.createAdminUser(request);
        });
        assertThat(exception.getMessage()).isEqualTo(String.format("AdminUser %s already exists", request.getEmail()));
    }
}