package com.apiNft.NFTAPI;

import com.apiNft.NFTAPI.domain.repositories.UsuarioRepository;
import com.apiNft.NFTAPI.domain.entity.Role;
import com.apiNft.NFTAPI.domain.entity.Usuario;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class NftApiApplication implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public NftApiApplication(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(NftApiApplication.class);
        springApplication.setAdditionalProfiles("dev");
        springApplication.run();
    }

    @Override
    public void run(String... args) throws Exception {
        var admin = Usuario.builder()
                .username("admin")
                .password(passwordEncoder.encode("123"))
                .email("admin@gmail.com")
                .role(Role.ADMIN)
                .build();
        var user = Usuario.builder()
                .username("user")
                .password(passwordEncoder.encode("123"))
                .email("user@gmail.com")
                .role(Role.USER)
                .build();
        usuarioRepository.saveAll(List.of(user, admin));
    }
}
