package com.apiNft.NFTAPI.services;

import com.apiNft.NFTAPI.Repositories.NftRepository;
import com.apiNft.NFTAPI.dto.RequestCadastroNft;
import com.apiNft.NFTAPI.entidades.Nft;
import com.apiNft.NFTAPI.entidades.Usuario;
import com.apiNft.NFTAPI.infra.security.JwtService;
import jakarta.persistence.EntityNotFoundException;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NftService {

    @Autowired
    private NftRepository repository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtService jwtService;

    @Transactional(readOnly = true)
    public Page<Nft> getAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Nft getById(Long id){
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("O nft " + id + " não existe"));
    }

    @Transactional
    public Nft postNft(RequestCadastroNft nft, String token) {
        String username = jwtService.validateToken(token.substring(7));
        Usuario usuario = usuarioService.findByUsername(username);

        Nft newNft = new Nft(nft, usuario);
        return repository.save(newNft);
    }

    public void delete(Long id) {
        getById(id);
        this.repository.deleteById(id);
    }
}
