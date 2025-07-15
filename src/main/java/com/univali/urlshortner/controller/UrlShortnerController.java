package com.univali.urlshortner.controller;

import com.univali.urlshortner.dto.UrlRequestDTO;
import com.univali.urlshortner.service.UrlShortnerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UrlShortnerController {

    @Autowired
    private UrlShortnerService service;

    @PostMapping(value="shorten-url")
    public ResponseEntity<String> urlShortner(@RequestBody UrlRequestDTO urlRequestDTO,
                                              HttpServletRequest request) {

        String url = urlRequestDTO.getUrl();
        String shortCode = service.shortenAndSave(url);
        // Pega a URL base pela requisição
        String baseUrl = String.format("%s://%s:%d/",
                request.getScheme(),
                request.getServerName(),
                request.getServerPort());

        // Concatena a URL encurtada
        String shortUrl = baseUrl + shortCode;

        return ResponseEntity.ok(shortUrl);
    }

    @GetMapping("{id}")
    public ResponseEntity<Void> redirectToUrl(@PathVariable("id") String id) {

        HttpHeaders headers = service.redirectToUrl(id);

        if (headers == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }

}
