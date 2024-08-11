package com.test.services;

import org.springframework.web.multipart.MultipartFile;

import com.test.collection.Photo;

public interface PhotoService {
    
    Photo savePhoto(String originalFileName,MultipartFile multipartFile);
    Photo getPhotoById(String id);
}
