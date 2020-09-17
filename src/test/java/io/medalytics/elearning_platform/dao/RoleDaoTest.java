package io.medalytics.elearning_platform.dao;

import io.medalytics.elearning_platform.DataSourceConfig;
import io.medalytics.elearning_platform.dao.impl.AdminUserDaoImpl;
import io.medalytics.elearning_platform.dao.impl.RoleDaoImpl;
import io.medalytics.elearning_platform.model.AdminUser;
import io.medalytics.elearning_platform.model.Role;
import io.medalytics.elearning_platform.util.DateFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.text.ParseException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = {RoleDaoImpl.class, DataSourceConfig.class, AdminUserDaoImpl.class})
@TestPropertySource("classpath:application.properties")
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RoleDaoTest {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private AdminUserDao adminUserDao;

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "user_role", "role", "admin_user");
    }

    @Test
    void testCreateRole() {
        Role role = Role.builder()
                .roleName("ROLE_ADMIN")
                .build();

        assertThat(roleDao.create(role)).isEqualTo(role);
    }

    @Test
    void testFindRoleByUserId() throws ParseException {
        Role role = roleDao.create(
                Role.builder()
                        .roleName("ROLE_ADMIN")
                        .build()
        );

        AdminUser adminUser = adminUserDao.create(
                            AdminUser.builder()
                                    .firstName("Ahmed")
                                    .lastName("Saka")
                                    .email("test@gmail.com")
                                    .password("Password12")
                                    .dateOfBirth(DateFormatter.formatDate("09/08/1991"))
                                    .build()
                    );

        roleDao.addAdminUserRole(role.getId(), adminUser.getId());
        List<Role> roles = roleDao.findRolesByUserId(adminUser.getId());
        assertThat(roles.get(0).getRoleName()).isEqualTo(role.getRoleName());
//        assertThat(roles.get(0).getId()).isEqualTo(Mockito.anyLong());

    }

    @Test
    void testFindRoleByName() {
        Role role = roleDao.create(
                    Role.builder()
                            .roleName("ROLE_ADMIN")
                            .build()
        );
        Role result = roleDao.findRoleByName("ROLE_ADMIN");
        assertThat(result.getRoleName()).isEqualTo(role.getRoleName());
//        assertThat(result.getId()).isEqualTo(Mockito.anyLong());
    }

    @Test
    void testAddAdminUserRole() throws ParseException{
        Role role = roleDao.create(
                Role.builder()
                        .roleName("ROLE_ADMIN")
                        .build()
        );

        AdminUser adminUser = adminUserDao.create(
                AdminUser.builder()
                        .firstName("Ahmed")
                        .lastName("Saka")
                        .email("test2@gmail.com")
                        .password("Password12")
                        .dateOfBirth(DateFormatter.formatDate("09/08/1991"))
                        .build()
        );
        assertThat(roleDao.addAdminUserRole(role.getId(), adminUser.getId())).isEqualTo(Mockito.anyLong());
    }
}