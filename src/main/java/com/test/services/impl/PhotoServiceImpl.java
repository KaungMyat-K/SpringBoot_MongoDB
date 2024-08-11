package com.test.services.impl;

import java.io.IOException;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.test.collection.Photo;
import com.test.repository.PhotoRepository;
import com.test.services.PhotoService;

@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    @Override
    public Photo savePhoto(String originalFileName,MultipartFile multipartFile) {
        Photo photo = new Photo();
        photo.setTitle(originalFileName);
        try {
            photo.setPhoto(new Binary(BsonBinarySubType.BINARY,multipartFile.getBytes()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return photoRepository.save(photo);
    }

    @Override
    public Photo getPhotoById(String id) {
       return photoRepository.findById(id).get();
    }
    
}
