package com.egs.shopping.service.impl;

import com.egs.shopping.domain.Customer;
import com.egs.shopping.domain.enumeration.CustomerRoles;
import com.egs.shopping.service.SecurityService;
import com.egs.shopping.service.exception.InvalidTokenException;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final String SECRET_KEY = "asdfSFS34wfsdfsdfSDSD54dfsddHGerQSNCK73SOWEK5354fdgdf4";

    @Override
    public String createToken(Customer customer) {
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(SECRET_KEY),
            SignatureAlgorithm.HS256.getJcaName());
        Instant now = Instant.now();
        String jwtToken = Jwts.builder()
            .claim("name", customer.getFirstName().concat(" ").concat(customer.getLastName()))
            .claim("email", customer.getEmail())
            .claim("role", customer.getRole())
            .setId(UUID.randomUUID().toString())
            .setIssuedAt(Date.from(now))
            .setExpiration(Date.from(now.plus(300l, ChronoUnit.MINUTES)))
            .signWith(SignatureAlgorithm.HS256, hmacKey)
            .compact();
        return jwtToken;
    }

    @Override
    public Customer parseToken(String token) {
        try {
            Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(SECRET_KEY),
                SignatureAlgorithm.HS256.getJcaName());
            Jws<Claims> jwt = Jwts.parser()
                .setSigningKey(hmacKey)
//                .build()
                .parseClaimsJws(token);

            Customer customer = new Customer();
            customer.setEmail(jwt.getBody().get("email", String.class));
            customer.setRole(CustomerRoles.valueOf(jwt.getBody().get("role", String.class)));

            return customer;

        } catch (JwtException | ClassCastException e) {
            throw new InvalidTokenException();
        }
    }
}
