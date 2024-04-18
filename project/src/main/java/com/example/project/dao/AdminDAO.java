package com.example.project.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.project.dto.EmployeeDTO;
import com.example.project.entity.Client;
import com.example.project.entity.Employee;
import com.example.project.entity.Pay;
import com.example.project.entity.Reserv_time;
import com.example.project.entity.Reservation;
import com.example.project.repository.ClientRepository;
import com.example.project.repository.EmployeeRepository;
import com.example.project.repository.PayRepository;
import com.example.project.repository.Reserv_timeRepository;
import com.example.project.repository.ReservationRepository;

@Repository
public class AdminDAO {
	@Autowired
	ClientRepository clientRepository;
	@Autowired
	ReservationRepository reservationRepository;
	@Autowired
	PayRepository payRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	Reserv_timeRepository reserv_timeRepository;

	// 예약관리
	public List<Reservation> adminReservationList() {
		
		return reservationRepository.findAll();
	}

	// 예약여부 관리
	public boolean adminReservationState(int reserv_seq, String reserv_state) {
		boolean result = false;
		Reservation reservation = reservationRepository.findById(reserv_seq).orElse(null);
		if(reservation != null) {
			
			int state = Integer.parseInt(reserv_state);
	          if(reserv_state.equals("0")) {
	        	  reservation.setReserv_state(0);  //예약취소
	        	  reservation.setReserv_statename("예약취소");
	           }else if(reserv_state.equals("1")) {
	        	   reservation.setReserv_state(1);   //예약진행완료
	        	   reservation.setReserv_statename("예약완료");
	           }else if(reserv_state.equals("2")) {
	        	   reservation.setReserv_state(2);   //예약진행완료
	        	   reservation.setReserv_statename("예약이행완료");
	           }
	        reservationRepository.save(reservation);
			result = true;

		}
			return result;		
	}	
	// 시간관리
	public List<Reserv_time> adminReservTimeList() {
		// TODO Auto-generated method stub
		return reserv_timeRepository.findAll();
	}

	// 결제 내역
	public List<Pay> adminPayList() {
			
		return payRepository.findAll();
	}
	// 직원관리
	public List<Employee> adminEmployeeList() {
		
		return employeeRepository.findAll();
	}
	// 직원 등록
	public boolean adminEmployeeWrite(Employee employee) {
		boolean result = false;
		employee = employeeRepository.save(employee);
		if(employee != null) {
			result = true;
		}
		return result;
	}

	// 직원 정보 상세보기
	public Employee adminEmployeeViewModal(String employee_id) {
		
		Employee employee = employeeRepository.findById(employee_id).orElse(null);
		
		return employee;
	}
	// 직원정보 수정
	public boolean adminEmployeeModify(EmployeeDTO dto) {
		boolean result = false;

		Employee employee = employeeRepository.findById(dto.getEmployee_id()).orElse(null);
		
		if(employee != null) {
			
			employee.setEmployee_name(dto.getEmployee_name());
			employee.setEmployee_birth(dto.getEmployee_birth());
			employee.setEmployee_gender(dto.getEmployee_gender());
			employee.setEmployee_price(dto.getEmployee_price());
			employee.setEmployee_height(dto.getEmployee_height());
			employee.setEmployee_content(dto.getEmployee_content());
			employee.setEmployee_image(dto.getEmployee_image());
			
			Employee employee_update = employeeRepository.save(employee);
			if(employee_update != null) {
				result = true;
			}else {
				result = false;
			}
		}
		return result;
	}
	// 직원정보 삭제
	public boolean adminEmployeeDelete(String employee_id) {
		
		boolean delete = false;
		Employee employee = employeeRepository.findById(employee_id).orElse(null);
		
		if(employee != null) {	
			employeeRepository.delete(employee); 
		}
		return employeeRepository.existsById(employee_id);
	}
	// 직원시간선택여부관리1
	public boolean adminTimeState1(int time_seq,String time_employeeid,String time_reservdate, String time_state1) {
		boolean result = false;
		
		System.out.println(time_reservdate);
		Reserv_time reserv_time = reserv_timeRepository.timeselect1(time_seq,time_reservdate,time_employeeid);
		
		
		
		if(reserv_time != null) {
			
			int state = Integer.parseInt(time_state1);
	          if(time_state1.equals("0")) {
	        	  reserv_time.setTime_state1(false);  
	        	  
	           }else {
	        	   reserv_time.setTime_state1(true);  
	        	  
	           }
	        reserv_timeRepository.save(reserv_time);
			result = true;

		}
		return result;		
	}
	// 직원시간선택여부관리2
	public boolean adminTimeState2(int time_seq,String time_employeeid,String time_reservdate, String time_state2) {
		boolean result = false;
		Reserv_time reserv_time = reserv_timeRepository.timeselect1(time_seq,time_reservdate,time_employeeid);
		if(reserv_time != null) {
			
			int state = Integer.parseInt(time_state2);
	          if(time_state2.equals("0")) {
	        	  reserv_time.setTime_state2(false);  
	        	  
	           }else {
	        	   reserv_time.setTime_state2(true);  
	        	  
	           }
	        reserv_timeRepository.save(reserv_time);
			result = true;

		}
		return result;		
	}
	// 직원시간선택여부관리3
	public boolean adminTimeState3(int time_seq,String time_employeeid,String time_reservdate, String time_state3) {
		boolean result = false;
		Reserv_time reserv_time = reserv_timeRepository.timeselect1(time_seq,time_reservdate,time_employeeid);
		if(reserv_time != null) {
			
			int state = Integer.parseInt(time_state3);
	          if(time_state3.equals("0")) {
	        	  reserv_time.setTime_state3(false);  
	        	  
	           }else {
	        	   reserv_time.setTime_state3(true);  
	        	  
	           }
	        reserv_timeRepository.save(reserv_time);
			result = true;

		}
		return result;		
	}
	// 직원시간선택여부관리4
	public boolean adminTimeState4(int time_seq,String time_employeeid,String time_reservdate, String time_state4) {
		boolean result = false;
		Reserv_time reserv_time = reserv_timeRepository.timeselect1(time_seq,time_reservdate,time_employeeid);
		if(reserv_time != null) {
			
			int state = Integer.parseInt(time_state4);
	          if(time_state4.equals("0")) {
	        	  reserv_time.setTime_state4(false);  
	        	  
	           }else {
	        	   reserv_time.setTime_state4(true);  
	        	  
	           }
	        reserv_timeRepository.save(reserv_time);
			result = true;

		}
		return result;		
	}
	// 직원시간선택여부관리5
	public boolean adminTimeState5(int time_seq,String time_employeeid,String time_reservdate, String time_state5 ) {
		boolean result = false;
		Reserv_time reserv_time = reserv_timeRepository.timeselect1(time_seq,time_reservdate,time_employeeid);
		if(reserv_time != null) {
			
			int state = Integer.parseInt(time_state5);
	          if(time_state5.equals("0")) {
	        	  reserv_time.setTime_state5(false);  
	        	  
	           }else {
	        	   reserv_time.setTime_state5(true);  
	        	  
	           }
	        reserv_timeRepository.save(reserv_time);
			result = true;

		}
		return result;		
	}
	// 직원시간선택여부관리6
	public boolean adminTimeState6(int time_seq,String time_employeeid,String time_reservdate, String time_state6) {
		boolean result = false;
		Reserv_time reserv_time = reserv_timeRepository.timeselect1(time_seq,time_reservdate,time_employeeid);
		if(reserv_time != null) {
			
			int state = Integer.parseInt(time_state6);
	          if(time_state6.equals("0")) {
	        	  reserv_time.setTime_state6(false);  
	        	  
	           }else {
	        	   reserv_time.setTime_state6(true);  
	        	  
	           }
	        reserv_timeRepository.save(reserv_time);
			result = true;

		}
		return result;		
	}
	// 직원시간선택여부관리3
	public boolean adminTimeState7(int time_seq,String time_employeeid,String time_reservdate, String time_state7) {
		boolean result = false;
		Reserv_time reserv_time = reserv_timeRepository.timeselect1(time_seq,time_reservdate,time_employeeid);
		

		if(reserv_time != null) {
			
			int state = Integer.parseInt(time_state7);
	
	          if(time_state7.equals("0")) {
	        	  reserv_time.setTime_state7(false);  
	        	  
	           }else {
	        	   reserv_time.setTime_state7(true);  
	        	  
	           }
	        reserv_timeRepository.save(reserv_time);
			result = true;

		}
		return result;	
		}
			
	
	// 직원시간선택여부관리8
	public boolean adminTimeState8(int time_seq,String time_employeeid,String time_reservdate, String time_state8) {
		boolean result = false;
		Reserv_time reserv_time = reserv_timeRepository.timeselect1(time_seq,time_reservdate,time_employeeid);
		System.out.println(reserv_time);
		
		if(reserv_time != null) {
			
			int state = Integer.parseInt(time_state8);
	          if(time_state8.equals("0")) {
	        	  reserv_time.setTime_state8(false);  
	        	  
	           }else {
	        	   reserv_time.setTime_state8(true);  
	        	  
	           }
	          reserv_timeRepository.save(reserv_time);
				result = true;
			}
		return result;		

		}
		
	
	public Employee selectimagefile(EmployeeDTO dto) {
		
		
		
		
		return employeeRepository.findById(dto.getEmployee_id()).orElse(null);
	}

}