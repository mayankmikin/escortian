package com.whitehall.technologies.escortian.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.whitehall.technologies.escortian.constants.Constants.API_URLS;
import com.whitehall.technologies.escortian.model.Entity;
import com.whitehall.technologies.escortian.model.Person;
import com.whitehall.technologies.escortian.model.UploadFileResponse;
import com.whitehall.technologies.escortian.query.builders.SearchQueryBuilder;
import com.whitehall.technologies.escortian.repository.PersonRepository;
import com.whitehall.technologies.escortian.utils.FileStorage;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/upload")
public class UploadController {
	@Value("${prod.url}")
	private String fullImagePath;
	@Autowired
	FileStorage fileStorage;
	@Autowired
	SearchQueryBuilder builder;
	@Autowired
	PersonRepository repo;
	private static final Logger log = LoggerFactory.getLogger(UploadController.class);
    
    @PostMapping("/")
    public ResponseEntity<?> uploadMultipartFile(@RequestParam("uploadfile") MultipartFile file, Model model) {
		String savedpath="";
    	try {
    		savedpath=fileStorage.store(file);
			//model.addAttribute("message", "File uploaded successfully! -> filename = " + file.getOriginalFilename());
		} catch (Exception e) {
			//model.addAttribute("message", "Fail! -> uploaded filename: " + file.getOriginalFilename());
			return new ResponseEntity<String>("File Uploaded failed "+e,HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>(fullImagePath+savedpath,HttpStatus.CREATED);
    }
  /*  @PostMapping("/profile/{profilename}")
    public ResponseEntity<?> uploadMultipartFile(@RequestParam("uploadfile") MultipartFile file, Model model,@PathVariable String profilename) {
		String savedpath="";
    	try {
    		savedpath=fileStorage.store(file,profilename);
			//model.addAttribute("message", "File uploaded successfully! -> filename = " + file.getOriginalFilename());
		} catch (Exception e) {
			//model.addAttribute("message", "Fail! -> uploaded filename: " + file.getOriginalFilename());
			return new ResponseEntity<String>("File Uploaded failed "+e,HttpStatus.CONFLICT);
		}
		return new ResponseEntity<String>(fullImagePath+savedpath,HttpStatus.CREATED);
    }*/
    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }
    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorage.store(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(API_URLS.GET_IMAGE)
                .path("/"+fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }
    
    @PostMapping("/profile/{profilename}")
    public Person uploadMultipleFiles(@RequestParam("uploadfiles") MultipartFile[] files,
    	 Model model,@PathVariable String profilename) throws JsonProcessingException 
    {
    	List<Person>persons=builder.getPersonByProfileName(profilename);
    	log.info("person result size is{}",persons.size());
    	Person p =persons.get(0);
    	Entity e= p.getEntity();
    	//e.setImages();
    	List<UploadFileResponse> imagesuploaded= Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file,profilename))
                .collect(Collectors.toList());
         
    	imagesuploaded.forEach(i->e.getImages().getImages().add(i.getFileDownloadUri()));
    	e.getImages().setMain_image(imagesuploaded.get(0).getFileDownloadUri());
    	repo.save(p);
         return p;
    }
    
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file,String profilename) {
        String savedpath = fileStorage.store(file,profilename);

        /*String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(API_URLS.GET_IMAGE)
                .path("/"+fileName)
                .toUriString();*/
        String fileDownloadUri =fullImagePath+savedpath;
        log.info("image paths is {}",fileDownloadUri);
        return new UploadFileResponse(savedpath, fileDownloadUri,
                file.getContentType(), file.getSize());
    }
    

}
