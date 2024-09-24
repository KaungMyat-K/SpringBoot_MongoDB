package com.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.test.collection.Photo;
import com.test.services.PhotoService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/photo")
public class PhotoController {
    
    @Autowired
    private PhotoService photoService;

    @PostMapping(value = "/uploaddb",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> storeDB(@RequestParam("image") MultipartFile multipartFile) {
        String id = photoService.savePhotoinDB(multipartFile.getOriginalFilename(),multipartFile).getId();
        return new ResponseEntity<>("successfully save "+id,HttpStatus.CREATED);
    }

    @PostMapping(value = "/uploadlocal",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> storeLocal(@RequestParam("image") MultipartFile multipartFile) {
        String name = photoService.savePhotoinLocal(multipartFile.getOriginalFilename(),multipartFile);
        return new ResponseEntity<>("successfully save "+name,HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> downloadPhoto(@PathVariable String id){
        Photo photo = photoService.getPhotoById(id);
        System.out.println("this is photo "+photo);
        Resource resource = new ByteArrayResource(photo.getPhoto().getData());
        
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+photo.getTitle()+"\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }   
    
}
