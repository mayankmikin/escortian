package com.whitehall.technologies.escortian.utils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.whitehall.technologies.escortian.constants.Constants.API_URLS;

import static  com.whitehall.technologies.escortian.constants.Constants.FileLocations;

@Service
public class FileStorageImpl implements FileStorage {

	Logger log = LoggerFactory.getLogger(this.getClass().getName());
	private final Path rootLocation = Paths.get(FileLocations.savepath);
 
	@Override
	public String store(MultipartFile file){
		try {
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
           
        } catch (Exception e) {
        	throw new RuntimeException("FAIL! -> message = " + e.getMessage());
        }
		 return this.rootLocation.resolve(file.getOriginalFilename()).toString();
	}
	
	@Override
    public Resource loadFile(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }else{
            	throw new RuntimeException("FAIL!");
            }
        } catch (MalformedURLException e) {
        	throw new RuntimeException("Error! -> message = " + e.getMessage());
        }
    }
    
	@Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
 
	@Override
    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage!");
        }
    }
 
	@Override
	public Stream<Path> loadFiles() {
        try {
            return Files.walk(this.rootLocation, 1)
                .filter(path -> !path.equals(this.rootLocation))
                .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
        	throw new RuntimeException("\"Failed to read stored file");
        }
	}

	@Override
	public String store(MultipartFile files, String folderName) {
		
		try {
			  Path path = Paths.get(FileLocations.savepath+"\\"+folderName);
			  String finalPath=folderName+"/"+files.getOriginalFilename();
		        //if directory exists?
		        if (!Files.exists(path)) 
		        {
		            Files.createDirectories(path);
		        } 
		        else 
			    {
		        	File file = new File(path+"/"+files.getOriginalFilename());
		        	if (file.exists() && file.isFile())
					  {
						log.info("filealready exists : "+path+"/"+finalPath);
						log.info("deleteing file");
					  file.delete();
					  }
			        	
			     }
			path=this.rootLocation.resolve(finalPath);
            Files.copy(files.getInputStream(),path);
            return API_URLS.GET_IMAGE+"/"+finalPath;
            
        } catch (Exception e) {
        	throw new RuntimeException("FAIL! -> message = " + e.getMessage());
        }
	}

}
