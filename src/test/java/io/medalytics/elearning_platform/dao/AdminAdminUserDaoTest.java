package io.medalytics.elearning_platform.dao;

import io.medalytics.elearning_platform.DataSourceConfig;
import io.medalytics.elearning_platform.dao.impl.AdminUserDaoImpl;
import io.medalytics.elearning_platform.model.AdminUser;
import io.medalytics.elearning_platform.util.DateFormatter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {AdminUserDaoImpl.class, DataSourceConfig.class})
@TestPropertySource("classpath:application.properties")
class AdminAdminUserDaoTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminUserDao adminUserDao;

    @BeforeAll
    void setUp() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "admin_user");
    }

    @Test
    void testCreateAdminUser() throws ParseException {
        AdminUser adminUser = AdminUser.builder()
                .firstName("Ahmed")
                .lastName("Saka")
                .email("ahmedsaka91@gmail.com")
                .password("Password12")
                .dateOfBirth(DateFormatter.formatDate("09/08/1991"))
                .build();

        Assertions.assertThat(adminUserDao.create(adminUser)).isNotNull();
    }

    @Test
    void testFindUserByEmail() throws ParseException {
        AdminUser adminUser =
        adminUserDao.create(AdminUser.builder()
                .firstName("Ahmed")
                .lastName("Saka")
                .email("ahmedsaka91@gmail.com")
                .password("Password12")
                .dateOfBirth(DateFormatter.formatDate("09/08/1991"))
                .build());

        AdminUser testAdminUser = adminUserDao.findUserByEmail(adminUser.getEmail());
        Assertions.assertThat(testAdminUser.getEmail()).isEqualTo(adminUser.getEmail());
        Assertions.assertThat(testAdminUser.getFirstName()).isEqualTo(adminUser.getFirstName());
        Assertions.assertThat(testAdminUser.getLastName()).isEqualTo(adminUser.getLastName());
    }

    @Test
    void testNullValueForFindUserByEmail(){
        Assertions.assertThat(adminUserDao.findUserByEmail(Mockito.anyString())).isNull();
    }
}