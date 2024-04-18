package com.example.project.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.project.entity.Reservation;

import jakarta.persistence.Transient;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ReservationDTO {
	// entity file 작성할때 아래 코드 추가 할 것:
	private int reserv_seq;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date reserv_date; // 예약 날짜
	private int reserv_price;
	private String reserv_time1; // 예약 시간1
	private String reserv_time2; // 예약 시간2
	private String reserv_time3; // 예약 시간3
	private String reserv_address;	 // 예약 주소
	private String employee_id; // 예약 상대(직원)
	private String client_id;
	private int reserv_state;
	private String reserv_statename;
	@Transient
	private boolean deletable;

	

	
	
	// Entity 객체 리턴
	public Reservation toEntity() {
		return new Reservation(reserv_seq, reserv_date,reserv_price, reserv_time1,reserv_time2,reserv_time3, reserv_address,
				employee_id, client_id, reserv_state,reserv_statename,deletable);
	}
}
