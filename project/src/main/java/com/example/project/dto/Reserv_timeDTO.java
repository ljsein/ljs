package com.example.project.dto;

import java.util.Date;

import com.example.project.entity.Reserv_time;

import lombok.Data;
import lombok.ToString;


@ToString
@Data
public class Reserv_timeDTO {
	private int time_seq; 
	private String time_employeeid; 
	private Date time_reservdate;
	private boolean time_state1; 
	private boolean time_state2;
	private boolean time_state3; 
	private boolean time_state4; 
	private boolean time_state5; 
	private boolean time_state6; 
	private boolean time_state7; 
	private boolean time_state8;
	
	public Reserv_time toEntity() {
		return new Reserv_time(time_seq,time_employeeid,time_reservdate,time_state1,time_state2,time_state3,time_state4,time_state5,time_state6,time_state7,time_state8);
	}
}
