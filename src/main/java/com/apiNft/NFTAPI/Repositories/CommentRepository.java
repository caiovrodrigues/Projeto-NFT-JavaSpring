package com.apiNft.NFTAPI.Repositories;

import com.apiNft.NFTAPI.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {}
