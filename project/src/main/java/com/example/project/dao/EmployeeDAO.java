package com.example.project.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.project.dto.ClientDTO;
import com.example.project.dto.EmployeeDTO;
import com.example.project.entity.Client;
import com.example.project.entity.Employee;
import com.example.project.entity.Reservation;
import com.example.project.repository.EmployeeRepository;
import com.example.project.repository.ReservationRepository;
import com.example.project.repository.ClientRepository;

@Repository
public class EmployeeDAO {
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	ReservationRepository reservationRepository;
	
	// 로그인
	public Employee login(String employee_id, String employee_pwd) {
		Employee employee = employeeRepository.findByIdAndPwd(employee_id, employee_pwd);
		return employee;
	}

    // 직원 리스트
    public List<Employee> employeeList(){
    	return employeeRepository.findAll();
    }

	// 직원정보 상세 보기
	public Employee employeeView(String employee_id) {
		return employeeRepository.findById(employee_id).orElse(null);
	}

	// 회원정보 수정
	public boolean employeeUpdate(EmployeeDTO dto) {
		// 1. 수정할 데이터를 db로부터 얻어오기
		Employee employee = employeeRepository.findById(dto.getEmployee_id()).orElse(null);

		boolean result = false;
		if (employee != null) {
			// 2. 수정하기
			employee.setEmployee_birth(dto.getEmployee_birth());
			employee.setEmployee_height(dto.getEmployee_height());
			employee.setEmployee_image(dto.getEmployee_image());
			employee.setEmployee_gender(dto.getEmployee_gender());

			// db에 저장
			Employee employee_update = employeeRepository.save(employee);
			if (employee_update != null)
				result = true;
		}
		return result;
	}

	// 아이디 확인
	public boolean isExistId(String employee_id) {
		return employeeRepository.existsById(employee_id);
	}

	public List<Employee> getEmployeeList() {
		// TODO Auto-generated method stub
		return employeeRepository.findAll();
	}

	public Employee Hit(String employee_id) {
		
		Employee employee = employeeRepository.findById(employee_id).orElse(null);
		if(employee != null) {
			
			employee.setEmployee_hit(employee.getEmployee_hit()+1);
			
		}
		return employeeRepository.save(employee);
	}
}
