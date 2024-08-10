package com.test.collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "address")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Address {
    
    @Id
    private String address1;
    private String address2;
    private String city;
}
