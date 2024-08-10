package com.test.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import com.test.collection.Person;
import com.test.repository.PersonRepository;
import com.test.services.PersonService;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public String save(Person person) {
        return personRepository.save(person).getPersonId();
    }

    @Override
    public List<Person> getPersonStartWith(String name) {
       return personRepository.findByFirstNameStartsWith(name);
    }

    @Override
    public void delete(String id) {
        personRepository.deleteById(id);
    }

    @Override
    public List<Person> getPersonByAge(Integer minAge, Integer maxAge) {
       return personRepository.findByAgeBetween(minAge, maxAge);
    }

    @Override
    public Page<Person> search(String name, Integer minAge, Integer maxAge, String city, Pageable pageable) {
        Query query = new Query().with(pageable);
        List<Criteria> criteria = new ArrayList<>();
        if (name != null && !name.isEmpty()) {
            criteria.add(Criteria.where("firstName").regex(name,"i"));
        }
        if(minAge != null && maxAge != null){
            criteria.add(Criteria.where("age").gte(minAge).lte(maxAge));
        }
        if(city != null && !city.isEmpty()){
            criteria.add(Criteria.where("addresses.city").is(city));
        }
        if(!criteria.isEmpty()){
            query.addCriteria(
                new Criteria().andOperator(criteria.toArray(new Criteria[0]))
                );
        }
        Page<Person> person = PageableExecutionUtils.getPage(
                                                            mongoTemplate.find(query,Person.class), 
                                                            pageable, 
                                                            () -> mongoTemplate.count(query.skip(0).limit(0),Person.class)
                                                            );
        return person;
    }

    @Override
    public List<Document> getOldestPersonByCity() {
        return null;
    }
    
    


}
