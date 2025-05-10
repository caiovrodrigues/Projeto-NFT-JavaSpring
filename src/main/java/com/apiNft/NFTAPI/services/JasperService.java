package com.apiNft.NFTAPI.services;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JasperService {

    private final ResourceLoader resourceLoader;
    private final DataSource dataSource;

    private Map<String, Object> params = new HashMap<>();

    public void addParams(String key, Object value) {
        this.params.put(key, value);
    }

    public byte[] geraRelatorio() {
        byte[] bytes = null;
        try {
            Resource resource = resourceLoader.getResource("classpath:/jasperReports/nft.jasper");
            InputStream stream = resource.getInputStream();
            JasperPrint jasperPrint = JasperFillManager.fillReport(stream, this.params, dataSource.getConnection());
            bytes = JasperExportManager.exportReportToPdf(jasperPrint);
            dataSource.getConnection().close();
        } catch (JRException | SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return bytes;
    }
}
