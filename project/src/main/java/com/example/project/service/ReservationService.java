package com.example.project.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.project.dao.ReservationDAO;
import com.example.project.dto.PayDTO;
import com.example.project.dto.Reserv_timeDTO;
import com.example.project.dto.ReservationDTO;
import com.example.project.entity.Pay;
import com.example.project.entity.Reserv_time;
import com.example.project.entity.Reservation;
import com.example.project.repository.ReservationRepository;

@Repository
public class ReservationService {
	@Autowired
	ReservationDAO reservationDAO;

	// 결제 내역 등록
	public Pay payWrite(PayDTO dto) {

		return reservationDAO.payWrite(dto);
	}

	// 예약내역 등록
	public boolean reservationWrite(ReservationDTO dto) {

		return reservationDAO.reservationWrite(dto);
	}

	public List<Reserv_time> TimeList(String time_reservdate, String time_employeeid) {
		// TODO Auto-generated method stub
		return reservationDAO.TimeList(time_reservdate, time_employeeid);
	}

	// 유저 예약 시간 선택 비활성화 활성화 변경
	public Reserv_time getReserv_time(Reserv_timeDTO reserv_timedto) {
		// TODO Auto-generated method stub
		return reservationDAO.getReserv_time(reserv_timedto);
	}

	public int reserv_count(Date reserv_date, String reserv_statename) {
		// TODO Auto-generated method stub
		return reservationDAO.reserv_count(reserv_date, reserv_statename);
	}

	public int reserv_count1(Date reserv_date, String reserv_statename) {
		// TODO Auto-generated method stub
		return reservationDAO.reserv_count1(reserv_date, reserv_statename);
	}

	public int reserv_count2(Date reserv_date, String reserv_statename) {
		// TODO Auto-generated method stub
		return reservationDAO.reserv_count(reserv_date, reserv_statename);
	}
}
