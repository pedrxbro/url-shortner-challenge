package com.univali.urlshortner.service;

import com.univali.urlshortner.entity.UrlShortnerEntity;
import com.univali.urlshortner.repository.UrlShortnerRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDateTime;

@Service
public class UrlShortnerService {


    @Autowired
    private UrlShortnerRepository repository;

    public String shortenAndSave(String url) {
        String id;
        // Evitar IDs duplicados
        do {
            // Cria o ID da URL (Encurtado)
            id = RandomStringUtils.randomAlphanumeric(5,10);
        }
        while (repository.existsById(id));
        //Cria a Entidade URL
        repository.save(new UrlShortnerEntity(id, url, LocalDateTime.now().plusMinutes(1)));

        return id;
    }

    public HttpHeaders redirectToUrl(String id) {

        var url = repository.findById(id);

        if(url.isEmpty()){
            return null;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(url.get().getFullUrl()));

        return headers;

    }

}
