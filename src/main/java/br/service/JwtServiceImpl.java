package br.service;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JwtServiceImpl implements JwtService {

    private static final Duration EXPIRATION_TIME = Duration.ofDays(30);


    @Override
    public String generateJwt(String username, String perfil) {
        Instant expiryDate = Instant.now().plus(EXPIRATION_TIME);
        Set<String> roles = new HashSet<>();
        roles.add(perfil);

        return Jwt.issuer("unitins-jwt")
            .subject(username)
            .groups(roles)
            .expiresAt(expiryDate)
            .sign();
    }
}