package com.example.project.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.project.dao.EmployeeDAO;
import com.example.project.dao.FreeBoardDAO;
import com.example.project.entity.Client;
import com.example.project.entity.Employee;
import com.example.project.entity.Event;
import com.example.project.entity.Reservation;
import com.example.project.service.ClientService;
import com.example.project.service.EmployeeService;
import com.example.project.service.EventService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class EmployeeController {
	@Autowired
	EmployeeService service;
	@Autowired
	EventService eventservice;


	@GetMapping("/employee/aboutUs")
	public String aboutUs(Model model, HttpServletRequest request) {
		
		
		
		HttpSession session = request.getSession();
		
		model.addAttribute("memId", session.getAttribute("memId"));
		model.addAttribute("memName", session.getAttribute("memName"));
		return "/employee/aboutUs";
		
	}
	@GetMapping("/employee/manual")
	public String manual() {
		return "/employee/manual";
		
	}

	//클라이언트 직원리스트 보기
		@GetMapping("/employee/employeeList")
		public String employeeList(Model model, HttpServletRequest request) {
		
			System.out.println("test");
			boolean gender = false;
			//db
			List<Employee> list = service.clientGetEmployeeList();
			List<Event> list2 = eventservice.eventPrint();
			
			// 이벤트 리스트 값 랜덤으로 가져오기
			Random random = new Random();
			int randomIndex1 = random.nextInt(list2.size());
			Event event1 = list2.get(randomIndex1);
			int randomIndex2 = random.nextInt(list2.size());
			Event event2 = list2.get(randomIndex2);
			
			//데이터 공유
			HttpSession session = request.getSession();
			model.addAttribute("list",list); //직원리스트
			model.addAttribute("event1",event1);
			model.addAttribute("event2",event2);
			
			
			model.addAttribute("memName",session.getAttribute("memName"));
			return "/employee/employeeList";
		}
		// 클라이언트가 직원 뷰 보기
		@GetMapping("/employee/employeeView")
		public String employeeView(Model model, HttpServletRequest request) {
			// 1. 데이터 처리
			String employee_id = request.getParameter("employee_id");
			// db
			Employee employee = service.ClientGetemployeeView(employee_id);
			List<Event> list2 = eventservice.eventPrint();
			
			// 이벤트 리스트 값 랜덤으로 가져오기
			Random random = new Random();
			int randomIndex1 = random.nextInt(list2.size());
			Event event1 = list2.get(randomIndex1);
			int randomIndex2 = random.nextInt(list2.size());
			Event event2 = list2.get(randomIndex2);
			// 2. 데이터 공유
			HttpSession session = request.getSession();
			model.addAttribute("memId", session.getAttribute("memId"));
			model.addAttribute("memName", session.getAttribute("memName"));
			model.addAttribute("employee",employee);
			model.addAttribute("event1",event1);
			model.addAttribute("event2",event2);
			// 3. view 처리 파일 선택
			return "/employee/employeeView";
		}
		
	
	

}
