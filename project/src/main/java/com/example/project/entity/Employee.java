package com.example.project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
	@Id
	private String employee_id; 			// primary key
	private String employee_name;
	private String employee_birth; 			// 19yy/mm/dd
	private String employee_height;
	private String employee_gender;
	private int employee_price;	//가격
	private String employee_content; // 말머린
	private int employee_hit; // 예약 횟수
	private String employee_image;


}
