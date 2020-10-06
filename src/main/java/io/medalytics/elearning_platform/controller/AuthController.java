package io.medalytics.elearning_platform.controller;

import io.medalytics.elearning_platform.model.request.AdminUserRequest;
import io.medalytics.elearning_platform.model.request.AuthRequest;
import io.medalytics.elearning_platform.model.response.AuthResponse;
import io.medalytics.elearning_platform.service.AdminUserService;
import io.medalytics.elearning_platform.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/authenticate")
public class AuthController {

    private AdminUserService adminUserService;
    private JwtUtil jwtUtil;

    @Autowired
    public AuthController(AdminUserService adminUserService, JwtUtil jwtUtil) {
        this.adminUserService = adminUserService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> registerUser(@RequestBody AdminUserRequest request) throws ParseException {
        return new ResponseEntity<>(
                adminUserService.createAdminUser(request),
                HttpStatus.OK
        );
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        String jwt = jwtUtil.generateToken(adminUserService.findUserByEmail(request.getEmail()).get());
        return new ResponseEntity<>(
                new AuthResponse(jwt), HttpStatus.OK
        );
    }
}
