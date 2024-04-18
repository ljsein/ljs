package com.example.project.entity;

import java.util.Date;

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
@NoArgsConstructor
@AllArgsConstructor
public class Reserv_time {
	
		@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESERV_TIME_SEQUENCE_GENERATOR")
		@SequenceGenerator(name = "RESERV_TIME_SEQUENCE_GENERATOR", sequenceName = "time_seq", initialValue = 1, allocationSize = 1)
		@Id
		private int time_seq; 
		private String time_employeeid; 
		@Temporal(TemporalType.DATE)
		private Date time_reservdate;
		private boolean time_state1; 
		private boolean time_state2;
		private boolean time_state3; 
		private boolean time_state4; 
		private boolean time_state5; 
		private boolean time_state6; 
		private boolean time_state7; 
		private boolean time_state8;

}
