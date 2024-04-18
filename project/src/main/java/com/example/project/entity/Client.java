package com.example.project.entity;

import com.example.project.dto.FreeBoardDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "client")
public class Client {
	private int client_seq;
	@Id
	private String client_id; 				// primary key
	private String client_pwd;				// 비밀번호
	private String client_name;				// 이름
	private String client_gender;			// 성별
	private String client_birthyear;		// 생년
	private String client_birthmonth;		// 월
	private String client_birthday;			// 일
	private String client_tel; 				// 전화번호		
	private String client_email; 			// 이메일
	private int client_state; 				// 계정상태
	
	private String client_statename;		// 0 : 계정정지 1: 계정사용
}
