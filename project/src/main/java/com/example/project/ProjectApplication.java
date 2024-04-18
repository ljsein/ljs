package com.example.project;

import java.util.Random;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import jakarta.mail.MessagingException;



@SpringBootApplication
public class ProjectApplication {
	
	
	 @Autowired private EmailSenderService senderService;
	 

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}
	
	
	  @EventListener(ApplicationReadyEvent.class) public void triggerMail() throws
	  MessagingException {
	  
	  Random random = new Random(); int randomNumber = 100000 +
	  random.nextInt(900000);
	  
	  
	  String messageBody = String.valueOf(randomNumber);
	  
	  senderService.sendSimpleEmail("ohdktest@gmail.com", messageBody, "인증코드입니다.");
	  }
	 

}
