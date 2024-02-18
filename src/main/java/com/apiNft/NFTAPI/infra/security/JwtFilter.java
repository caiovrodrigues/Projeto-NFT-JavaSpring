package com.apiNft.NFTAPI.infra.security;

import com.apiNft.NFTAPI.Repositories.UsuarioRepository;
import com.apiNft.NFTAPI.services.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var authorizationHeader = getAuthorizationFromHeader(request);

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            var token = authorizationHeader.substring("Bearer ".length());

            var username = jwtService.validateToken(token);

            var subject = usuarioRepository.findByUsername(username)
                    .orElseThrow(() -> new EntityNotFoundException(String.format("User '%s' not found.", username)));

            var usernamePassword = new UsernamePasswordAuthenticationToken(subject, null, subject.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(usernamePassword);
        }

        filterChain.doFilter(request, response);
    }

    private String getAuthorizationFromHeader(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }
}
