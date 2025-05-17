package com.apiNft.NFTAPI.infra.security;

import com.apiNft.NFTAPI.domain.entity.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.time.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    @Value("{spring.security.secret:123}")
    private String secret;

    public String generateToken(Usuario usuario) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withIssuer("api-nft")
                .withIssuedAt(Instant.now())
                .withExpiresAt(getExpirationTime())
                .withClaim("authorities", usuario.getRole().name())
                .withClaim("id", usuario.getId())
                .withSubject(usuario.getUsername())
                .sign(algorithm);
    }

    public String validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.require(algorithm)
                .withIssuer("api-nft")
                .build()
                .verify(token)
                .getSubject();
    }

    private Instant getExpirationTime() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
