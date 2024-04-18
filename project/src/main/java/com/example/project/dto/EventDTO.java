package com.example.project.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.example.project.entity.Client;
import com.example.project.entity.Event;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EventDTO {
	   private String event_imagecode;   //primary	
	   private String event_image;
	   private String event_startdate;       
	   private String event_enddate;       

	   // Entity 객체 리턴
	   public Event toEntity() {
	      return new Event(event_imagecode, event_image,event_startdate,event_enddate);
	   }
	}