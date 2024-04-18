package com.example.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.project.dao.AdminDAO;
import com.example.project.dao.ClientDAO;
import com.example.project.dao.EventDAO;
import com.example.project.dto.EmployeeDTO;
import com.example.project.dto.EventDTO;
import com.example.project.entity.Client;
import com.example.project.entity.Employee;
import com.example.project.entity.Event;
import com.example.project.entity.Pay;
import com.example.project.entity.Reserv_time;
import com.example.project.entity.Reservation;
import com.example.project.repository.ClientRepository;
import com.example.project.repository.EmployeeRepository;
import com.example.project.repository.ReservationRepository;


import jakarta.websocket.ClientEndpoint;

@Service
public class AdminService {
	@Autowired
	ClientDAO clientDAO;
	@Autowired
	EventDAO eventDAO;
	@Autowired
	AdminDAO adminDAO;

	// 회원목록 출력
	public List<Client> getClientList() {
		return clientDAO.ClientList();
	}

	// 회원상태 수정
	public boolean stateUpdate(String client_id, String client_state) {
		return clientDAO.stateUpdate(client_id, client_state);
	}

	// 이벤트 리스트 출력
	public List<Event> getEventList() {
		return eventDAO.eventList();
	}

	// 이벤트 이미지 등록
	public boolean EventWrite(Event event) {
		return eventDAO.eventWrite(event);
	}

	// 이벤트 1개
	public Event event(String event_imagecode) {
		return eventDAO.event(event_imagecode);
	}

	// 이벤트 수정
	public boolean eventModify(String event_imagecode, EventDTO dto) {
		return eventDAO.eventModify(event_imagecode, dto);
	}

	// 이벤트 삭제
	public boolean eventDelete(String event_imagecode) {
		return eventDAO.eventDelete(event_imagecode);
	}
	
	
	//////////////////////////////////////////////////////////////////////
	// 예약내역관리
	public List<Reservation> adminReservationList() {
		// TODO Auto-generated method stub
		return adminDAO.adminReservationList();
	}

	// 결제내역관리
	public List<Pay> adminPayList() {

		return adminDAO.adminPayList();
	}

	// 예약여부관리
	public boolean adminReservationState(int reserv_seq, String reserv_state) {
		// TODO Auto-generated method stub
		return adminDAO.adminReservationState(reserv_seq, reserv_state);
	}
	// 시간관리
	public List<Reserv_time> adminReservTimeList() {
		// TODO Auto-generated method stub
		return adminDAO.adminReservTimeList();
	}
	// 직원시간선택여부관리1
	public boolean adminTimeState1(int time_seq,String time_employeeid,String time_reservdate, String time_state1 ) {
		// TODO Auto-generated method stub
		return adminDAO.adminTimeState1(time_seq,time_employeeid,time_reservdate, time_state1);
	}

	// 직원시간선택여부관리2
	public boolean adminTimeState2(int time_seq,String time_employeeid,String time_reservdate, String time_state2) {
		// TODO Auto-generated method stub
		return adminDAO.adminTimeState2(time_seq,time_employeeid,time_reservdate, time_state2);
	}

	// 직원시간선택여부관리3
	public boolean adminTimeState3(int time_seq,String time_employeeid,String time_reservdate, String time_state3) {
		// TODO Auto-generated method stub
		return adminDAO.adminTimeState3(time_seq,time_employeeid,time_reservdate, time_state3);
	}

	// 직원시간선택여부관리4
	public boolean adminTimeState4(int time_seq,String time_employeeid,String time_reservdate, String time_state4) {
		// TODO Auto-generated method stub
		return adminDAO.adminTimeState4(time_seq,time_employeeid,time_reservdate, time_state4);
	}

	// 직원시간선택여부관리5
	public boolean adminTimeState5(int time_seq,String time_employeeid,String time_reservdate, String time_state5) {
		// TODO Auto-generated method stub
		return adminDAO.adminTimeState5(time_seq,time_employeeid,time_reservdate, time_state5);
	}

	// 직원시간선택여부관리6
	public boolean adminTimeState6(int time_seq,String time_employeeid,String time_reservdate, String time_state6) {
		// TODO Auto-generated method stub
		return adminDAO.adminTimeState6(time_seq,time_employeeid,time_reservdate, time_state6);
	}

	// 직원시간선택여부관리7
	public boolean adminTimeState7(int time_seq,String time_employeeid,String time_reservdate, String time_state7) {
		// TODO Auto-generated method stub
		return adminDAO.adminTimeState7(time_seq,time_employeeid,time_reservdate, time_state7);
	}

	// 직원시간선택여부관리8
	public boolean adminTimeState8(int time_seq,String time_employeeid,String time_reservdate, String time_state8) {
		// TODO Auto-generated method stub
		return adminDAO.adminTimeState8(time_seq,time_employeeid,time_reservdate, time_state8);
	}
    ///////////////////////////////////////////////////
	// 직원관리
	public List<Employee> adminEmployeeList() {
		// TODO Auto-generated method stub
		return adminDAO.adminEmployeeList();
	}

	// 직원정보등록
	public boolean adminEmployeeWrite(Employee employee) {
		// TODO Auto-generated method stub
		return adminDAO.adminEmployeeWrite(employee);
	}
	

	// 직원정보상세보기
	public Employee adminEmployeeViewModal(String employee_id) {
		// TODO Auto-generated method stub
		return adminDAO.adminEmployeeViewModal(employee_id);
	}
	// 직원정보사진파일확인
	public Employee selectimagefile(EmployeeDTO dto) {
		// TODO Auto-generated method stub
		return adminDAO.selectimagefile(dto);
	}

	// 직원정보수정
	public boolean adminEmployeeModify(EmployeeDTO dto) {
		// TODO Auto-generated method stub
		return adminDAO.adminEmployeeModify(dto);
	}
	// 직원정보삭제
	public boolean adminEmployeeDelete(String employee_id) {
		// TODO Auto-generated method stub
		return adminDAO.adminEmployeeDelete(employee_id);
	}

}
