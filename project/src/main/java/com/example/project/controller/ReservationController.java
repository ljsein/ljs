package com.example.project.controller;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.project.dto.PayDTO;
import com.example.project.dto.Reserv_timeDTO;
import com.example.project.dto.ReservationDTO;
import com.example.project.entity.Client;
import com.example.project.entity.Employee;
import com.example.project.entity.Event;
import com.example.project.entity.Pay;
import com.example.project.entity.Reserv_time;
import com.example.project.service.AdminService;
import com.example.project.service.ClientService;
import com.example.project.service.EmployeeService;
import com.example.project.service.EventService;
import com.example.project.service.ReservationService;
import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ReservationController {
	@Autowired
	ReservationService service;
	@Autowired
	AdminService adminservice;
	@Autowired
	ClientService clientService;
	@Autowired
	EmployeeService employeeService;
	@Autowired
	EventService eventService;

	// 데이트 예약 등록 페이지
	@GetMapping("/reservation/reservationWriteForm")
	public String reservationWriteForm(HttpServletRequest request, Model model) {
		// 1. 데이터 처리
		HttpSession session = request.getSession();
		String employee_id = request.getParameter("employee_id");
		String client_id = (String) session.getAttribute("memId");

		Client client = clientService.clientView(client_id);
		Employee employee = employeeService.employeeView(employee_id);
		List<Reserv_time> reservation = adminservice.adminReservTimeList();

		for (int i = 0; i < reservation.size(); i++) {

			model.addAttribute("time_state1", reservation.get(i).isTime_state1());
			model.addAttribute("time_state2", reservation.get(i).isTime_state2());
			model.addAttribute("time_state3", reservation.get(i).isTime_state3());
			model.addAttribute("time_state4", reservation.get(i).isTime_state4());
			model.addAttribute("time_state5", reservation.get(i).isTime_state5());
			model.addAttribute("time_state6", reservation.get(i).isTime_state6());
			model.addAttribute("time_state7", reservation.get(i).isTime_state7());
			model.addAttribute("time_state8", reservation.get(i).isTime_state8());

		}
		// 이벤트 리스트 값 랜덤으로 가져오기
	      List<Event> list2 = eventService.eventPrint();
	      Random random = new Random();
	      int randomIndex1 = random.nextInt(list2.size());
	      Event event1 = list2.get(randomIndex1);

	      model.addAttribute("event1", event1);
	      
		model.addAttribute("client_id", client_id);
		model.addAttribute("employee_id", employee_id);
		model.addAttribute("employee_price", employee.getEmployee_price());
		model.addAttribute("client_tel", client.getClient_tel());
		model.addAttribute("memName", session.getAttribute("memName"));
		model.addAttribute("memId", session.getAttribute("memId"));
		// 3. view 처리 파일 선택
		return "/reservation/reservationWriteForm";
	}

	@GetMapping("/reservation/TimeList")
	public @ResponseBody String TimeList(Model model, HttpServletRequest request) {

		String time_reservdate = request.getParameter("time_reservdate");
		String time_employeeid = request.getParameter("time_employeeid");

		List<Reserv_time> list = service.TimeList(time_reservdate, time_employeeid);

		Gson gson = new Gson();
		String reserv_time_json = gson.toJson(list);

		return reserv_time_json;
	}

	// 결제내역 등록
	@PostMapping("/reservation/payWrite")
	public @ResponseBody Pay payWrite(PayDTO dto, Model model, HttpServletRequest request) {

		Pay result = service.payWrite(dto);

		model.addAttribute("result", result);

		return result;

	}

	// 데이트 예약 등록
	@PostMapping("/reservation/reservationWrite")
	public String reservationWrite(ReservationDTO dto, Model model, HttpServletRequest request) {
		boolean result = false;

		Reserv_timeDTO reserv_timedto = new Reserv_timeDTO();
		int count = 0;
		


		int time_count1 = service.reserv_count(dto.getReserv_date(),dto.getReserv_statename()); 
		int time_count2 = service.reserv_count1(dto.getReserv_date(),dto.getReserv_statename()); 
		int time_count3 = service.reserv_count2(dto.getReserv_date(),dto.getReserv_statename()); 
		
		count = time_count1+time_count2+time_count3;
		
		reserv_timedto.setTime_employeeid(dto.getEmployee_id());
		reserv_timedto.setTime_reservdate(dto.getReserv_date());
		dto.setReserv_state(1);
		dto.setReserv_statename("예약완료");
		

		if(dto.getReserv_time1() == null) {
			
			dto.setReserv_time1("");
		}
		
		if(dto.getReserv_time2() == null) {
			
			dto.setReserv_time2("");
		}
		if(dto.getReserv_time3() == null) {
			
			dto.setReserv_time3("");
		}

		
		if(count == 0) {
		if(!dto.getReserv_time1().equals("")
			&& !dto.getReserv_time2().equals("")
			&& !dto.getReserv_time3().equals("")) {	
			
			if (dto.getReserv_time1().equals("09:00 ~ 12:00")
					&& dto.getReserv_time2().equals("12:00 ~ 15:00")
					&&dto.getReserv_time3().equals("15:00 ~ 18:00")) {
							reserv_timedto.setTime_state1(true);
							reserv_timedto.setTime_state2(true);
							reserv_timedto.setTime_state3(true);
							reserv_timedto.setTime_state4(true);
							reserv_timedto.setTime_state5(true);
							reserv_timedto.setTime_state6(true);
							reserv_timedto.setTime_state7(true);
							reserv_timedto.setTime_state8(true);

					}else if(dto.getReserv_time1().equals("12:00 ~ 15:00")
							&& dto.getReserv_time2().equals("18:00 ~ 21:00")
							&&dto.getReserv_time3().equals("21:00 ~ 00:00")) {
									reserv_timedto.setTime_state1(true);
									reserv_timedto.setTime_state2(true);
									reserv_timedto.setTime_state3(true);
									reserv_timedto.setTime_state4(true);
									reserv_timedto.setTime_state5(true);
									reserv_timedto.setTime_state6(true);
									reserv_timedto.setTime_state7(true);
									reserv_timedto.setTime_state8(true);

					}else if(dto.getReserv_time1().equals("18:00 ~ 21:00")
							&& dto.getReserv_time2().equals("21:00 ~ 00:00")
							&&dto.getReserv_time3().equals("00:00 ~ 03:00")) {
									reserv_timedto.setTime_state1(true);
									reserv_timedto.setTime_state2(true);
									reserv_timedto.setTime_state3(true);
									reserv_timedto.setTime_state4(true);
									reserv_timedto.setTime_state5(true);
									reserv_timedto.setTime_state6(true);
									reserv_timedto.setTime_state7(true);
									reserv_timedto.setTime_state8(true);

					}else if(dto.getReserv_time1().equals("21:00 ~ 00:00")
							&& dto.getReserv_time2().equals("00:00 ~ 03:00")
							&&dto.getReserv_time3().equals("03:00 ~ 06:00")) {
									reserv_timedto.setTime_state1(true);
									reserv_timedto.setTime_state2(true);
									reserv_timedto.setTime_state3(true);
									reserv_timedto.setTime_state4(true);
									reserv_timedto.setTime_state5(true);
									reserv_timedto.setTime_state6(true);
									reserv_timedto.setTime_state7(true);
									reserv_timedto.setTime_state8(true);

					}else if(dto.getReserv_time1().equals("00:00 ~ 06:00")
							&& dto.getReserv_time2().equals("06:00 ~ 09:00")
							&&dto.getReserv_time3().equals("09:00 ~ 12:00")) {
									reserv_timedto.setTime_state1(true);
									reserv_timedto.setTime_state2(true);
									reserv_timedto.setTime_state3(true);
									reserv_timedto.setTime_state4(true);
									reserv_timedto.setTime_state5(true);
									reserv_timedto.setTime_state6(true);
									reserv_timedto.setTime_state7(true);
									reserv_timedto.setTime_state8(true);

					}
					//////////////////////////////////////////////////////
					if (dto.getReserv_time1().equals("09:00 ~ 12:00")
					&& dto.getReserv_time3().equals("12:00 ~ 15:00")
					&&dto.getReserv_time2().equals("15:00 ~ 18:00")) {
							reserv_timedto.setTime_state1(true);
							reserv_timedto.setTime_state2(true);
							reserv_timedto.setTime_state3(true);
							reserv_timedto.setTime_state4(true);
							reserv_timedto.setTime_state5(true);
							reserv_timedto.setTime_state6(true);
							reserv_timedto.setTime_state7(true);
							reserv_timedto.setTime_state8(true);

					}else if(dto.getReserv_time1().equals("12:00 ~ 15:00")
							&& dto.getReserv_time3().equals("18:00 ~ 21:00")
							&&dto.getReserv_time2().equals("21:00 ~ 00:00")) {
									reserv_timedto.setTime_state1(true);
									reserv_timedto.setTime_state2(true);
									reserv_timedto.setTime_state3(true);
									reserv_timedto.setTime_state4(true);
									reserv_timedto.setTime_state5(true);
									reserv_timedto.setTime_state6(true);
									reserv_timedto.setTime_state7(true);
									reserv_timedto.setTime_state8(true);

					}else if(dto.getReserv_time1().equals("18:00 ~ 21:00")
							&& dto.getReserv_time3().equals("21:00 ~ 00:00")
							&&dto.getReserv_time2().equals("00:00 ~ 03:00")) {
									reserv_timedto.setTime_state1(true);
									reserv_timedto.setTime_state2(true);
									reserv_timedto.setTime_state3(true);
									reserv_timedto.setTime_state4(true);
									reserv_timedto.setTime_state5(true);
									reserv_timedto.setTime_state6(true);
									reserv_timedto.setTime_state7(true);
									reserv_timedto.setTime_state8(true);

					}else if(dto.getReserv_time1().equals("21:00 ~ 00:00")
							&& dto.getReserv_time3().equals("00:00 ~ 03:00")
							&&dto.getReserv_time2().equals("03:00 ~ 06:00")) {
									reserv_timedto.setTime_state1(true);
									reserv_timedto.setTime_state2(true);
									reserv_timedto.setTime_state3(true);
									reserv_timedto.setTime_state4(true);
									reserv_timedto.setTime_state5(true);
									reserv_timedto.setTime_state6(true);
									reserv_timedto.setTime_state7(true);
									reserv_timedto.setTime_state8(true);

					}else if(dto.getReserv_time1().equals("00:00 ~ 06:00")
							&& dto.getReserv_time3().equals("06:00 ~ 09:00")
							&&dto.getReserv_time2().equals("09:00 ~ 12:00")) {
									reserv_timedto.setTime_state1(true);
									reserv_timedto.setTime_state2(true);
									reserv_timedto.setTime_state3(true);
									reserv_timedto.setTime_state4(true);
									reserv_timedto.setTime_state5(true);
									reserv_timedto.setTime_state6(true);
									reserv_timedto.setTime_state7(true);
									reserv_timedto.setTime_state8(true);

					}
					//////////////////////////////////////////////////////
					if (dto.getReserv_time2().equals("09:00 ~ 12:00")
					&& dto.getReserv_time1().equals("12:00 ~ 15:00")
					&&dto.getReserv_time3().equals("15:00 ~ 18:00")) {
							reserv_timedto.setTime_state1(true);
							reserv_timedto.setTime_state2(true);
							reserv_timedto.setTime_state3(true);
							reserv_timedto.setTime_state4(true);
							reserv_timedto.setTime_state5(true);
							reserv_timedto.setTime_state6(true);
							reserv_timedto.setTime_state7(true);
							reserv_timedto.setTime_state8(true);

					}else if(dto.getReserv_time2().equals("12:00 ~ 15:00")
							&& dto.getReserv_time1().equals("18:00 ~ 21:00")
							&&dto.getReserv_time3().equals("21:00 ~ 00:00")) {
									reserv_timedto.setTime_state1(true);
									reserv_timedto.setTime_state2(true);
									reserv_timedto.setTime_state3(true);
									reserv_timedto.setTime_state4(true);
									reserv_timedto.setTime_state5(true);
									reserv_timedto.setTime_state6(true);
									reserv_timedto.setTime_state7(true);
									reserv_timedto.setTime_state8(true);

					}else if(dto.getReserv_time2().equals("18:00 ~ 21:00")
							&& dto.getReserv_time1().equals("21:00 ~ 00:00")
							&&dto.getReserv_time3().equals("00:00 ~ 03:00")) {
									reserv_timedto.setTime_state1(true);
									reserv_timedto.setTime_state2(true);
									reserv_timedto.setTime_state3(true);
									reserv_timedto.setTime_state4(true);
									reserv_timedto.setTime_state5(true);
									reserv_timedto.setTime_state6(true);
									reserv_timedto.setTime_state7(true);
									reserv_timedto.setTime_state8(true);

					}else if(dto.getReserv_time2().equals("21:00 ~ 00:00")
							&& dto.getReserv_time1().equals("00:00 ~ 03:00")
							&&dto.getReserv_time3().equals("03:00 ~ 06:00")) {
									reserv_timedto.setTime_state1(true);
									reserv_timedto.setTime_state2(true);
									reserv_timedto.setTime_state3(true);
									reserv_timedto.setTime_state4(true);
									reserv_timedto.setTime_state5(true);
									reserv_timedto.setTime_state6(true);
									reserv_timedto.setTime_state7(true);
									reserv_timedto.setTime_state8(true);

					}else if(dto.getReserv_time2().equals("00:00 ~ 06:00")
							&& dto.getReserv_time1().equals("06:00 ~ 09:00")
							&&dto.getReserv_time3().equals("09:00 ~ 12:00")) {
									reserv_timedto.setTime_state1(true);
									reserv_timedto.setTime_state2(true);
									reserv_timedto.setTime_state3(true);
									reserv_timedto.setTime_state4(true);
									reserv_timedto.setTime_state5(true);
									reserv_timedto.setTime_state6(true);
									reserv_timedto.setTime_state7(true);
									reserv_timedto.setTime_state8(true);

					}
					/////////////////////////////////////////////////////
					if (dto.getReserv_time2().equals("09:00 ~ 12:00")
					&& dto.getReserv_time3().equals("12:00 ~ 15:00")
					&&dto.getReserv_time1().equals("15:00 ~ 18:00")) {
							reserv_timedto.setTime_state1(true);
							reserv_timedto.setTime_state2(true);
							reserv_timedto.setTime_state3(true);
							reserv_timedto.setTime_state4(true);
							reserv_timedto.setTime_state5(true);
							reserv_timedto.setTime_state6(true);
							reserv_timedto.setTime_state7(true);
							reserv_timedto.setTime_state8(true);

					}else if(dto.getReserv_time2().equals("12:00 ~ 15:00")
							&& dto.getReserv_time3().equals("18:00 ~ 21:00")
							&&dto.getReserv_time1().equals("21:00 ~ 00:00")) {
									reserv_timedto.setTime_state1(true);
									reserv_timedto.setTime_state2(true);
									reserv_timedto.setTime_state3(true);
									reserv_timedto.setTime_state4(true);
									reserv_timedto.setTime_state5(true);
									reserv_timedto.setTime_state6(true);
									reserv_timedto.setTime_state7(true);
									reserv_timedto.setTime_state8(true);

					}else if(dto.getReserv_time2().equals("18:00 ~ 21:00")
							&& dto.getReserv_time3().equals("21:00 ~ 00:00")
							&&dto.getReserv_time1().equals("00:00 ~ 03:00")) {
									reserv_timedto.setTime_state1(true);
									reserv_timedto.setTime_state2(true);
									reserv_timedto.setTime_state3(true);
									reserv_timedto.setTime_state4(true);
									reserv_timedto.setTime_state5(true);
									reserv_timedto.setTime_state6(true);
									reserv_timedto.setTime_state7(true);
									reserv_timedto.setTime_state8(true);

					}else if(dto.getReserv_time2().equals("21:00 ~ 00:00")
							&& dto.getReserv_time3().equals("00:00 ~ 03:00")
							&&dto.getReserv_time1().equals("03:00 ~ 06:00")) {
									reserv_timedto.setTime_state1(true);
									reserv_timedto.setTime_state2(true);
									reserv_timedto.setTime_state3(true);
									reserv_timedto.setTime_state4(true);
									reserv_timedto.setTime_state5(true);
									reserv_timedto.setTime_state6(true);
									reserv_timedto.setTime_state7(true);
									reserv_timedto.setTime_state8(true);

					}else if(dto.getReserv_time2().equals("00:00 ~ 06:00")
							&& dto.getReserv_time3().equals("06:00 ~ 09:00")
							&&dto.getReserv_time1().equals("09:00 ~ 12:00")) {
									reserv_timedto.setTime_state1(true);
									reserv_timedto.setTime_state2(true);
									reserv_timedto.setTime_state3(true);
									reserv_timedto.setTime_state4(true);
									reserv_timedto.setTime_state5(true);
									reserv_timedto.setTime_state6(true);
									reserv_timedto.setTime_state7(true);
									reserv_timedto.setTime_state8(true);

					}
					////////////////////////////////////////////////////
					if (dto.getReserv_time3().equals("09:00 ~ 12:00")
					&& dto.getReserv_time1().equals("12:00 ~ 15:00")
					&&dto.getReserv_time2().equals("15:00 ~ 18:00")) {
							reserv_timedto.setTime_state1(true);
							reserv_timedto.setTime_state2(true);
							reserv_timedto.setTime_state3(true);
							reserv_timedto.setTime_state4(true);
							reserv_timedto.setTime_state5(true);
							reserv_timedto.setTime_state6(true);
							reserv_timedto.setTime_state7(true);
							reserv_timedto.setTime_state8(true);

					}else if(dto.getReserv_time3().equals("12:00 ~ 15:00")
							&& dto.getReserv_time1().equals("18:00 ~ 21:00")
							&&dto.getReserv_time2().equals("21:00 ~ 00:00")) {
									reserv_timedto.setTime_state1(true);
									reserv_timedto.setTime_state2(true);
									reserv_timedto.setTime_state3(true);
									reserv_timedto.setTime_state4(true);
									reserv_timedto.setTime_state5(true);
									reserv_timedto.setTime_state6(true);
									reserv_timedto.setTime_state7(true);
									reserv_timedto.setTime_state8(true);

					}else if(dto.getReserv_time3().equals("18:00 ~ 21:00")
							&& dto.getReserv_time1().equals("21:00 ~ 00:00")
							&&dto.getReserv_time2().equals("00:00 ~ 03:00")) {
									reserv_timedto.setTime_state1(true);
									reserv_timedto.setTime_state2(true);
									reserv_timedto.setTime_state3(true);
									reserv_timedto.setTime_state4(true);
									reserv_timedto.setTime_state5(true);
									reserv_timedto.setTime_state6(true);
									reserv_timedto.setTime_state7(true);
									reserv_timedto.setTime_state8(true);

					}else if(dto.getReserv_time3().equals("21:00 ~ 00:00")
							&& dto.getReserv_time1().equals("00:00 ~ 03:00")
							&&dto.getReserv_time2().equals("03:00 ~ 06:00")) {
									reserv_timedto.setTime_state1(true);
									reserv_timedto.setTime_state2(true);
									reserv_timedto.setTime_state3(true);
									reserv_timedto.setTime_state4(true);
									reserv_timedto.setTime_state5(true);
									reserv_timedto.setTime_state6(true);
									reserv_timedto.setTime_state7(true);
									reserv_timedto.setTime_state8(true);

					}else if(dto.getReserv_time3().equals("00:00 ~ 06:00")
							&& dto.getReserv_time1().equals("06:00 ~ 09:00")
							&&dto.getReserv_time2().equals("09:00 ~ 12:00")) {
									reserv_timedto.setTime_state1(true);
									reserv_timedto.setTime_state2(true);
									reserv_timedto.setTime_state3(true);
									reserv_timedto.setTime_state4(true);
									reserv_timedto.setTime_state5(true);
									reserv_timedto.setTime_state6(true);
									reserv_timedto.setTime_state7(true);
									reserv_timedto.setTime_state8(true);

					}
					///////////////////////////////////////////////////
					if (dto.getReserv_time3().equals("09:00 ~ 12:00")
					&& dto.getReserv_time2().equals("12:00 ~ 15:00")
					&&dto.getReserv_time1().equals("15:00 ~ 18:00")) {
							reserv_timedto.setTime_state1(true);
							reserv_timedto.setTime_state2(true);
							reserv_timedto.setTime_state3(true);
							reserv_timedto.setTime_state4(true);
							reserv_timedto.setTime_state5(true);
							reserv_timedto.setTime_state6(true);
							reserv_timedto.setTime_state7(true);
							reserv_timedto.setTime_state8(true);

					}else if(dto.getReserv_time3().equals("12:00 ~ 15:00")
							&& dto.getReserv_time2().equals("18:00 ~ 21:00")
							&&dto.getReserv_time1().equals("21:00 ~ 00:00")) {
									reserv_timedto.setTime_state1(true);
									reserv_timedto.setTime_state2(true);
									reserv_timedto.setTime_state3(true);
									reserv_timedto.setTime_state4(true);
									reserv_timedto.setTime_state5(true);
									reserv_timedto.setTime_state6(true);
									reserv_timedto.setTime_state7(true);
									reserv_timedto.setTime_state8(true);

					}else if(dto.getReserv_time3().equals("18:00 ~ 21:00")
							&& dto.getReserv_time2().equals("21:00 ~ 00:00")
							&&dto.getReserv_time1().equals("00:00 ~ 03:00")) {
									reserv_timedto.setTime_state1(true);
									reserv_timedto.setTime_state2(true);
									reserv_timedto.setTime_state3(true);
									reserv_timedto.setTime_state4(true);
									reserv_timedto.setTime_state5(true);
									reserv_timedto.setTime_state6(true);
									reserv_timedto.setTime_state7(true);
									reserv_timedto.setTime_state8(true);

					}else if(dto.getReserv_time3().equals("21:00 ~ 00:00")
							&& dto.getReserv_time2().equals("00:00 ~ 03:00")
							&&dto.getReserv_time1().equals("03:00 ~ 06:00")) {
									reserv_timedto.setTime_state1(true);
									reserv_timedto.setTime_state2(true);
									reserv_timedto.setTime_state3(true);
									reserv_timedto.setTime_state4(true);
									reserv_timedto.setTime_state5(true);
									reserv_timedto.setTime_state6(true);
									reserv_timedto.setTime_state7(true);
									reserv_timedto.setTime_state8(true);

					}else if(dto.getReserv_time3().equals("00:00 ~ 06:00")
							&& dto.getReserv_time2().equals("06:00 ~ 09:00")
							&&dto.getReserv_time1().equals("09:00 ~ 12:00")) {
									reserv_timedto.setTime_state1(true);
									reserv_timedto.setTime_state2(true);
									reserv_timedto.setTime_state3(true);
									reserv_timedto.setTime_state4(true);
									reserv_timedto.setTime_state5(true);
									reserv_timedto.setTime_state6(true);
									reserv_timedto.setTime_state7(true);
									reserv_timedto.setTime_state8(true);

					}
			
			
		}else {
		if(dto.getReserv_time1().equals("09:00 ~ 12:00"))	{
			reserv_timedto.setTime_state1(true);
			reserv_timedto.setTime_state2(true);
			
			
		}else if(dto.getReserv_time1().equals("12:00 ~ 15:00")) {
			reserv_timedto.setTime_state2(true);
			reserv_timedto.setTime_state3(true);
			
		}else if(dto.getReserv_time1().equals("15:00 ~ 18:00")) {
			reserv_timedto.setTime_state3(true);
			reserv_timedto.setTime_state4(true);
			
		}else if(dto.getReserv_time1().equals("18:00 ~ 21:00")) {
			reserv_timedto.setTime_state4(true);
			reserv_timedto.setTime_state5(true);
			
		}else if(dto.getReserv_time1().equals("21:00 ~ 00:00")) {
			reserv_timedto.setTime_state5(true);
			reserv_timedto.setTime_state6(true);
			
		}else if(dto.getReserv_time1().equals("00:00 ~ 03:00")) {
			reserv_timedto.setTime_state6(true);
			reserv_timedto.setTime_state7(true);
			
		}else if(dto.getReserv_time1().equals("03:00 ~ 06:00")) {
			reserv_timedto.setTime_state7(true);
			reserv_timedto.setTime_state8(true);
			
		}else if(dto.getReserv_time1().equals("06:00 ~ 09:00")) {
			reserv_timedto.setTime_state8(true);
			reserv_timedto.setTime_state1(true);
			
		}
		
		if(dto.getReserv_time2().equals("09:00 ~ 12:00"))	{
			reserv_timedto.setTime_state1(true);
			reserv_timedto.setTime_state2(true);
			
			
		}else if(dto.getReserv_time2().equals("12:00 ~ 15:00")) {
			reserv_timedto.setTime_state2(true);
			reserv_timedto.setTime_state3(true);
			
		}else if(dto.getReserv_time2().equals("15:00 ~ 18:00")) {
			reserv_timedto.setTime_state3(true);
			reserv_timedto.setTime_state4(true);
			
		}else if(dto.getReserv_time2().equals("18:00 ~ 21:00")) {
			reserv_timedto.setTime_state4(true);
			reserv_timedto.setTime_state5(true);
			
		}else if(dto.getReserv_time2().equals("21:00 ~ 00:00")) {
			reserv_timedto.setTime_state5(true);
			reserv_timedto.setTime_state6(true);
			
		}else if(dto.getReserv_time2().equals("00:00 ~ 03:00")) {
			reserv_timedto.setTime_state6(true);
			reserv_timedto.setTime_state7(true);
			
		}else if(dto.getReserv_time2().equals("03:00 ~ 06:00")) {
			reserv_timedto.setTime_state7(true);
			reserv_timedto.setTime_state8(true);
			
		}else if(dto.getReserv_time2().equals("06:00 ~ 09:00")) {
			reserv_timedto.setTime_state8(true);
			reserv_timedto.setTime_state1(true);
			
		}
		
		if(dto.getReserv_time3().equals("09:00 ~ 12:00"))	{
			reserv_timedto.setTime_state1(true);
			reserv_timedto.setTime_state2(true);
			
			
		}else if(dto.getReserv_time3().equals("12:00 ~ 15:00")) {
			reserv_timedto.setTime_state2(true);
			reserv_timedto.setTime_state3(true);
			
		}else if(dto.getReserv_time3().equals("15:00 ~ 18:00")) {
			reserv_timedto.setTime_state3(true);
			reserv_timedto.setTime_state4(true);
			
		}else if(dto.getReserv_time3().equals("18:00 ~ 21:00")) {
			reserv_timedto.setTime_state4(true);
			reserv_timedto.setTime_state5(true);
			
		}else if(dto.getReserv_time3().equals("21:00 ~ 00:00")) {
			reserv_timedto.setTime_state5(true);
			reserv_timedto.setTime_state6(true);
			
		}else if(dto.getReserv_time3().equals("00:00 ~ 03:00")) {
			reserv_timedto.setTime_state6(true);
			reserv_timedto.setTime_state7(true);
			
		}else if(dto.getReserv_time3().equals("03:00 ~ 06:00")) {
			reserv_timedto.setTime_state7(true);
			reserv_timedto.setTime_state8(true);
			
		}else if(dto.getReserv_time3().equals("06:00 ~ 09:00")) {
			reserv_timedto.setTime_state8(true);
			reserv_timedto.setTime_state1(true);
			
		}
		
		
		///////////////////////////////////////////////////
		if (dto.getReserv_time1().equals("09:00 ~ 12:00") 
		&& dto.getReserv_time2().equals("")
		&& dto.getReserv_time3().equals("")) {

			reserv_timedto.setTime_state1(true);
			reserv_timedto.setTime_state2(true);

		} else if (dto.getReserv_time1().equals("12:00 ~ 15:00")
				&& dto.getReserv_time2().equals("")
				&& dto.getReserv_time3().equals("")) {

			reserv_timedto.setTime_state2(true);
			reserv_timedto.setTime_state3(true);

		} else if (dto.getReserv_time1().equals("15:00 ~ 18:00")
				&& dto.getReserv_time2().equals("")
				&& dto.getReserv_time3().equals("")) {

			reserv_timedto.setTime_state3(true);
			reserv_timedto.setTime_state4(true);

		} else if (dto.getReserv_time1().equals("18:00 ~ 21:00")
				&& dto.getReserv_time2().equals("")
				&& dto.getReserv_time3().equals("")) {

			reserv_timedto.setTime_state4(true);
			reserv_timedto.setTime_state5(true);

		} else if (dto.getReserv_time1().equals("21:00 ~ 00:00")
				&& dto.getReserv_time2().equals("")
				&& dto.getReserv_time3().equals("")) {

			reserv_timedto.setTime_state5(true);
			reserv_timedto.setTime_state6(true);

		} else if (dto.getReserv_time1().equals("00:00 ~ 03:00")
				&& dto.getReserv_time2().equals("")
				&& dto.getReserv_time3().equals("")) {

			reserv_timedto.setTime_state6(true);
			reserv_timedto.setTime_state7(true);

		} else if (dto.getReserv_time1().equals("03:00 ~ 06:00")
				&& dto.getReserv_time2().equals("")
				&& dto.getReserv_time3().equals("")) {

			reserv_timedto.setTime_state7(true);
			reserv_timedto.setTime_state8(true);

		} else if (dto.getReserv_time1().equals("06:00 ~ 09:00")
				&& dto.getReserv_time2().equals("")
				&& dto.getReserv_time3().equals("")) {

			reserv_timedto.setTime_state8(true);
			reserv_timedto.setTime_state1(true);

		}
		
		//////////////////////////////////////////////
		if (dto.getReserv_time1().equals("") 
		&& dto.getReserv_time2().equals("09:00 ~ 12:00")
		&& dto.getReserv_time3().equals("")) {

			reserv_timedto.setTime_state1(true);
			reserv_timedto.setTime_state2(true);

		} else if (dto.getReserv_time1().equals("")
				&& dto.getReserv_time2().equals("12:00 ~ 15:00")
				&& dto.getReserv_time3().equals("")) {

			reserv_timedto.setTime_state2(true);
			reserv_timedto.setTime_state3(true);

		} else if (dto.getReserv_time1().equals("")
				&& dto.getReserv_time2().equals("15:00 ~ 18:00")
				&& dto.getReserv_time3().equals("")) {

			reserv_timedto.setTime_state3(true);
			reserv_timedto.setTime_state4(true);

		} else if (dto.getReserv_time1().equals("")
				&& dto.getReserv_time2().equals("18:00 ~ 21:00")
				&& dto.getReserv_time3().equals("")) {

			reserv_timedto.setTime_state4(true);
			reserv_timedto.setTime_state5(true);

		} else if (dto.getReserv_time1().equals("")
				&& dto.getReserv_time2().equals("21:00 ~ 00:00")
				&& dto.getReserv_time3().equals("")) {

			reserv_timedto.setTime_state5(true);
			reserv_timedto.setTime_state6(true);

		} else if (dto.getReserv_time1().equals("")
				&& dto.getReserv_time2().equals("21:00 ~ 00:00")
				&& dto.getReserv_time3().equals("")) {

			reserv_timedto.setTime_state6(true);
			reserv_timedto.setTime_state7(true);

		} else if (dto.getReserv_time1().equals("")
				&& dto.getReserv_time2().equals("03:00 ~ 06:00")
				&& dto.getReserv_time3().equals("")) {

			reserv_timedto.setTime_state7(true);
			reserv_timedto.setTime_state8(true);

		} else if (dto.getReserv_time1().equals("")
				&& dto.getReserv_time2().equals("06:00 ~ 09:00")
				&& dto.getReserv_time3().equals("")) {

			reserv_timedto.setTime_state8(true);
			reserv_timedto.setTime_state1(true);

		}
		
		
		////////////////////////////////////////////

		if (dto.getReserv_time1().equals("") 
		&& dto.getReserv_time2().equals("")
		&& dto.getReserv_time3().equals("09:00 ~ 12:00")) {

			reserv_timedto.setTime_state1(true);
			reserv_timedto.setTime_state2(true);

		} else if (dto.getReserv_time1().equals("")
				&& dto.getReserv_time2().equals("")
				&& dto.getReserv_time3().equals("12:00 ~ 15:00")) {

			reserv_timedto.setTime_state2(true);
			reserv_timedto.setTime_state3(true);

		} else if (dto.getReserv_time1().equals("")
				&& dto.getReserv_time2().equals("")
				&& dto.getReserv_time3().equals("15:00 ~ 18:00")) {

			reserv_timedto.setTime_state3(true);
			reserv_timedto.setTime_state4(true);

		} else if (dto.getReserv_time1().equals("")
				&& dto.getReserv_time2().equals("")
				&& dto.getReserv_time3().equals("18:00 ~ 21:00")) {

			reserv_timedto.setTime_state4(true);
			reserv_timedto.setTime_state5(true);

		} else if (dto.getReserv_time1().equals("")
				&& dto.getReserv_time2().equals("")
				&& dto.getReserv_time3().equals("21:00 ~ 00:00")) {

			reserv_timedto.setTime_state5(true);
			reserv_timedto.setTime_state6(true);

		} else if (dto.getReserv_time1().equals("")
				&& dto.getReserv_time2().equals("")
				&& dto.getReserv_time3().equals("21:00 ~ 00:00")) {

			reserv_timedto.setTime_state6(true);
			reserv_timedto.setTime_state7(true);

		} else if (dto.getReserv_time1().equals("")
				&& dto.getReserv_time2().equals("")
				&& dto.getReserv_time3().equals("03:00 ~ 06:00")) {

			reserv_timedto.setTime_state7(true);
			reserv_timedto.setTime_state8(true);

		} else if (dto.getReserv_time1().equals("")
				&& dto.getReserv_time2().equals("")
				&& dto.getReserv_time3().equals("06:00 ~ 09:00")) {

			reserv_timedto.setTime_state8(true);
			reserv_timedto.setTime_state1(true);

		}
		
		////////////////////////////////////////////////
		if (dto.getReserv_time1().equals("09:00 ~ 12:00")
				&& dto.getReserv_time2().equals("12:00 ~ 15:00")
				&& dto.getReserv_time3().equals("")) {

			reserv_timedto.setTime_state1(true);
			reserv_timedto.setTime_state2(true);
			reserv_timedto.setTime_state3(true);

		} else if (dto.getReserv_time1().equals("12:00 ~ 15:00")
				&& dto.getReserv_time2().equals("15:00 ~ 18:00")
				&& dto.getReserv_time3().equals("")) {

			reserv_timedto.setTime_state2(true);
			reserv_timedto.setTime_state3(true);
			reserv_timedto.setTime_state4(true);

		} else if (dto.getReserv_time1().equals("15:00 ~ 18:00")
				&& dto.getReserv_time2().equals("18:00 ~ 21:00")
				&& dto.getReserv_time3().equals("")) {

			reserv_timedto.setTime_state3(true);
			reserv_timedto.setTime_state4(true);
			reserv_timedto.setTime_state5(true);
		} else if (dto.getReserv_time1().equals("18:00 ~ 21:00")
				&& dto.getReserv_time2().equals("21:00 ~ 00:00")
				&& dto.getReserv_time3().equals("")) {

			reserv_timedto.setTime_state4(true);
			reserv_timedto.setTime_state5(true);
			reserv_timedto.setTime_state6(true);

		} else if (dto.getReserv_time1().equals("21:00 ~ 00:00")
				&& dto.getReserv_time2().equals("00:00 ~ 03:00")
				&& dto.getReserv_time3().equals("")) {

			reserv_timedto.setTime_state5(true);
			reserv_timedto.setTime_state6(true);
			reserv_timedto.setTime_state7(true);

		} else if (dto.getReserv_time1().equals("00:00 ~ 03:00")
				&& dto.getReserv_time2().equals("03:00 ~ 06:00")
				&& dto.getReserv_time3().equals("")) {

			reserv_timedto.setTime_state6(true);
			reserv_timedto.setTime_state7(true);
			reserv_timedto.setTime_state8(true);

		} else if (dto.getReserv_time1().equals("03:00 ~ 06:00")
				&& dto.getReserv_time2().equals("06:00 ~ 09:00")
				&& dto.getReserv_time3().equals("")) {

			reserv_timedto.setTime_state7(true);
			reserv_timedto.setTime_state8(true);
			reserv_timedto.setTime_state1(true);

		} else if (dto.getReserv_time1().equals("06:00 ~ 09:00")
				&& dto.getReserv_time2().equals("09:00 ~ 12:00")
				&& dto.getReserv_time3().equals("")) {

			reserv_timedto.setTime_state8(true);
			reserv_timedto.setTime_state1(true);
			reserv_timedto.setTime_state2(true);

		}
		

		if (dto.getReserv_time1().equals("09:00 ~ 12:00")
		&& dto.getReserv_time2().equals("")
		&& dto.getReserv_time3().equals("12:00 ~ 15:00")) {

			reserv_timedto.setTime_state1(true);
			reserv_timedto.setTime_state2(true);
			reserv_timedto.setTime_state3(true);

		} else if (dto.getReserv_time1().equals("12:00 ~ 15:00")
				&& dto.getReserv_time2().equals("")
				&& dto.getReserv_time3().equals("15:00 ~ 18:00")) {

			reserv_timedto.setTime_state2(true);
			reserv_timedto.setTime_state3(true);
			reserv_timedto.setTime_state4(true);

		} else if (dto.getReserv_time1().equals("15:00 ~ 18:00")
				&& dto.getReserv_time2().equals("")
				&& dto.getReserv_time3().equals("18:00 ~ 21:00")) {

			reserv_timedto.setTime_state3(true);
			reserv_timedto.setTime_state4(true);
			reserv_timedto.setTime_state5(true);
		} else if (dto.getReserv_time1().equals("18:00 ~ 21:00")
				&& dto.getReserv_time2().equals("")
				&& dto.getReserv_time3().equals("21:00 ~ 00:00")) {

			reserv_timedto.setTime_state4(true);
			reserv_timedto.setTime_state5(true);
			reserv_timedto.setTime_state6(true);

		} else if (dto.getReserv_time1().equals("21:00 ~ 00:00")
				&& dto.getReserv_time2().equals("")
				&& dto.getReserv_time3().equals("00:00 ~ 03:00")) {

			reserv_timedto.setTime_state5(true);
			reserv_timedto.setTime_state6(true);
			reserv_timedto.setTime_state7(true);

		} else if (dto.getReserv_time1().equals("00:00 ~ 03:00")
				&& dto.getReserv_time2().equals("")
				&& dto.getReserv_time3().equals("03:00 ~ 06:00")) {

			reserv_timedto.setTime_state6(true);
			reserv_timedto.setTime_state7(true);
			reserv_timedto.setTime_state8(true);

		} else if (dto.getReserv_time1().equals("03:00 ~ 06:00")
				&& dto.getReserv_time2().equals("")
				&& dto.getReserv_time3().equals("06:00 ~ 09:00")) {

			reserv_timedto.setTime_state7(true);
			reserv_timedto.setTime_state8(true);
			reserv_timedto.setTime_state1(true);

		} else if (dto.getReserv_time1().equals("06:00 ~ 09:00")
				&& dto.getReserv_time2().equals("")
				&& dto.getReserv_time3().equals("09:00 ~ 12:00")) {

			reserv_timedto.setTime_state8(true);
			reserv_timedto.setTime_state1(true);
			reserv_timedto.setTime_state2(true);

		}

		if (dto.getReserv_time2().equals("09:00 ~ 12:00")
				&& dto.getReserv_time1().equals("12:00 ~ 15:00")
				&& dto.getReserv_time3().equals("")) {

			reserv_timedto.setTime_state1(true);
			reserv_timedto.setTime_state2(true);
			reserv_timedto.setTime_state3(true);

		} else if (dto.getReserv_time2().equals("12:00 ~ 15:00")
				&& dto.getReserv_time1().equals("15:00 ~ 18:00")
				&& dto.getReserv_time3().equals("")) {

			reserv_timedto.setTime_state2(true);
			reserv_timedto.setTime_state3(true);
			reserv_timedto.setTime_state4(true);

		} else if (dto.getReserv_time2().equals("15:00 ~ 18:00")
				&& dto.getReserv_time1().equals("18:00 ~ 21:00")
				&& dto.getReserv_time3().equals("")) {

			reserv_timedto.setTime_state3(true);
			reserv_timedto.setTime_state4(true);
			reserv_timedto.setTime_state5(true);
		} else if (dto.getReserv_time2().equals("18:00 ~ 21:00")
				&& dto.getReserv_time1().equals("21:00 ~ 00:00")
				&& dto.getReserv_time3().equals("")) {

			reserv_timedto.setTime_state4(true);
			reserv_timedto.setTime_state5(true);
			reserv_timedto.setTime_state6(true);

		} else if (dto.getReserv_time2().equals("21:00 ~ 00:00")
				&& dto.getReserv_time1().equals("00:00 ~ 03:00")
				&& dto.getReserv_time3().equals("")) {

			reserv_timedto.setTime_state5(true);
			reserv_timedto.setTime_state6(true);
			reserv_timedto.setTime_state7(true);

		} else if (dto.getReserv_time2().equals("00:00 ~ 03:00")
				&& dto.getReserv_time1().equals("03:00 ~ 06:00")
				&& dto.getReserv_time3().equals("")) {

			reserv_timedto.setTime_state6(true);
			reserv_timedto.setTime_state7(true);
			reserv_timedto.setTime_state8(true);

		} else if (dto.getReserv_time2().equals("03:00 ~ 06:00")
				&& dto.getReserv_time1().equals("06:00 ~ 09:00")
				&& dto.getReserv_time3().equals("")) {

			reserv_timedto.setTime_state7(true);
			reserv_timedto.setTime_state8(true);
			reserv_timedto.setTime_state1(true);

		} else if (dto.getReserv_time2().equals("06:00 ~ 09:00")
				&& dto.getReserv_time1().equals("09:00 ~ 12:00")
				&& dto.getReserv_time3().equals("")) {

			reserv_timedto.setTime_state8(true);
			reserv_timedto.setTime_state1(true);
			reserv_timedto.setTime_state2(true);

		}
		if (dto.getReserv_time3().equals("09:00 ~ 12:00")
				&& dto.getReserv_time2().equals("")
				&& dto.getReserv_time1().equals("12:00 ~ 15:00")) {

			reserv_timedto.setTime_state1(true);
			reserv_timedto.setTime_state2(true);
			reserv_timedto.setTime_state3(true);

		} else if (dto.getReserv_time3().equals("12:00 ~ 15:00")
				&& dto.getReserv_time2().equals("")
				&& dto.getReserv_time1().equals("15:00 ~ 18:00")) {

			reserv_timedto.setTime_state2(true);
			reserv_timedto.setTime_state3(true);
			reserv_timedto.setTime_state4(true);

		} else if (dto.getReserv_time3().equals("15:00 ~ 18:00")
				&& dto.getReserv_time2().equals("")
				&& dto.getReserv_time1().equals("18:00 ~ 21:00")) {

			reserv_timedto.setTime_state3(true);
			reserv_timedto.setTime_state4(true);
			reserv_timedto.setTime_state5(true);
		} else if (dto.getReserv_time3().equals("18:00 ~ 21:00")
				&& dto.getReserv_time2().equals("")
				&& dto.getReserv_time1().equals("21:00 ~ 00:00")) {

			reserv_timedto.setTime_state4(true);
			reserv_timedto.setTime_state5(true);
			reserv_timedto.setTime_state6(true);

		} else if (dto.getReserv_time3().equals("21:00 ~ 00:00")
				&& dto.getReserv_time2().equals("")
				&& dto.getReserv_time1().equals("00:00 ~ 03:00")) {

			reserv_timedto.setTime_state5(true);
			reserv_timedto.setTime_state6(true);
			reserv_timedto.setTime_state7(true);

		} else if (dto.getReserv_time3().equals("00:00 ~ 03:00")
				&& dto.getReserv_time2().equals("")
				&& dto.getReserv_time1().equals("03:00 ~ 06:00")) {

			reserv_timedto.setTime_state6(true);
			reserv_timedto.setTime_state7(true);
			reserv_timedto.setTime_state8(true);

		} else if (dto.getReserv_time3().equals("03:00 ~ 06:00")
				&& dto.getReserv_time2().equals("")
				&& dto.getReserv_time1().equals("06:00 ~ 09:00")) {

			reserv_timedto.setTime_state7(true);
			reserv_timedto.setTime_state8(true);
			reserv_timedto.setTime_state1(true);

		} else if (dto.getReserv_time3().equals("06:00 ~ 09:00")
				&& dto.getReserv_time2().equals("")
				&& dto.getReserv_time1().equals("09:00 ~ 12:00")) {

			reserv_timedto.setTime_state8(true);
			reserv_timedto.setTime_state1(true);
			reserv_timedto.setTime_state2(true);

		}

		if (dto.getReserv_time1().equals("")
				&& dto.getReserv_time2().equals("09:00 ~ 12:00")
				&& dto.getReserv_time3().equals("12:00 ~ 15:00")) {

			reserv_timedto.setTime_state1(true);
			reserv_timedto.setTime_state2(true);
			reserv_timedto.setTime_state3(true);

		} else if (dto.getReserv_time1().equals("")
				&& dto.getReserv_time2().equals("12:00 ~ 15:00")
				&& dto.getReserv_time3().equals("15:00 ~ 18:00")) {

			reserv_timedto.setTime_state2(true);
			reserv_timedto.setTime_state3(true);
			reserv_timedto.setTime_state4(true);

		} else if (dto.getReserv_time1().equals("")
				&& dto.getReserv_time2().equals("15:00 ~ 18:00")
				&& dto.getReserv_time3().equals("18:00 ~ 21:00")) {

			reserv_timedto.setTime_state3(true);
			reserv_timedto.setTime_state4(true);
			reserv_timedto.setTime_state5(true);
		} else if (dto.getReserv_time1().equals("")
				&& dto.getReserv_time2().equals("18:00 ~ 21:00")
				&& dto.getReserv_time3().equals("21:00 ~ 00:00")) {

			reserv_timedto.setTime_state4(true);
			reserv_timedto.setTime_state5(true);
			reserv_timedto.setTime_state6(true);

		} else if (dto.getReserv_time1().equals("")
				&& dto.getReserv_time2().equals("21:00 ~ 00:00")
				&& dto.getReserv_time3().equals("00:00 ~ 03:00")) {

			reserv_timedto.setTime_state5(true);
			reserv_timedto.setTime_state6(true);
			reserv_timedto.setTime_state7(true);

		} else if (dto.getReserv_time1().equals("")
				&& dto.getReserv_time2().equals("00:00 ~ 03:00")
				&& dto.getReserv_time3().equals("03:00 ~ 06:00")) {

			reserv_timedto.setTime_state6(true);
			reserv_timedto.setTime_state7(true);
			reserv_timedto.setTime_state8(true);

		} else if (dto.getReserv_time1().equals("")
				&& dto.getReserv_time2().equals("03:00 ~ 06:00")
				&& dto.getReserv_time3().equals("06:00 ~ 09:00")) {

			reserv_timedto.setTime_state7(true);
			reserv_timedto.setTime_state8(true);
			reserv_timedto.setTime_state1(true);

		} else if (dto.getReserv_time1().equals("")
				&& dto.getReserv_time2().equals("06:00 ~ 09:00")
				&& dto.getReserv_time3().equals("09:00 ~ 12:00")) {

			reserv_timedto.setTime_state8(true);
			reserv_timedto.setTime_state1(true);
			reserv_timedto.setTime_state2(true);

		}

		if (dto.getReserv_time1().equals("")
				&& dto.getReserv_time3().equals("09:00 ~ 12:00")
				&& dto.getReserv_time2().equals("12:00 ~ 15:00")) {

			reserv_timedto.setTime_state1(true);
			reserv_timedto.setTime_state2(true);
			reserv_timedto.setTime_state3(true);

		} else if (dto.getReserv_time1().equals("")
				&& dto.getReserv_time3().equals("12:00 ~ 15:00")
				&& dto.getReserv_time2().equals("15:00 ~ 18:00")) {

			reserv_timedto.setTime_state2(true);
			reserv_timedto.setTime_state3(true);
			reserv_timedto.setTime_state4(true);

		} else if (dto.getReserv_time1().equals("")
				&& dto.getReserv_time3().equals("15:00 ~ 18:00")
				&& dto.getReserv_time2().equals("18:00 ~ 21:00")) {

			reserv_timedto.setTime_state3(true);
			reserv_timedto.setTime_state4(true);
			reserv_timedto.setTime_state5(true);
		} else if (dto.getReserv_time1().equals("")
				&& dto.getReserv_time3().equals("18:00 ~ 21:00")
				&& dto.getReserv_time2().equals("21:00 ~ 00:00")) {

			reserv_timedto.setTime_state4(true);
			reserv_timedto.setTime_state5(true);
			reserv_timedto.setTime_state6(true);

		} else if (dto.getReserv_time1().equals("")
				&& dto.getReserv_time3().equals("21:00 ~ 00:00")
				&& dto.getReserv_time2().equals("00:00 ~ 03:00")) {

			reserv_timedto.setTime_state5(true);
			reserv_timedto.setTime_state6(true);
			reserv_timedto.setTime_state7(true);

		} else if (dto.getReserv_time1().equals("")
				&& dto.getReserv_time3().equals("00:00 ~ 03:00")
				&& dto.getReserv_time2().equals("03:00 ~ 06:00")) {

			reserv_timedto.setTime_state6(true);
			reserv_timedto.setTime_state7(true);
			reserv_timedto.setTime_state8(true);

		} else if (dto.getReserv_time1().equals("")
				&& dto.getReserv_time3().equals("03:00 ~ 06:00")
				&& dto.getReserv_time2().equals("06:00 ~ 09:00")) {

			reserv_timedto.setTime_state7(true);
			reserv_timedto.setTime_state8(true);
			reserv_timedto.setTime_state1(true);

		} else if (dto.getReserv_time1().equals("")
				&& dto.getReserv_time3().equals("06:00 ~ 09:00")
				&& dto.getReserv_time2().equals("09:00 ~ 12:00")) {

			reserv_timedto.setTime_state8(true);
			reserv_timedto.setTime_state1(true);
			reserv_timedto.setTime_state2(true);
		

		}
		}
		////////////////////////////////////////////////
		}else if (count == 1) {
			if (dto.getReserv_time1().equals("09:00 ~ 12:00") 
					&& dto.getReserv_time2().equals("")
					&& dto.getReserv_time3().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);

				} else if (dto.getReserv_time1().equals("12:00 ~ 15:00")
						&& dto.getReserv_time2().equals("")
						&& dto.getReserv_time3().equals("")) {

					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);

				} else if (dto.getReserv_time1().equals("15:00 ~ 18:00")
						&& dto.getReserv_time2().equals("")
						&& dto.getReserv_time3().equals("")) {

					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);

				} else if (dto.getReserv_time1().equals("18:00 ~ 21:00")
						&& dto.getReserv_time2().equals("")
						&& dto.getReserv_time3().equals("")) {

					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);

				} else if (dto.getReserv_time1().equals("21:00 ~ 00:00")
						&& dto.getReserv_time2().equals("")
						&& dto.getReserv_time3().equals("")) {

					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);

				} else if (dto.getReserv_time1().equals("00:00 ~ 03:00")
						&& dto.getReserv_time2().equals("")
						&& dto.getReserv_time3().equals("")) {

					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);

				} else if (dto.getReserv_time1().equals("03:00 ~ 06:00")
						&& dto.getReserv_time2().equals("")
						&& dto.getReserv_time3().equals("")) {

					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				} else if (dto.getReserv_time1().equals("06:00 ~ 09:00")
						     && dto.getReserv_time2().equals("")
						     && dto.getReserv_time3().equals("")) {

					reserv_timedto.setTime_state8(true);
					reserv_timedto.setTime_state1(true);

				}
		
			if (dto.getReserv_time1().equals("") 
					&& dto.getReserv_time2().equals("09:00 ~ 12:00")
					&& dto.getReserv_time3().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);

				} else if (dto.getReserv_time1().equals("")
						&& dto.getReserv_time2().equals("12:00 ~ 15:00")
						&& dto.getReserv_time3().equals("")) {

					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);

				} else if (dto.getReserv_time1().equals("")
						&& dto.getReserv_time2().equals("15:00 ~ 18:00")
						&& dto.getReserv_time3().equals("")) {

					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);

				} else if (dto.getReserv_time1().equals("")
						&& dto.getReserv_time2().equals("18:00 ~ 21:00")
						&& dto.getReserv_time3().equals("")) {

					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);

				} else if (dto.getReserv_time1().equals("")
						&& dto.getReserv_time2().equals("21:00 ~ 00:00")
						&& dto.getReserv_time3().equals("")) {

					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);

				} else if (dto.getReserv_time1().equals("")
						&& dto.getReserv_time2().equals("21:00 ~ 00:00")
						&& dto.getReserv_time3().equals("")) {

					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);

				} else if (dto.getReserv_time1().equals("")
						&& dto.getReserv_time2().equals("03:00 ~ 06:00")
						&& dto.getReserv_time3().equals("")) {

					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				} else if (dto.getReserv_time1().equals("")
						     && dto.getReserv_time2().equals("06:00 ~ 09:00")
						     && dto.getReserv_time3().equals("")) {

					reserv_timedto.setTime_state8(true);
					reserv_timedto.setTime_state1(true);

				}

			if (dto.getReserv_time1().equals("") 
					&& dto.getReserv_time2().equals("")
					&& dto.getReserv_time3().equals("09:00 ~ 12:00")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);

				} else if (dto.getReserv_time1().equals("")
						&& dto.getReserv_time2().equals("")
						&& dto.getReserv_time3().equals("12:00 ~ 15:00")) {

					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);

				} else if (dto.getReserv_time1().equals("")
						&& dto.getReserv_time2().equals("")
						&& dto.getReserv_time3().equals("15:00 ~ 18:00")) {

					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);

				} else if (dto.getReserv_time1().equals("")
						&& dto.getReserv_time2().equals("")
						&& dto.getReserv_time3().equals("18:00 ~ 21:00")) {

					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);

				} else if (dto.getReserv_time1().equals("")
						&& dto.getReserv_time2().equals("")
						&& dto.getReserv_time3().equals("21:00 ~ 00:00")) {

					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);

				} else if (dto.getReserv_time1().equals("")
						&& dto.getReserv_time2().equals("")
						&& dto.getReserv_time3().equals("21:00 ~ 00:00")) {

					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);

				} else if (dto.getReserv_time1().equals("")
						&& dto.getReserv_time2().equals("")
						&& dto.getReserv_time3().equals("03:00 ~ 06:00")) {

					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				} else if (dto.getReserv_time1().equals("")
						     && dto.getReserv_time2().equals("")
						     && dto.getReserv_time3().equals("06:00 ~ 09:00")) {

					reserv_timedto.setTime_state8(true);
					reserv_timedto.setTime_state1(true);

				}
			
			/////////////////////////////////////////////////
				if (dto.getReserv_time1().equals("09:00 ~ 12:00")
				&& dto.getReserv_time2().equals("12:00 ~ 15:00")
				&& dto.getReserv_time3().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				} else if (dto.getReserv_time1().equals("12:00 ~ 15:00")
						&& dto.getReserv_time2().equals("15:00 ~ 18:00")
						&& dto.getReserv_time3().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				} else if (dto.getReserv_time1().equals("15:00 ~ 18:00")
						&& dto.getReserv_time2().equals("18:00 ~ 21:00")
						&& dto.getReserv_time3().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);
					
				} else if (dto.getReserv_time1().equals("18:00 ~ 21:00")
						&& dto.getReserv_time2().equals("21:00 ~ 00:00")
						&& dto.getReserv_time3().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				} else if (dto.getReserv_time1().equals("21:00 ~ 00:00")
						&& dto.getReserv_time2().equals("00:00 ~ 03:00")
						&& dto.getReserv_time3().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				} else if (dto.getReserv_time1().equals("00:00 ~ 03:00")
						&& dto.getReserv_time2().equals("03:00 ~ 06:00")
						&& dto.getReserv_time3().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				} else if (dto.getReserv_time1().equals("03:00 ~ 06:00")
						&& dto.getReserv_time2().equals("06:00 ~ 09:00")
						&& dto.getReserv_time3().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				} else if (dto.getReserv_time1().equals("06:00 ~ 09:00")
						&& dto.getReserv_time2().equals("09:00 ~ 12:00")
						&& dto.getReserv_time3().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				}
				

				if (dto.getReserv_time1().equals("09:00 ~ 12:00")
						&& dto.getReserv_time3().equals("12:00 ~ 15:00")
						&& dto.getReserv_time2().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				} else if (dto.getReserv_time1().equals("12:00 ~ 15:00")
						&& dto.getReserv_time3().equals("15:00 ~ 18:00")
						&& dto.getReserv_time2().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				} else if (dto.getReserv_time1().equals("15:00 ~ 18:00")
						&& dto.getReserv_time3().equals("18:00 ~ 21:00")
						&& dto.getReserv_time2().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);
				} else if (dto.getReserv_time1().equals("18:00 ~ 21:00")
						&& dto.getReserv_time3().equals("21:00 ~ 00:00")
						&& dto.getReserv_time2().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				} else if (dto.getReserv_time1().equals("21:00 ~ 00:00")
						&& dto.getReserv_time3().equals("00:00 ~ 03:00")
						&& dto.getReserv_time2().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				} else if (dto.getReserv_time1().equals("00:00 ~ 03:00")
						&& dto.getReserv_time3().equals("03:00 ~ 06:00")
						&& dto.getReserv_time2().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				} else if (dto.getReserv_time1().equals("03:00 ~ 06:00")
						&& dto.getReserv_time3().equals("06:00 ~ 09:00")
						&& dto.getReserv_time2().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				} else if (dto.getReserv_time1().equals("06:00 ~ 09:00")
						&& dto.getReserv_time3().equals("09:00 ~ 12:00")
						&& dto.getReserv_time2().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				}

				if (dto.getReserv_time2().equals("09:00 ~ 12:00")
						&& dto.getReserv_time1().equals("12:00 ~ 15:00")
						&& dto.getReserv_time3().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				} else if (dto.getReserv_time2().equals("12:00 ~ 15:00")
						&& dto.getReserv_time1().equals("15:00 ~ 18:00")
						&& dto.getReserv_time3().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				} else if (dto.getReserv_time2().equals("15:00 ~ 18:00")
						&& dto.getReserv_time1().equals("18:00 ~ 21:00")
						&& dto.getReserv_time3().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);
				} else if (dto.getReserv_time2().equals("18:00 ~ 21:00")
						&& dto.getReserv_time1().equals("21:00 ~ 00:00")
						&& dto.getReserv_time3().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				} else if (dto.getReserv_time2().equals("21:00 ~ 00:00")
						&& dto.getReserv_time1().equals("00:00 ~ 03:00")
						&& dto.getReserv_time3().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				} else if (dto.getReserv_time2().equals("00:00 ~ 03:00")
						&& dto.getReserv_time1().equals("03:00 ~ 06:00")
						&& dto.getReserv_time3().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				} else if (dto.getReserv_time2().equals("03:00 ~ 06:00")
				&& dto.getReserv_time1().equals("06:00 ~ 09:00")
				&& dto.getReserv_time3().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				} else if (dto.getReserv_time2().equals("06:00 ~ 09:00")
				&& dto.getReserv_time1().equals("09:00 ~ 12:00")
				&& dto.getReserv_time3().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				}
				if (dto.getReserv_time3().equals("09:00 ~ 12:00")
				&& dto.getReserv_time1().equals("12:00 ~ 15:00")
				&& dto.getReserv_time2().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				} else if (dto.getReserv_time3().equals("12:00 ~ 15:00")
						&& dto.getReserv_time1().equals("15:00 ~ 18:00")
						&& dto.getReserv_time2().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				} else if (dto.getReserv_time3().equals("15:00 ~ 18:00")
						&& dto.getReserv_time1().equals("18:00 ~ 21:00")
						&& dto.getReserv_time2().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);
				} else if (dto.getReserv_time3().equals("18:00 ~ 21:00")
						&& dto.getReserv_time1().equals("21:00 ~ 00:00")
						&& dto.getReserv_time2().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				} else if (dto.getReserv_time3().equals("21:00 ~ 00:00")
						&& dto.getReserv_time1().equals("00:00 ~ 03:00")
						&& dto.getReserv_time2().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				} else if (dto.getReserv_time3().equals("00:00 ~ 03:00")
						&& dto.getReserv_time1().equals("03:00 ~ 06:00")
						&& dto.getReserv_time2().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				} else if (dto.getReserv_time3().equals("03:00 ~ 06:00")
						&& dto.getReserv_time1().equals("06:00 ~ 09:00")
						&& dto.getReserv_time2().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				} else if (dto.getReserv_time3().equals("06:00 ~ 09:00")
						&& dto.getReserv_time1().equals("09:00 ~ 12:00")
						&& dto.getReserv_time2().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				}

				if (dto.getReserv_time2().equals("09:00 ~ 12:00")
				&& dto.getReserv_time3().equals("12:00 ~ 15:00")
				&& dto.getReserv_time1().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				} else if (dto.getReserv_time2().equals("12:00 ~ 15:00")
						&& dto.getReserv_time3().equals("15:00 ~ 18:00")
						&& dto.getReserv_time1().equals("")) {
					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				} else if (dto.getReserv_time2().equals("15:00 ~ 18:00")
						&& dto.getReserv_time3().equals("18:00 ~ 21:00")
						&& dto.getReserv_time1().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);
				} else if (dto.getReserv_time2().equals("18:00 ~ 21:00")
						&& dto.getReserv_time3().equals("21:00 ~ 00:00")
						&& dto.getReserv_time1().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				} else if (dto.getReserv_time2().equals("21:00 ~ 00:00")
						&& dto.getReserv_time3().equals("00:00 ~ 03:00")
						&& dto.getReserv_time1().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				} else if (dto.getReserv_time2().equals("00:00 ~ 03:00")
						&& dto.getReserv_time3().equals("03:00 ~ 06:00")
						&& dto.getReserv_time1().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);
				} else if (dto.getReserv_time2().equals("03:00 ~ 06:00")
						&& dto.getReserv_time3().equals("06:00 ~ 09:00")
						&& dto.getReserv_time1().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				} else if (dto.getReserv_time2().equals("06:00 ~ 09:00")
						&& dto.getReserv_time3().equals("09:00 ~ 12:00")
						&& dto.getReserv_time1().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				}

				if (dto.getReserv_time3().equals("09:00 ~ 12:00")
						&& dto.getReserv_time2().equals("12:00 ~ 15:00")
						&& dto.getReserv_time1().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				} else if (dto.getReserv_time3().equals("12:00 ~ 15:00")
						&& dto.getReserv_time2().equals("15:00 ~ 18:00")
						&& dto.getReserv_time1().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				} else if (dto.getReserv_time3().equals("15:00 ~ 18:00")
						&& dto.getReserv_time2().equals("18:00 ~ 21:00")
						&& dto.getReserv_time1().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);
				} else if (dto.getReserv_time3().equals("18:00 ~ 21:00")
						&& dto.getReserv_time2().equals("21:00 ~ 00:00")
						&& dto.getReserv_time2().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);
				} else if (dto.getReserv_time3().equals("21:00 ~ 00:00")
						&& dto.getReserv_time2().equals("00:00 ~ 03:00")
						&& dto.getReserv_time1().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				} else if (dto.getReserv_time3().equals("00:00 ~ 03:00")
						&& dto.getReserv_time2().equals("03:00 ~ 06:00")
						&& dto.getReserv_time1().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				} else if (dto.getReserv_time3().equals("03:00 ~ 06:00")
						&& dto.getReserv_time2().equals("06:00 ~ 09:00")
						&& dto.getReserv_time1().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

				} else if (dto.getReserv_time3().equals("06:00 ~ 09:00")
						&& dto.getReserv_time2().equals("09:00 ~ 12:00")
						&& dto.getReserv_time1().equals("")) {

					reserv_timedto.setTime_state1(true);
					reserv_timedto.setTime_state2(true);
					reserv_timedto.setTime_state3(true);
					reserv_timedto.setTime_state4(true);
					reserv_timedto.setTime_state5(true);
					reserv_timedto.setTime_state6(true);
					reserv_timedto.setTime_state7(true);
					reserv_timedto.setTime_state8(true);

			
		}
				
		}else if(count == 2) {
			
			reserv_timedto.setTime_state1(true);
			reserv_timedto.setTime_state2(true);
			reserv_timedto.setTime_state3(true);
			reserv_timedto.setTime_state4(true);
			reserv_timedto.setTime_state5(true);
			reserv_timedto.setTime_state6(true);
			reserv_timedto.setTime_state7(true);
			reserv_timedto.setTime_state8(true);
			
		}
		

		

		
		
		Reserv_time reserv_time = service.getReserv_time(reserv_timedto);

		if(reserv_time != null) result = service.reservationWrite(dto);
		
		Employee employee = employeeService.Hit(dto.getEmployee_id());

		model.addAttribute("result", result);

		return "/reservation/reservationWrite";

	
		
	}
}
	
	
