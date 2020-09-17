package io.medalytics.elearning_platform.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.medalytics.elearning_platform.model.AdminUser;
import io.medalytics.elearning_platform.model.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${security.jwt.token.secret.key}")
    private String SECRET_KEY;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(AdminUser adminUser) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, adminUser.getEmail());
    }

    public String generateToken(String subject, List<Role> roles) {
        Claims claims = Jwts.claims().setSubject(subject);
        claims.put("roles", roles);

        Date now = new Date();

        Date expiration = new Date(now.getTime() + 1000 * 60 * 60 * 10);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .compact();
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }


    public Boolean validateToken(String token, AdminUser adminUser) {
        final String username = extractUsername(token);
        return (username.equals(adminUser.getEmail()) && !isTokenExpired(token));
    }
}
