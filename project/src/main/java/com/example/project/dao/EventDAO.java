package com.example.project.dao;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.stereotype.Repository;

import com.example.project.dto.EventDTO;
import com.example.project.dto.FreeBoardDTO;
import com.example.project.entity.Event;
import com.example.project.entity.Freeboard;
import com.example.project.repository.EventRepository;
import com.example.project.repository.FreeBoardRepository;

@Repository
public class EventDAO {
	@Autowired
	EventRepository eventRepository;
	//이벤트 목록
	public List<Event> eventList() {
		return eventRepository.findAll();
	}
	
	// 이벤트 1개
	public Event event(String event_imagecode) {
		Event event = eventRepository.findById(event_imagecode).orElse(null);
		return event;
	}
	
	//이벤트 등록
	public boolean eventWrite(Event event) {
		boolean result = false;
		event = eventRepository.save(event);
		if (event != null) {
			result = true;
		}
		return result;
	}
	// 이벤트 수정
	public boolean eventModify(String event_imagecode, EventDTO dto) {
		boolean result = false;
		Event event1 = eventRepository.findById(event_imagecode).orElse(null);
		if(event1 != null) {
			eventRepository.delete(event1);
			Event event = dto.toEntity();
			event.setEvent_imagecode(dto.getEvent_imagecode());
			event.setEvent_image(dto.getEvent_image());
			event.setEvent_startdate(dto.getEvent_startdate());
			event.setEvent_enddate(dto.getEvent_enddate());
			
			eventRepository.save(event);
			result = true;
		}
		
		return result;
	}
	// 이벤트 삭제
	public boolean eventDelete(String event_imagecode) {
		boolean result = false;
		Event event = eventRepository.findById(event_imagecode).orElse(null);
		if(event != null) {
			eventRepository.delete(event);
			result = true;
		}else result = false;
		
		return result;
	}
	
	// 이벤트 출력
	public List<Event> eventPrint() {
		return  eventRepository.findAll();
	}

}