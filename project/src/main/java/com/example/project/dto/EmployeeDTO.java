package com.example.project.dto;

import org.springframework.web.multipart.MultipartFile;

import com.example.project.entity.Employee;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class EmployeeDTO {
	private String employee_id; 			// primary key
	private String employee_name;		// 이름
	private String employee_birth; 			// 19yy/mm/dd
	private String employee_height;
	private String employee_gender;
	private int employee_price;	//가격
	private String employee_content; // 말머리
	private int employee_hit; // 예약 횟수
	private String employee_image;

	
	private MultipartFile employee_imagefile; // 이미지

	// Entity 객체 리턴
	public Employee toEntity() {
		return new Employee(employee_id, employee_name,employee_birth, employee_height, employee_gender, employee_price,employee_content, employee_hit,employee_image);
	}
}
