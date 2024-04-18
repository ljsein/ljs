package com.example.project.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.dao.ClientDAO;
import com.example.project.dao.EventDAO;
import com.example.project.dto.ClientDTO;
import com.example.project.entity.Client;
import com.example.project.entity.Event;
import com.example.project.entity.Reservation;
import com.example.project.repository.ClientRepository;

@Service
public class EventService {
	@Autowired
	EventDAO dao;
	
	
	//이벤트	
	// 이벤트 팝업
		public List<Event> eventPrint() {
			return dao.eventPrint();
		}
//	public List<Event> getEventList(){
//		return dao.EventList();
//	}
}
