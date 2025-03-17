package com.github.enma11235.generic.poll.system.configuration;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import javax.crypto.SecretKey;

@Configuration
public class TokenConfig {
    @Bean
    public SecretKey jwtSecretKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    @Value("${jwt.expiration-time}")
    private long tokenExpirationTime;

    @Bean
    public long getTokenExpirationTime() {
        return tokenExpirationTime;
    }
}
