package com.example.project.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.project.entity.Pay;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class PayDTO {

	private int pay_seq;
	private String pay_clientid;
	private int pay_price;
	private Date pay_date;
	private String pay_statename;
	
	public Pay toEntity() {
		
		return new Pay(pay_seq,pay_clientid, pay_price, pay_date, pay_statename);
		
	}
	
}
