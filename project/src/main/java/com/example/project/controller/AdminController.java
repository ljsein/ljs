package com.example.project.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.google.gson.Gson;

import com.example.project.dao.AdminDAO;
import com.example.project.dto.EmployeeDTO;
import com.example.project.dto.EventDTO;
import com.example.project.dto.ReservationTotalDTO;
import com.example.project.entity.Client;
import com.example.project.entity.Employee;
import com.example.project.entity.Event;
import com.example.project.entity.Pay;
import com.example.project.entity.Reserv_time;
import com.example.project.entity.Reservation;
import com.example.project.service.AdminService;
import com.example.project.service.EventService;

import jakarta.mail.Session;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Value;

@Controller
public class AdminController {
	@Autowired
	AdminService service;
	private String savePath="C:\\Chomijeong\\Spring_boot\\project\\project\\src\\main\\resources\\static\\flieUpload";


	
	 // 관리자 회원관리리스트
	   @GetMapping("/admin/adminClientList")
	   public String adminClientList(HttpServletRequest request, Model model) {
	      // 데이터 처리
	      int num;
	      
	      
	      // db
	      List<Client> list = service.getClientList();
	      // dao.stateUpdate(client_id,client_state);
	      for (Client dto : list) {
	         num = dto.getClient_state();
	         // System.out.println("num : " + num);
	         if (num == 0) {
	            dto.setClient_statename("계정사용");
	         } else if(num == 1) {
	            dto.setClient_statename("계정정지");
	         } else dto.setClient_statename("계정탈퇴");

	         // System.out.println(dto.getClient_statename());
	      }
	      // 데이터공유
	      model.addAttribute("list", list);
	      
 HttpSession session = request.getSession();
	      
	      model.addAttribute("adminId",session.getAttribute("adminId"))  ;
	      model.addAttribute("adminName", session.getAttribute("adminName"));

	      return "/admin/adminClientList";
	   }

	   // 회원계정상태 변경
	   @GetMapping("/admin/adminClientState")
	   public @ResponseBody boolean adminClientState(HttpServletRequest request, Model model) {
	      String client_id = request.getParameter("client_id");
	      String client_state = request.getParameter("client_state");
	      boolean result = false;
	      System.out.println(client_id);
	      System.out.println(client_state);

	      if (client_id != null)
	         result = service.stateUpdate(client_id, client_state);
	      model.addAttribute("result", result);
	      return result;
	      // model.addAttribute(client_state);
	   }

	
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////////이벤트 시작
	// 이벤트 폼
	@GetMapping("/admin/adminEventWriteForm")
	public String adminEventWriteForm(Model model,HttpServletRequest request) {
		
		
		 HttpSession session = request.getSession();
	      
	      model.addAttribute("adminId",session.getAttribute("adminId"))  ;
	      model.addAttribute("adminName", session.getAttribute("adminName"));
		return "/admin/adminEventWriteForm";
	}
	// 이벤트 등록
	@PostMapping("/admin/adminEventWrite")
	public ModelAndView adminEmployeeWrite(Model model, MultipartHttpServletRequest request) {

		ModelAndView mvc = new ModelAndView();
		Event event = new Event();

		MultipartFile event_image = request.getFile("event_image");
		String event_startdate = request.getParameter("event_startdate");
		String event_enddate = request.getParameter("event_enddate");
		String fileName = event_image.getOriginalFilename();
		if (!event_image.isEmpty()) {
			// 파일이 비어있지 않은 경우에만 실행
			String event_imageCode = UUID.randomUUID().toString().substring(0, 2) + "_" + fileName;
			Path imagePath = Paths.get(savePath, event_imageCode);

			try {
				// 프로필 이미지 파일 저장
				Files.write(imagePath, event_image.getBytes());
				// 파일명 및 코드를 Event 객체에 설정
				event.setEvent_imagecode(event_imageCode);
				event.setEvent_image(fileName);
				event.setEvent_startdate(event_startdate);
				event.setEvent_enddate(event_enddate);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// System.out.println(event.toString());
		boolean result = service.EventWrite(event);
		
		
		model.addAttribute("Event_result", result);
		mvc.addObject("Event_result", result);
		mvc.setViewName("/admin/adminEventWrite");

		return mvc;
	}
	// 이벤트 목록
	@GetMapping("/admin/adminEventList")
	public String adminEventList(Model model, HttpServletRequest request) {

		List<Event> list = service.getEventList();
		
		 HttpSession session = request.getSession();
	      
	      model.addAttribute("adminId",session.getAttribute("adminId"))  ;
	      model.addAttribute("adminName", session.getAttribute("adminName"));

		model.addAttribute("list", list);
		return "/admin/adminEventList";

	}
	//이벤트 삭제
	@GetMapping("/admin/adminEventDelete")
	public String adminEventDelete(Model model, HttpServletRequest request) {
		String event_imagecode = request.getParameter("event_imagecode");
		
		//db
		boolean result = service.eventDelete(event_imagecode);
		

		model.addAttribute("result", result);
		return "/admin/adminEventDelete";

	}
	//이벤트 수정
	@PostMapping("/admin/adminEventModify")
	public String adminEventModify(Model model, MultipartHttpServletRequest request) {
		
		String event_imagecode = request.getParameter("event_imagecode");
		System.out.println(event_imagecode);
		boolean result = false;
		
		MultipartFile event_image = request.getFile("event_image");
		String event_startdate = request.getParameter("event_startdate");
		String event_enddate = request.getParameter("event_enddate");
		String fileName = event_image.getOriginalFilename();
		
		if (!event_image.isEmpty()) {
			// 파일이 비어있지 않은 경우에만 실행
			String imageCode = UUID.randomUUID().toString().substring(0, 2) + "_" + fileName;
			Path imagePath = Paths.get(savePath, imageCode);

			try {
				// 프로필 이미지 파일 저장
				Files.write(imagePath, event_image.getBytes());
				// 파일명 및 코드를 Event 객체에 설정
				EventDTO dto = new EventDTO();
				dto.setEvent_imagecode(imageCode);
				dto.setEvent_image(fileName);
				dto.setEvent_startdate(event_startdate);
				dto.setEvent_enddate(event_enddate);
				//System.out.println("event.tostring2 : " + event.toString());
				result = service.eventModify(event_imagecode, dto);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		model.addAttribute("result", result);
		return "/admin/adminEventModify";

	}
	// 이벤트 수정창
	@GetMapping("/admin/adminEventModifyForm")
	public String adminEventModifyForm(Model model, HttpServletRequest request) {
		String event_imagecode = request.getParameter("event_imagecode");
		System.out.println("event_imagecode : " + event_imagecode );
		//db
		Event event = service.event(event_imagecode);

		 HttpSession session = request.getSession();
	      
	      model.addAttribute("adminId",session.getAttribute("adminId"))  ;
	      model.addAttribute("adminName", session.getAttribute("adminName"));
		
		model.addAttribute("event", event);
		return "/admin/adminEventModifyForm";

	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////이벤트 끝
/////////////////////////////////////////////////////////////////예약
	// 관리자 예약관리 / 결제관리
		@GetMapping("/admin/adminReservationList")
		public String adminReservationList(Model model, HttpServletRequest request) {

			
			System.out.println("test");
			
			
			
			List<Reserv_time> reservList = service.adminReservTimeList();
			List<Reservation> list = service.adminReservationList();
			System.out.println(reservList);
			System.out.println(list);
			
			List<ReservationTotalDTO> totalList = new ArrayList<>();
			
			System.out.println(totalList);
			
			for(int i = 0; i<list.size(); i++) {
			ReservationTotalDTO totaldto = new ReservationTotalDTO();
			 totaldto.setReserv_seq(list.get(i).getReserv_seq());
			 totaldto.setReserv_date(list.get(i).getReserv_date());
			 totaldto.setReserv_price(list.get(i).getReserv_price());
			 totaldto.setReserv_time1(list.get(i).getReserv_time1());
			 totaldto.setReserv_time2(list.get(i).getReserv_time2());
			 totaldto.setReserv_time3(list.get(i).getReserv_time3());
			 totaldto.setReserv_address(list.get(i).getReserv_address());
			 totaldto.setEmployee_id(list.get(i).getEmployee_id());
			 totaldto.setClient_id(list.get(i).getClient_id());
			 totaldto.setReserv_state(list.get(i).getReserv_state());
			 totaldto.setReserv_statename(list.get(i).getReserv_statename());
			 totaldto.setTime_reservdate(reservList.get(i).getTime_reservdate());
			 totaldto.setTime_state1(reservList.get(i).isTime_state1());
			 totaldto.setTime_state2(reservList.get(i).isTime_state2());
			 totaldto.setTime_state3(reservList.get(i).isTime_state3());
			 totaldto.setTime_state4(reservList.get(i).isTime_state4());
			 totaldto.setTime_state5(reservList.get(i).isTime_state5());
			 totaldto.setTime_state6(reservList.get(i).isTime_state6());
			 totaldto.setTime_state7(reservList.get(i).isTime_state7());
			 totaldto.setTime_state8(reservList.get(i).isTime_state8());
			 totalList.add(totaldto);
			 
			 if(totalList.get(i).getReserv_state() == 0) {
				 
				 model.addAttribute("reserv_state0", totalList.get(i).getReserv_state());
				 
			 }else if(totalList.get(i).getReserv_state() == 1) {
				 
				 model.addAttribute("reserv_state1", totalList.get(i).getReserv_state());
				 
			 }else if(totalList.get(i).getReserv_state() == 2) {
				 
				 model.addAttribute("reserv_state2", totalList.get(i).getReserv_state());
				 
			 }
				
			}
			System.out.println(totalList);
			
			System.out.println("test1");
			System.out.println(totalList);
			System.out.println(totalList.size());
			model.addAttribute("totalList", totalList);
			
			
			for(Reservation dto : list) {
				if(dto.getReserv_state() == 0) {
					dto.setReserv_statename("예약취소");
				}else if(dto.getReserv_state() == 1) {
					
					dto.setReserv_statename("예약완료");
				}else if(dto.getReserv_state() == 2) {
					dto.setReserv_statename("예약취소");
				}
			}
			for(int i =0; i<list.size(); i++) { 
				if(list.get(i).getReserv_time1() != null) {
					model.addAttribute("reserv_time1", list.get(i).getReserv_time1());
				}
				else if(list.get(i).getReserv_time2() != null) {
					model.addAttribute("reserv_time2", list.get(i).getReserv_time2());
				}else if(list.get(i).getReserv_time3() != null) {
					model.addAttribute("reserv_time3", list.get(i).getReserv_time3());
				}
			}

		    List<Pay> PayList = service.adminPayList();
		    
		    HttpSession session = request.getSession();
		      
		      model.addAttribute("adminId",session.getAttribute("adminId"))  ;
		      model.addAttribute("adminName", session.getAttribute("adminName"));

			model.addAttribute("PayList", PayList);
			
			
			return "/admin/adminReservationList";
		}
		// 예약여부 관리
		@GetMapping("/admin/adminReservationState")
		public @ResponseBody boolean adminReservationState(Model model, HttpServletRequest request) {
			
			
			int reserv_seq = Integer.parseInt(request.getParameter("reserv_seq"));
			String reserv_state = request.getParameter("reserv_state");

			boolean result = false;
			result  = service.adminReservationState(reserv_seq,reserv_state);

			 model.addAttribute("result",result);
		     return result;
			
		}
		
		//직원등록페이지
		@GetMapping("/admin/adminEmployeeWriteForm")
		public String adminEmployeeWriteForm(Model model,HttpServletRequest request){
			
			 HttpSession session = request.getSession();
		      
		      model.addAttribute("adminId",session.getAttribute("adminId"))  ;
		      model.addAttribute("adminName", session.getAttribute("adminName"));
			
			
			return "/admin/adminEmployeeWriteForm";
		}
		// 직원 등록
		@PostMapping("/admin/adminEmployeeWrite")
		public ModelAndView adminEmployeeWrite(Model model,RedirectAttributes ra, EmployeeDTO dto, HttpServletRequest request ){
			
			ModelAndView mvc = new ModelAndView();
			
			Employee employee = dto.toEntity();
			
			MultipartFile employee_imagefile = dto.getEmployee_imagefile();
			
			String employee_image = "";
			if(!employee_imagefile.isEmpty()) {
					// isEmpty() : 비어있는지 확인 / !가 붙었기 때문에 있는지 확인.
					// 첨부 파일 있을 때만 실행.
					UUID uuid = UUID.randomUUID();
					// 1. 파일명 생성
					employee_image = uuid.toString()+"_"+employee_imagefile.getOriginalFilename();
					
					// 3. 프로필 이미지 파일 저장

						try {
							employee_imagefile.transferTo(new File(savePath, employee_image));
						} catch (IllegalStateException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			}	

			employee.setEmployee_image(employee_image);
				
			boolean result = service.adminEmployeeWrite(employee);
			
			
			mvc.addObject("write_result", result);
			
			mvc.setViewName("redirect:/admin/adminEmployeeList");
			
			return mvc;
		}
		
		
		// 직원관리
		@GetMapping("/admin/adminEmployeeList")
		public String adminEmployeeList(Model model, HttpServletRequest request) {
			
			List<Employee> list = service.adminEmployeeList();
			for(int i = 0; i<list.size(); i++) {
			
			model.addAttribute("employee_id",list.get(i).getEmployee_id());
			
			}
			model.addAttribute("list", list);
			 HttpSession session = request.getSession();
		      
		      model.addAttribute("adminId",session.getAttribute("adminId"))  ;
		      model.addAttribute("adminName", session.getAttribute("adminName"));
			
			return "/admin/adminEmployeeList";
		}
		
		@GetMapping("/admin/adminEmployeeViewModal")
		public @ResponseBody String adminEmployeeViewModal( Model model, EmployeeDTO dto, HttpServletRequest request ) {

			Employee employee = dto.toEntity();
			
			employee = service.adminEmployeeViewModal(employee.getEmployee_id());
			
			Gson gson = new Gson();
			String employee_Json = gson.toJson(employee);
			System.out.println(employee_Json);
			
			return employee_Json;
			
		}
		// 직원 정보 삭제
		@GetMapping("/admin/adminEmployeeDelete")
		public ModelAndView adminEmployeeDelete(Model model, HttpServletRequest request ) {
			
			ModelAndView mvc = new ModelAndView();
			
			String employee_id = request.getParameter("employee_id");
			
			boolean delete = service.adminEmployeeDelete(employee_id);
			
			mvc.addObject("delete_result", delete);
			
			mvc.setViewName("redirect:/admin/adminEmployeeList");
			
			return mvc;
			
		}
		// 직원 정보 수정
		@PostMapping("/admin/adminEmployeeModify")
		public ModelAndView adminEmployeeModify(Model model,EmployeeDTO dto, HttpServletRequest request ) {

			ModelAndView mvc = new ModelAndView();
			MultipartFile employee_imagefile = dto.getEmployee_imagefile();		
			String employee_image = "";
			System.out.println(!employee_imagefile.isEmpty());
			
			if(!employee_imagefile.isEmpty()) {
					// isEmpty() : 비어있는지 확인 / !가 붙었기 때문에 있는지 확인.
					// 첨부 파일 있을 때만 실행.
					System.out.println("첨부파일 있음");
					UUID uuid = UUID.randomUUID();
					// 1. 파일명 생성
					employee_image = uuid.toString()+"_"+employee_imagefile.getOriginalFilename();
					
					// 3. 프로필 이미지 파일 저장
					try {
						employee_imagefile.transferTo(new File(savePath, employee_image));
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}else {
				System.out.println("첨부파일 없음");
				
			employee_image = ""; //원래가지고있던 파일명 
				
			Employee employeefile = service.selectimagefile(dto);
			
			employee_image = employeefile.getEmployee_image();

			}
			dto.setEmployee_image(employee_image);
			
			boolean result = service.adminEmployeeModify(dto);
			
			model.addAttribute("result", result);
			
			mvc.setViewName("redirect:/admin/adminEmployeeList");
			
			return mvc;	
		}
		
		// 시간선택1 관리
		@GetMapping("/admin/adminTimeState1")
		public @ResponseBody boolean adminTimeState1(Model model, HttpServletRequest request) {
			
			String time_employeeid = request.getParameter("time_employeeid");
			String time_state1 = request.getParameter("time_state1");
			String time_reservdate = request.getParameter("time_reservdate");
			int time_seq = Integer.parseInt(request.getParameter("time_seq"));
			boolean result = false;
			if(time_employeeid != null) result  = service.adminTimeState1(time_seq,time_employeeid,time_reservdate,time_state1);
			 
		    return result;
			
		}
		// 시간선택2 관리
		@GetMapping("/admin/adminTimeState2")
		public @ResponseBody boolean adminTimeState2(Model model, HttpServletRequest request) {
			
			String time_employeeid = request.getParameter("time_employeeid");
			String time_state2 = request.getParameter("time_state2");
			String time_reservdate = request.getParameter("time_reservdate");
			int time_seq = Integer.parseInt(request.getParameter("time_seq"));
			boolean result = false;
			if(time_employeeid != null) result  = service.adminTimeState2(time_seq,time_employeeid,time_reservdate,time_state2);

		    return result;
			
		}
		// 시간선택3 관리
		@GetMapping("/admin/adminTimeState3")
		public @ResponseBody boolean adminTimeState3(Model model, HttpServletRequest request) {
			
			String time_employeeid = request.getParameter("time_employeeid");
			String time_state3 = request.getParameter("time_state3");
			String time_reservdate = request.getParameter("time_reservdate");
			int time_seq = Integer.parseInt(request.getParameter("time_seq"));
			boolean result = false;
			if(time_employeeid != null) result  = service.adminTimeState3(time_seq,time_employeeid,time_reservdate,time_state3);

		    return result;
			
		}
		// 시간선택4 관리
		@GetMapping("/admin/adminTimeState4")
		public @ResponseBody boolean adminTimeState4(Model model, HttpServletRequest request) {
			
			String time_employeeid = request.getParameter("time_employeeid");
			String time_state4 = request.getParameter("time_state4");
			String time_reservdate = request.getParameter("time_reservdate");
			int time_seq = Integer.parseInt(request.getParameter("time_seq"));
			boolean result = false;
			if(time_employeeid != null) result  = service.adminTimeState4(time_seq,time_employeeid,time_reservdate,time_state4);

		    return result;
			
		}
		// 시간선택5 관리
		@GetMapping("/admin/adminTimeState5")
		public @ResponseBody boolean adminTimeState5(Model model, HttpServletRequest request) {
			
			String time_employeeid = request.getParameter("time_employeeid");
			String time_state5 = request.getParameter("time_state5");
			String time_reservdate = request.getParameter("time_reservdate");
			int time_seq = Integer.parseInt(request.getParameter("time_seq"));
			boolean result = false;
			if(time_employeeid != null) result  = service.adminTimeState5(time_seq,time_employeeid,time_reservdate,time_state5);
	 
		    return result;
			
		}
		// 시간선택6 관리
		@GetMapping("/admin/adminTimeState6")
		public @ResponseBody boolean adminTimeState6(Model model, HttpServletRequest request) {
			
			String time_employeeid = request.getParameter("time_employeeid");
			String time_state6 = request.getParameter("time_state6");
			String time_reservdate = request.getParameter("time_reservdate");
			int time_seq = Integer.parseInt(request.getParameter("time_seq"));
			boolean result = false;
			if(time_employeeid != null) result  = service.adminTimeState6(time_seq,time_employeeid,time_reservdate,time_state6);

			 
		    return result;
			
		}
		// 시간선택7 관리
		@GetMapping("/admin/adminTimeState7")
		public @ResponseBody boolean adminTimeState7(Model model, HttpServletRequest request) {
			
			String time_employeeid = request.getParameter("time_employeeid");
			String time_state7 = request.getParameter("time_state7");
			String time_reservdate = request.getParameter("time_reservdate");
			int time_seq = Integer.parseInt(request.getParameter("time_seq"));
			boolean result = false;
			if(time_employeeid != null) result  = service.adminTimeState7(time_seq,time_employeeid,time_reservdate,time_state7);

		    return result;
			
		}
		// 시간선택8 관리
		@GetMapping("/admin/adminTimeState8")
		public @ResponseBody boolean adminTimeState8(Model model, HttpServletRequest request) {
			
			String time_employeeid = request.getParameter("time_employeeid");
			String time_state8 = request.getParameter("time_state8");
			String time_reservdate = request.getParameter("time_reservdate");
			int time_seq = Integer.parseInt(request.getParameter("time_seq"));
			boolean result = false;
			if(time_employeeid != null) result  = service.adminTimeState8(time_seq,time_employeeid,time_reservdate,time_state8);

		    return result;
			
		}

		
		

}
