package com.univali.urlshortner.service;

import com.univali.urlshortner.entity.UrlShortnerEntity;
import com.univali.urlshortner.repository.UrlShortnerRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.Date;

@Service
public class UrlShortnerService {


    @Autowired
    private UrlShortnerRepository repository;

    public String shortenAndSave(String url, String expiration) {
        String id;
        // Evitar IDs duplicados
        do {
            // Cria o ID da URL (Encurtado)
            id = RandomStringUtils.randomAlphanumeric(5,10);
        }
        while (repository.existsById(id));

        Date expirationDate = null;

        if (expiration != null && !"null".equalsIgnoreCase(expiration)) {
            try {
                LocalDateTime expirationDateTime = LocalDateTime.parse(expiration);
                expirationDate = Date.from(
                        expirationDateTime.atZone(ZoneId.systemDefault()).toInstant()
                );
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Data de expiração inválida. Use formato ISO-8601.");
            }
        }
        //Cria a Entidade URL
        repository.save(new UrlShortnerEntity(id, url, expirationDate));

        return id;
    }

    public HttpHeaders redirectToUrl(String id) {
        var urlOpt = repository.findById(id);

        if (urlOpt.isEmpty()) {
            return null;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(urlOpt.get().getFullUrl()));
        return headers;
    }
}
