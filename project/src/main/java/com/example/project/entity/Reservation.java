package com.example.project.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
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
public class Reservation {
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESERVATION_SEQUENCE_GENERATOR")
	@SequenceGenerator(name = "RESERVATION_SEQUENCE_GENERATOR", sequenceName = "reserv_seq", initialValue = 1, allocationSize = 1)
	@Id
	private int reserv_seq;
	@Temporal(TemporalType.DATE)
	private Date reserv_date; // 예약 날짜
	private int reserv_price;
	@Column(name="reserv_time1",nullable = true)
	private String reserv_time1; // 예약 시간1
	@Column(name="reserv_time2",nullable = true)
	private String reserv_time2; // 예약 시간2
	@Column(name="reserv_time3", nullable = true)
	private String reserv_time3; // 예약 시간3
	private String reserv_address;  // 예약 주소
	private String employee_id; // 예약 상대(직원)
	private String client_id;
	private int reserv_state; 
	private String reserv_statename;
	@Transient
	private boolean deletable;
	

}
