package com.univali.urlshortner.repository;

import com.univali.urlshortner.entity.UrlShortnerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlShortnerRepository extends MongoRepository<UrlShortnerEntity, String> {
}
