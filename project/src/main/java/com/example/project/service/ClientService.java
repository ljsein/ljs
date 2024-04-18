package com.example.project.service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.project.dao.ClientDAO;
import com.example.project.dao.EmployeeDAO;
import com.example.project.dao.EventDAO;
import com.example.project.dao.ReservationDAO;
import com.example.project.dto.ClientDTO;
import com.example.project.entity.Client;
import com.example.project.entity.Employee;
import com.example.project.entity.Event;
import com.example.project.entity.Reserv_time;
import com.example.project.entity.Reservation;
import com.example.project.repository.ClientRepository;
import com.example.project.repository.ReservationRepository;

@Service
public class ClientService {
	@Autowired
	ClientDAO clientDAO;
	@Autowired
	EmployeeDAO employeeDAO;
	@Autowired
	ReservationDAO reservationDAO;
	@Autowired
	EventDAO eventDAO;
//클라이언트
	// 로그인
	public Client login(String client_id, String client_pwd) {
		return clientDAO.login(client_id, client_pwd);
	}

	// 회원가입
	public boolean clientInsert(ClientDTO dto) {
		return clientDAO.clientInsert(dto);

	}

	// 아이디 확인
	public boolean isExistId(String client_id) {
		return clientDAO.isExistId(client_id);
	}

	// 업데이트
	public boolean clientUpdate(ClientDTO dto) {
		return clientDAO.clientUpdate(dto);
	}

	// 회원 뷰
	public Client clientView(String client_id) {
		return clientDAO.getClient(client_id);
	}
	
//예약	
	// 예약 삭제
	public boolean clientResDelete(int reserv_seq) {
		return reservationDAO.reservationDelete(reserv_seq);
	}
	   //회원 삭제
	public boolean clientDelete(String client_id) {
	      return clientDAO.clientDelete(client_id);
	}

	// 클라이언트 예약리스트
	public List<Reservation> clientGetReservationList(String client_id) {
		return reservationDAO.clientGetReservationList(client_id);
	}

	// 삭제버튼 유무
	public boolean isDeletable(List<Reservation> list) {
		return reservationDAO.isDeletable(list);
	}
//직원
	// 직원리스트 보기
	public List<Employee> clientGetEmployeeList() {
		return employeeDAO.employeeList();

	}

	// 직원뷰 보기
	public Employee ClientGetemployeeView(String employee_id) {
		return employeeDAO.employeeView(employee_id);
	}

	public Reserv_time time_click(int time_seq) {
		// TODO Auto-generated method stub
		return reservationDAO.time_click(time_seq);
	}

}
