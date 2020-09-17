package io.medalytics.elearning_platform.controller;

import io.medalytics.elearning_platform.model.request.RoleRequest;
import io.medalytics.elearning_platform.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/role")
public class RoleController {

    private RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping(value = "/createRole", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<?> createRole(@RequestBody RoleRequest request) {
        return new ResponseEntity<>(
                roleService.createRole(request),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<?> getRoleByName(@RequestBody RoleRequest request){
        return new ResponseEntity<>(
                roleService.getRoleByName(request),
                HttpStatus.OK
        );
    }
}
