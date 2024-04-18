package com.example.project.entity;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.example.project.dto.FreeBoardDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "event")
public class Event {
	@Id
	private String event_imagecode;   //primary
	private String event_image;

	private String event_startdate;  

	private String event_enddate;     
}
