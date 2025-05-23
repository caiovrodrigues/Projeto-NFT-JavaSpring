package com.apiNft.NFTAPI.services;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class MinioService {

    private final MinioClient minioClient;

    public void salvarImagem(String urlMinio, MultipartFile file) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder().bucket("nft").stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .object(urlMinio)
                            .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
