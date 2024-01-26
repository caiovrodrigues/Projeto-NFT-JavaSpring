package com.apiNft.NFTAPI.dto;

import com.apiNft.NFTAPI.entidades.Nft;

import java.time.LocalDateTime;

public record ResponseNftDTO(Long id, ResponseLoginUsuario user, String name, String description, Float price, Integer qtd, String img_url, LocalDateTime date) {
    public ResponseNftDTO(Nft nft){
        this(nft.getId(), new ResponseLoginUsuario(nft.getUser().getId(), nft.getUser().getUsername()), nft.getName(), nft.getDescription(), nft.getPrice(), nft.getQtd(), nft.getImg_url(), nft.getDate());
    }
}
