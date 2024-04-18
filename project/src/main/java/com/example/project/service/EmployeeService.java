package com.example.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.project.dao.EmployeeDAO;
import com.example.project.dao.ReservationDAO;
import com.example.project.dto.ClientDTO;
import com.example.project.dto.EmployeeDTO;
import com.example.project.entity.Client;
import com.example.project.entity.Employee;
import com.example.project.entity.Event;
import com.example.project.entity.Reservation;
import com.example.project.repository.EmployeeRepository;
import com.example.project.repository.ReservationRepository;
import com.example.project.repository.ClientRepository;

@Repository
public class EmployeeService {
	@Autowired
	EmployeeDAO employeeDAO;

	//직원
		// 직원리스트 보기
	public List<Employee> clientGetEmployeeList() {
		return employeeDAO.employeeList();

	}

	// 직원뷰 보기
	public Employee ClientGetemployeeView(String employee_id) {
		return employeeDAO.employeeView(employee_id);
	}


	
	// employee 1명 조회
	public Employee employeeView(String employee_id) {
		// TODO Auto-generated method stub
		return employeeDAO.employeeView(employee_id);
	}

	public List<Employee> getEmployeeList() {
		// TODO Auto-generated method stub
		return employeeDAO.employeeList();
	}

	public Employee Hit(String employee_id) {
		return employeeDAO.Hit(employee_id);
		
	}

}
