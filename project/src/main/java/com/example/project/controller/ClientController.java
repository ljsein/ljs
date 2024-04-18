package com.example.project.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.project.dao.ClientDAO;
import com.example.project.dao.FreeBoardDAO;
import com.example.project.dto.ClientDTO;
import com.example.project.entity.Client;
import com.example.project.entity.Employee;
import com.example.project.entity.Event;
import com.example.project.entity.Reserv_time;
import com.example.project.entity.Reservation;
import com.example.project.service.ClientService;
import com.example.project.service.EventService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

@Controller
public class ClientController {
	@Autowired
	ClientService service;
	@Autowired
	EventService eventservice;
	// 메인창
	@GetMapping("/main/mainIndex")
	public String mainIndex(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		
		// 2. 데이터 공유
		HttpSession session = request.getSession();
		model.addAttribute("memId", session.getAttribute("memId"));
		model.addAttribute("memName", session.getAttribute("memName"));
		
		// 3. view 처리 파일 선택
		return "/main/mainIndex";
	}
	
	//마이페이지
	@GetMapping("/client/mypage")
	public String mypage(Model model, HttpServletRequest request) {
			      Client client;
			      //데이터처리
			      HttpSession session = request.getSession();
			      String client_id = (String) session.getAttribute("memId");
			      
			      client = service.clientView(client_id);
			      boolean memName =false;
			      boolean is_deletable = false;
			      //boolean reserv_current = false;
			      int reserv_state;
			      //db
			      List<Reservation> list = service.clientGetReservationList(client_id);  //예약한 리스트
			      is_deletable = service.isDeletable(list);//삭제 가능한지 여부
			      if(client_id != null) {
			         memName = true;
			      }else {
			         memName = false;
			      }
			      
			      for(Reservation dto2 : list) {
			         //reserv_current = dto2.isReserv_state();
			    	  reserv_state = dto2.getReserv_state();
			         if(reserv_state == 0) {
			            dto2.setReserv_statename("예약취소");
			         }else if (reserv_state == 1) { 
			            dto2.setReserv_statename("예약완료");
			         }else if (reserv_state == 2) {
			        	dto2.setReserv_statename("진행완료");
			         }
			      }
			      // 이벤트 리스트 값 랜덤으로 가져오기
					List<Event> list2 = eventservice.eventPrint();					
					Random random = new Random();
					int randomIndex1 = random.nextInt(list2.size());
					Event event1 = list2.get(randomIndex1);
					int randomIndex2 = random.nextInt(list2.size());
					Event event2 = list2.get(randomIndex2);
					
			      //System.out.println("mypage client : " + client);
			      //데이터 공유   
			      model.addAttribute("list",list);
			      model.addAttribute("is_deletable",is_deletable);
			      model.addAttribute("client",client);
			      model.addAttribute("memName", session.getAttribute("memName"));
				  model.addAttribute("event1",event1);
			      model.addAttribute("event2",event2);
			      //view 처리
			      return "/client/mypage";

	}
	
	// 유저 로그인폼
	@GetMapping("/client/loginForm")
	public String loginForm() {
		// 1. view 처리 파일 선택
		return "/client/loginForm";
	}
	
	// 로그인
	@PostMapping("/client/login")
	public String login(Model model, HttpServletRequest request) {
		// System.out.println(id + " / " + pwd);
		String client_id = request.getParameter("client_id");
		String client_pwd = request.getParameter("client_pwd");
		// 1. 데이터 처리
		//System.out.println("dto : " + dto.toString());
		
		// db
		Client client = service.login(client_id, client_pwd);

		// 2. 데이터 공유
		if(client != null && (client.getClient_state() == 0)) {	// 로그인 성공
			if(client.getClient_id().equals("admin")) {
				// session에 로그인 정보 저장
				HttpSession session = request.getSession();

				session.setAttribute("adminName", client.getClient_name());
				session.setAttribute("adminId", client_id);
				// session에 저장된 데이터 공유
				
			      
			    model.addAttribute("adminId",session.getAttribute("adminId"))  ;
			    model.addAttribute("adminName", session.getAttribute("adminName"));
				
				return "redirect:/admin/adminClientList";
			}else {
				// session에 로그인 정보 저장
				HttpSession session = request.getSession();

				session.setAttribute("memName", client.getClient_name());
				session.setAttribute("memId", client_id);
				// session에 저장된 데이터 공유
				model.addAttribute("memName", session.getAttribute("memName"));
				model.addAttribute("memId", session.getAttribute("memId"));
				
				// 3.view 처리 파일 선택
				return "/main/mainIndex";
			}
		} else {	// 로그인 실패
			// 3. view 처리 파일 선택
			return "/client/loginFail";
		}
	}
	
	//로그아웃
	@GetMapping("/client/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("memId");	// 개별 삭제
		session.removeAttribute("memName");
		
		session.invalidate();	// 모든 세션 삭제
		return "/client/logout";
	}
	
	//회원가입창
	@GetMapping("/client/writeForm")
	public String writeForm() {
		return "/client/writeForm";
	}
	//회원가입 결과창
	@PostMapping("/client/write")
	public String write(ClientDTO dto, Model model) {
		// 1. 데이터 처리
		// dto.toEntity 객체를 Entity 객체에 저장
		System.out.println(dto.toString());
		
		
		// DB로 데이터 저장
		boolean result = service.clientInsert(dto);
		
		// 2. 데이터 공유
		model.addAttribute("result", result);
		
		// 3. view 처리 파일의 경로선택
		return "/client/write";
	}

	//회원정보 수정결과
	@PostMapping("/client/modify")
	public @ResponseBody String modify(@RequestBody ClientDTO dto, Model model, HttpServletRequest request) {
//		String client_id = request.getParameter("client_id");
//		String client_name = request.getParameter("client_name");
//		String client_pwd = request.getParameter("client_pwd");
//		String client_email = request.getParameter("client_email");
//		String client_tel = request.getParameter("client_tel");
		
		//System.out.println(client_id + " " + client_name + " " + client_pwd + " " + client_email + " " + client_tel + " "  );
		System.out.println("client dto : " + dto.toString());
		// 1. 데이터 처리
		boolean result = service.clientUpdate(dto);
		if(result) { 
			HttpSession session = request.getSession();
			session.setAttribute("memName",dto.getClient_name());
		}
		// 2. 데이터 공유
		model.addAttribute("result", result);
		// 3. view처리 파일 선택
		return "success";
	}
	
	//회원 예약삭제
	   @GetMapping("/client/clientResDelete")
	   public String clientResDelete(Model model, HttpServletRequest request) {
	      //데이터 처리
	      int reserv_seq = Integer.parseInt(request.getParameter("reserv_seq"));
	      int time_seq = Integer.parseInt(request.getParameter("reserv_seq"));
	      System.out.println(reserv_seq);
	      
	      Reserv_time reserv_time = service.time_click(time_seq);
	      
	      //db
	      boolean result =  service.clientResDelete(reserv_seq);
	      
	      
	      //데이터 공유
	      model.addAttribute("result", result);
	      
	      return "/client/clientResDelete";
	   }
	   //회원 삭제
	   @GetMapping("/client/clientDelete")
	   public String clientDelete(Model model, HttpServletRequest request) {
	      //데이터처리
	      String client_id = request.getParameter("client_id");
	      //db
	      boolean result = service.clientDelete(client_id);
	      boolean isExistId =  service.isExistId(client_id);
	      
	      //세션 삭제
	      if(isExistId) {
	         HttpSession session = request.getSession();
	         session.removeAttribute("memId");   // 개별 삭제
	         session.removeAttribute("memName");
	         session.invalidate();   // 모든 세션 삭제
	      }
	      //데이터 공유
	      model.addAttribute("result",result);
	      return "/client/clientDelete";
	   }   

	
	//아이디 중복 검사
	@GetMapping("/client/checkId")
	public String checkid(Model model, HttpServletRequest request) {
		// 1. 데이터 처리
		String client_id = request.getParameter("client_id");
		System.out.println("client_id :" + client_id);
		// 아이디가 중복되면 true 중복이 안되면 false
		boolean result = service.isExistId(client_id);
		// 2. 데이터 공유
		model.addAttribute("result", result);
		model.addAttribute("client_id", client_id);
		
		// 3. view 처리 파일의 선택
		return "/client/checkId";
	}

	
	
	// 클라이언트 예약
//	public String cleintReservation(Reservation dto, Model model) {
//		
//	}
	
}
