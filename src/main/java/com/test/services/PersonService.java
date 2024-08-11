package com.test.services;

import java.util.List;

import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.test.collection.Person;

public interface PersonService {
    
    String save(Person person);
    List<Person> getPersonStartWith(String name);
    void delete(String id); 
    Page<Person> search(String name,Integer minAge,Integer maxAge,String city,Pageable pageable);
    List<Person> getPersonByAge(Integer minAge,Integer maxAge);
    List<Document> getOldestPersonByCity();
    List<Document> getPopulationByCity();
}
