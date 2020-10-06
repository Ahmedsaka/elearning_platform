package io.medalytics.elearning_platform.security;

import io.jsonwebtoken.Claims;
import io.medalytics.elearning_platform.exception.NotFoundException;
import io.medalytics.elearning_platform.model.AdminUser;
import io.medalytics.elearning_platform.service.AdminUserService;
import io.medalytics.elearning_platform.service.RoleService;
import io.medalytics.elearning_platform.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AuthenticationManager implements org.springframework.security.authentication.AuthenticationManager {

    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getCredentials().toString();

        if (jwtUtil.isTokenExpired(token)){
            return null;
        }

//       After getting the token from the request, I want to extract the subject(email in this case)
        Claims claims = jwtUtil.extractAllClaims(token);
        String  subject = claims.getSubject();

//       Validate the email using adminUserService

        AdminUser adminUser = adminUserService.findUserByEmail(subject).get();
        if (!subject.equals(adminUser.getEmail())){
            throw new NotFoundException(String.format("AdminUser %s not found", subject));
        }

        return new UsernamePasswordAuthenticationToken(subject, null,
                        roleService.getRoleByUserId(adminUser.getId())
                                .get()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                        .collect(Collectors.toList()));

    }
}
