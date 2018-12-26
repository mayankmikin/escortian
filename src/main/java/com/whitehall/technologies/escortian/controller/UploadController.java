package com.whitehall.technologies.escortian.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.whitehall.technologies.escortian.utils.FileStorage;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/upload")
public class UploadController {
	
	@Autowired
	FileStorage fileStorage;
	
    /*@GetMapping("/")
    public ResponseEntity<?> index() {
    	return new ResponseEntity<String>("File Uploaded successfully",HttpStatus.OK);
    }*/
    
    @PostMapping("/")
    public ResponseEntity<?> uploadMultipartFile(@RequestParam("uploadfile") MultipartFile file, Model model) {
		try {
			fileStorage.store(file);
			//model.addAttribute("message", "File uploaded successfully! -> filename = " + file.getOriginalFilename());
		} catch (Exception e) {
			//model.addAttribute("message", "Fail! -> uploaded filename: " + file.getOriginalFilename());
			return new ResponseEntity<String>("File Uploaded failed "+e,HttpStatus.CREATED);
		}
		return new ResponseEntity<String>("File Uploaded successfully",HttpStatus.CREATED);
    }
}
