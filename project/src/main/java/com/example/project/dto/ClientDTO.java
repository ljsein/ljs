package com.example.project.dto;

import java.util.Date;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.beans.factory.annotation.Value;

import com.example.project.entity.Client;

import jakarta.persistence.Id;
import lombok.Builder.Default;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ClientDTO {
	   private int client_seq;
	   private String client_id;       // primary key
	   private String client_pwd;
	   private String client_name;
	   private String client_gender;
	   private String client_birthyear; 
	   private String client_birthmonth; 
	   private String client_birthday; 
	   private String client_tel;          // 전화번호
	   private String client_email;       // 이메일
	   private int client_state;          //계정상태
	   private String client_statename;

	   
	   // Entity 객체 리턴
	   public Client toEntity() {
	      return new Client(client_seq, client_id,client_pwd, client_name, client_gender,
	            client_birthyear, client_birthmonth,client_birthday, client_tel,client_email, client_state, client_statename);
	   }
	}