package com.whitehall.technologies.escortian.utils;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorage {
	public String store(MultipartFile file);
	public String store(MultipartFile file,String folderName);
	public Resource loadFile(String filename);
	public void deleteAll();
	public void init();
	public Stream<Path> loadFiles();
}
