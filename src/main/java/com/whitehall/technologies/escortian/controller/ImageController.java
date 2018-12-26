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

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ImageController {
	@GetMapping(value = "/api/image/{imagename}")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String imagename) throws IOException {
 
        ClassPathResource imgFile = new ClassPathResource("static/images/"+imagename+".jpg");
 
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(new InputStreamResource(imgFile.getInputStream()));
    }
}
