package com.test.services.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

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

    //private final String uploadDir = "src/main/resources/static/images/ads";

    private final String uploadDir = "D:/uploaded_images/";

    @Autowired
    private PhotoRepository photoRepository;

    @Override
    public Photo savePhotoinDB(String originalFileName,MultipartFile multipartFile) {
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

    @Override
    public String savePhotoinLocal(String originalFileName, MultipartFile multipartFile) {

        //store in local project
        // Path uploadPath = Path.of(uploadDir);
        // Path filePath = uploadPath.resolve(originalFileName);
        // try {
        // if(!Files.exists(uploadPath)){
        //         Files.createDirectories(uploadPath);
        // }
        //     Files.copy(multipartFile.getInputStream(),filePath,StandardCopyOption.REPLACE_EXISTING);
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }

        // return originalFileName;


        //store in drive d
         String folderPath = "D:/uploaded_images/";
         File directory = new File(folderPath);
         if (!directory.exists()) {
             directory.mkdirs();
         }
         String fileName = multipartFile.getOriginalFilename();
         File destinationFile = new File(folderPath + fileName);
 
         try {
            multipartFile.transferTo(destinationFile);
             return "File uploaded successfully: " + folderPath + fileName;
         } catch (IOException e) {
             e.printStackTrace();
             return "File upload failed!";
         }
    }
    
}
