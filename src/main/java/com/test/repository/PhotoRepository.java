package com.test.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.test.collection.Photo;

@Repository
public interface PhotoRepository extends MongoRepository<Photo,String> {
    
}
