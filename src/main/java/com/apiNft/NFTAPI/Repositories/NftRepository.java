package com.apiNft.NFTAPI.Repositories;

import com.apiNft.NFTAPI.entidades.Nft;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NftRepository extends JpaRepository<Nft, Long> {}
