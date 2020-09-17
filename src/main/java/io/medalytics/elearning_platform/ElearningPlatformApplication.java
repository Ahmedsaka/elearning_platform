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
//            AdminUserRequest request = AdminUserRequest.builder()
//                    .firstName("admin")
//                    .lastName("admin")
//                    .email("admin@user.com")
//                    .password("Password12")
//                    .roleName("ROLE_ADMIN")
//                    .dateOfBirth("09/08/1991")
//                    .build();
//
//            RoleRequest roleRequest = RoleRequest.builder()
//                    .roleName(request.getRoleName())
//                    .build();
//
//            roleService.createRole(roleRequest);
//            adminUserService.createAdminUser(request);
        }



    }
}
