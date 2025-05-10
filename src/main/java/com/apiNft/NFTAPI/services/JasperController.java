package com.apiNft.NFTAPI.services;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/relatorio")
public class JasperController {

    private final JasperService jasperService;

    public JasperController(JasperService jasperService) {
        this.jasperService = jasperService;
    }

    @GetMapping
    public ResponseEntity<Void> gerarRelatorio(@PathVariable Long id, HttpServletResponse response) throws IOException {
        this.jasperService.addParams("user_id", id);
        byte[] bytes = this.jasperService.geraRelatorio();
        response.setHeader("Content-Disposition", "inline; filename=" + System.currentTimeMillis() + ".pdf");
        response.getOutputStream().write(bytes);
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        return ResponseEntity.ok().build();
    }
}
