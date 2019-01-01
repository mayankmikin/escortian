package com.whitehall.technologies.escortian.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Images {
	
	@Field(type = FieldType.Keyword)
	private String main_image;
	
	@Field(type = FieldType.Keyword)
	private List<String> images= new ArrayList<String>();

	public String getMain_image() {
		return main_image;
	}

	public void setMain_image(String main_image) {
		this.main_image = main_image;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}
	

}
