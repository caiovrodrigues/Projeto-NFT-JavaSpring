package com.apiNft.NFTAPI;

import com.apiNft.NFTAPI.Repositories.UsuarioRepository;
import com.apiNft.NFTAPI.entity.Role;
import com.apiNft.NFTAPI.entity.Usuario;
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
                .role(Role.USER)
                .build();
        usuarioRepository.save(admin);
    }
}
