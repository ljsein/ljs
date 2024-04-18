package com.example.project.entity;


import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Pay {

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PAY_SEQUENCE_GENERATOR")
	@SequenceGenerator(name = "PAY_SEQUENCE_GENERATOR", sequenceName = "pay_seq", initialValue = 1, allocationSize = 1)
	@Id
	private int pay_seq;
	private String pay_clientid;
	private int pay_price;
	@Temporal(TemporalType.DATE)
	private Date pay_date;
	private String pay_statename;
	
}
