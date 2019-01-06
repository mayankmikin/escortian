package com.whitehall.technologies.escortian.model;

import java.util.List;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

public class Entity 
{
	@Field(type = FieldType.Text)
	private String name;
	@Field(type = FieldType.Text)
	private String label;
	@Field(type = FieldType.Text)
	private String about_me;
	@Field(type = FieldType.Text)
	private String contact_no;
	@Field(type = FieldType.Text)
	private List<String> languages;
	@Field(type = FieldType.Text)
	private List<String> location_availablity;
	@Field(type = FieldType.Text)
	private List<String> time_slot_available;
	@Field(type = FieldType.Text)
	private Images images;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getAbout_me() {
		return about_me;
	}
	public void setAbout_me(String about_me) {
		this.about_me = about_me;
	}
	public List<String> getLanguages() {
		return languages;
	}
	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}
	public List<String> getLocation_availablity() {
		return location_availablity;
	}
	public void setLocation_availablity(List<String> location_availablity) {
		this.location_availablity = location_availablity;
	}
	public List<String> getTime_slot_available() {
		return time_slot_available;
	}
	public void setTime_slot_available(List<String> time_slot_available) {
		this.time_slot_available = time_slot_available;
	}
	public Images getImages() {
		return images;
	}
	public void setImages(Images images) {
		this.images = images;
	}
	public String getContact_no() {
		return contact_no;
	}
	public void setContact_no(String contact_no) {
		this.contact_no = contact_no;
	}
	
	
	
	
	
}
