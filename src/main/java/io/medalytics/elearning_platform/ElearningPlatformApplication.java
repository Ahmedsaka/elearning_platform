package io.medalytics.elearning_platform;

import io.medalytics.elearning_platform.model.request.AdminUserRequest;
import io.medalytics.elearning_platform.model.request.RoleRequest;
import io.medalytics.elearning_platform.service.AdminUserService;
import io.medalytics.elearning_platform.service.impl.AdminUserServiceImpl;
import io.medalytics.elearning_platform.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class ElearningPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElearningPlatformApplication.class, args);
    }

    @Component
    class InitialDataLoader implements CommandLineRunner {

        private AdminUserService adminUserService;
        private RoleService roleService;

        @Autowired
        public InitialDataLoader(AdminUserService adminUserService, RoleService roleService) {
            this.adminUserService = adminUserService;
            this.roleService = roleService;
        }

        @Override
        public void run(String... args) throws Exception {
//            List<String> roles = Arrays.asList("ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_STUDENT", "ROLE_INSTRUCTOR");
//            for (String role : roles) {
//                roleService.createRole(
//                        RoleRequest.builder()
//                                .roleName(role)
//                                .build()
//                );
//            }
//
//            AdminUserRequest request = AdminUserRequest.builder()
//                    .firstName("admin")
//                    .lastName("admin")
//                    .email("admin@user.com")
//                    .password("Password12")
//                    .roleName("ROLE_ADMIN")
//                    .dateOfBirth("09/08/1991")
//                    .build();
//            adminUserService.createAdminUser(request);
        }
    }
}
