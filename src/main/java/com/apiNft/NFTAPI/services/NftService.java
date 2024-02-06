package com.apiNft.NFTAPI.services;

import com.apiNft.NFTAPI.Repositories.NftRepository;
import com.apiNft.NFTAPI.dto.RequestCadastroNft;
import com.apiNft.NFTAPI.dto.ResponseNftDTO;
import com.apiNft.NFTAPI.entidades.Nft;
import com.apiNft.NFTAPI.entidades.Usuario;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NftService {

    @Autowired
    private NftRepository repository;

    @Autowired
    private UsuarioService usuarioService;

    @Transactional(readOnly = true)
    public List<Nft> getAll(){
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Nft getById(Long id){
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("O nft " + id + " não existe"));
    }

    @Transactional
    public Nft postNft(Long id, RequestCadastroNft nft) {
        Usuario usuario = usuarioService.getUser(id);

        var newNft = new Nft(nft, usuario);

        return repository.save(newNft);
    }

    public void delete(Long id) {
        getById(id);
        this.repository.deleteById(id);
    }
}
