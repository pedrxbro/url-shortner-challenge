package com.univali.urlshortner.dto;

public class UrlRequestDTO {
    private String url;
    private String expiration;

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getExpiration() { return expiration; }
    public void setExpiration(String expiration) { this.expiration = expiration; }
}
