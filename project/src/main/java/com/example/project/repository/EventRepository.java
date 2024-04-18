package com.example.project.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.project.entity.Client;
import com.example.project.entity.Employee;
import com.example.project.entity.Event;

public interface EventRepository extends JpaRepository<Event, String>{
	@Query(value="update event set event_imagecode=':event_imagecode', event_image=':event_image', event_startdate = ':event_startdate', event_enddate = ':event_enddate'"
			+ " where event_imagecode=':event_imagecode'",
			   nativeQuery = true)
		Employee updateById(@Param("event_imagecode") String event_imagecode, @Param("event_image") String event_image
				, @Param("event_startdate") String event_startdate, @Param("event_enddate") String event_enddate);
	
	
//	@Query(value="select * from event", nativeQuery = true)
//		Set<Event> (@Param("event_imagecode") String event_imagecode, @Param("event_image") String event_image
//				, @Param("event_startdate") String event_startdate, @Param("event_enddate") String event_enddate);
}
