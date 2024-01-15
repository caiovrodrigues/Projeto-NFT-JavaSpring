package com.apiNft.NFTAPI.services;

import com.apiNft.NFTAPI.Repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentRepository repository;



}
