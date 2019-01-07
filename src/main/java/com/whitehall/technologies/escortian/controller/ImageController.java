package com.whitehall.technologies.escortian.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.Resource;
import com.whitehall.technologies.escortian.constants.Constants.FileLocations;
import com.whitehall.technologies.escortian.utils.FileStorage;

import static com.whitehall.technologies.escortian.constants.Constants.API_URLS;
import static com.whitehall.technologies.escortian.constants.Constants.FileLocations;
@CrossOrigin(origins = {"http://localhost:4200","https://escortian-front.herokuapp.com/"})
@RestController
public class ImageController {
	private final Path rootLocation = Paths.get(FileLocations.savepath);
	@Autowired
	FileStorage fileStorage;
	
	@GetMapping(value =API_URLS.GET_IMAGE+"/{imagename}")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String imagename) throws IOException {
 
        ClassPathResource imgFile = new ClassPathResource(FileLocations.getpath+imagename);
 
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(new InputStreamResource(imgFile.getInputStream()));
    }
	@GetMapping(value =API_URLS.GET_IMAGE+"/{profilename}/{imagename}")
    public ResponseEntity<Resource> getImageWithProfile(@PathVariable String profilename,@PathVariable String imagename) throws IOException {
 
        //ClassPathResource imgFile = new ClassPathResource(FileLocations.getpath+profilename+"/"+imagename);
        Resource file = fileStorage.loadFile(profilename+"/"+imagename);
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(file);
                //.body(new InputStreamResource(imgFile.getInputStream()));
    }
}
