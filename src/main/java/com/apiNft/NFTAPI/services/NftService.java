package com.apiNft.NFTAPI.services;

import com.apiNft.NFTAPI.Repositories.NftRepository;
import com.apiNft.NFTAPI.dto.RequestCadastroNft;
import com.apiNft.NFTAPI.entity.Nft;
import com.apiNft.NFTAPI.entity.Usuario;
import com.apiNft.NFTAPI.infra.security.JwtService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Service
public class NftService {

    private final NftRepository repository;
    private final UsuarioService usuarioService;
    private final JwtService jwtService;
    private final MinioService minioService;

    @Transactional(readOnly = true)
    public Page<Nft> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Nft getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("O nft " + id + " não existe"));
    }

    @Transactional
    public Nft postNft(RequestCadastroNft nft, String token) {
        String username = jwtService.validateToken(token.substring(7));
        Usuario usuario = usuarioService.findByUsername(username);

        Nft newNft = new Nft(nft, usuario);
        return repository.save(newNft);
    }

    @Transactional
    public void saveNftPicture(Long id, MultipartFile file) {
        Nft nft = getById(id);
        StringBuilder urlMinio = new StringBuilder("/").append(nft.getId());
        urlMinio.append("/").append(file.getOriginalFilename());

        nft.setUrlMinio(urlMinio.toString());

        repository.save(nft);
        minioService.salvarImagem(urlMinio.toString(), file);

        log.info("Picture {} uploaded successfully! ", nft);
    }

    public void delete(Long id) {
        getById(id);
        this.repository.deleteById(id);
    }
}
