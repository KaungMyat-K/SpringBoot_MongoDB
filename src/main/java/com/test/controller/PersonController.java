package com.test.controller;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.collection.Person;
import com.test.services.PersonService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/person")
public class PersonController {
    
    @Autowired
    private PersonService personService;

    @PostMapping
    public ResponseEntity<String> savePerson(@RequestBody Person person){
       String userId = personService.save(person);
        return new ResponseEntity<>("successfully add "+userId,HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Person>> getPersonStartWith(@RequestParam("name") String name) {
        List<Person> persons = personService.getPersonStartWith(name);
        return new ResponseEntity<>(persons,HttpStatus.OK);
    }

    @GetMapping("/age")
    public ResponseEntity<List<Person>> getPersonByAge(@RequestParam Integer minAge,@RequestParam Integer maxAge) {
        List<Person> persons = personService.getPersonByAge(minAge,maxAge);
        return new ResponseEntity<>(persons,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePerson(@PathVariable String id) {
        personService.delete(id);
        return new ResponseEntity<>("sucessfully delete",HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Person>> searchPerson(@RequestParam(required = false) String name,
                                                    @RequestParam(required = false) Integer minAge,
                                                    @RequestParam(required = false) Integer maxAge,
                                                    @RequestParam(required = false) String city,
                                                    @RequestParam(defaultValue = "0") Integer page,
                                                    @RequestParam(defaultValue = "5") Integer size
                                                    ){
        Pageable pageable = PageRequest.of(page,size);
        Page<Person> persons = personService.search(name,minAge,maxAge,city,pageable);
        return new ResponseEntity<>(persons,HttpStatus.OK);
    }

    @GetMapping("/oldestPerson")
    public ResponseEntity<List<Document>> getOldestPerson(){
        List<Document> persons = personService.getOldestPersonByCity();
        return new ResponseEntity<>(persons,HttpStatus.OK);
    }
    
}
