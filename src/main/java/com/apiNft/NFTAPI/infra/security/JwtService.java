package com.apiNft.NFTAPI.infra.security;

import com.apiNft.NFTAPI.entidades.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private final String secretkey = "123456";

    public String generateToken(Usuario usuario){
        Algorithm algorithm = Algorithm.HMAC256(secretkey);
        return JWT.create()
                .withIssuer("api-nft")
                .withIssuedAt(Instant.now())
                .withExpiresAt(getExpirationTime())
                .withClaim("authorities", usuario.getRole().name())
                .withClaim("id", usuario.getId())
                .withSubject(usuario.getUsername())
                .sign(algorithm);
    }

    public String validateToken(String token){
        Algorithm algorithm = Algorithm.HMAC256(secretkey);
        return JWT.require(algorithm)
                .withIssuer("api-nft")
                .build()
                .verify(token)
                .getSubject();
    }

    private Instant getExpirationTime(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
