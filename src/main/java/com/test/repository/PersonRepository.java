package com.test.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.test.collection.Person;

@Repository
public interface PersonRepository extends MongoRepository<Person,String> {
    
    List<Person> findByFirstNameStartsWith(String name);

    @Query(value = "{'age':{$gte : ?0, $lte : ?1}}",
            fields = "{addresses: 0}")
    List<Person> findByAgeBetween(Integer minAge,Integer maxAge);
}
