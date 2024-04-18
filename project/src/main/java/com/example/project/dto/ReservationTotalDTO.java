package com.example.project.dto;

import java.util.Date;

import lombok.Data;
import lombok.ToString;
@ToString
@Data
public class ReservationTotalDTO {
	
	private int reserv_seq;
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
	private Date time_reservdate; // time용 date
	private boolean time_state1; 
	private boolean time_state2;
	private boolean time_state3; 
	private boolean time_state4; 
	private boolean time_state5; 
	private boolean time_state6; 
	private boolean time_state7; 
	private boolean time_state8;


}
