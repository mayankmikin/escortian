package com.whitehall.technologies.escortian.controller;

import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import static com.whitehall.technologies.escortian.constants.Constants.API_URLS;
import static com.whitehall.technologies.escortian.constants.Constants.FileLocations;
@CrossOrigin(origins = {"http://localhost:4200","https://escortian-front.herokuapp.com/"})
@RestController
public class ImageController {
	@GetMapping(value =API_URLS.GET_IMAGE+"/{imagename}")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String imagename) throws IOException {
 
        ClassPathResource imgFile = new ClassPathResource(FileLocations.getpath+imagename);
 
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(new InputStreamResource(imgFile.getInputStream()));
    }
	@GetMapping(value =API_URLS.GET_IMAGE+"/{profilename}/{imagename}")
    public ResponseEntity<InputStreamResource> getImageWithProfile(@PathVariable String profilename,@PathVariable String imagename) throws IOException {
 
        ClassPathResource imgFile = new ClassPathResource(FileLocations.getpath+profilename+"/"+imagename);
 
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new InputStreamResource(imgFile.getInputStream()));
    }
}
