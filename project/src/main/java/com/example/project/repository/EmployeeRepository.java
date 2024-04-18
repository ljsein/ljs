package com.example.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.project.entity.Client;
import com.example.project.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
	// 아이디와 비밀번호로 조회
			@Query(value="select * from employee where employee_id=:employee_id and employee_pwd=:employee_pwd",
				   nativeQuery = true)
			Employee findByIdAndPwd(@Param("employee_id") String employee_id, @Param("employee_pwd") String employee_pwd);
			
			
		
				@Query(value = "select * from "
						+ "(select rownum rn, tt.* from "
						+ "(select * from employee order by employee_id desc) tt) "
						+ "where rn>=:startnum and rn<=:endnum",nativeQuery = true)
				List<Employee> findByStartnumandEndnum(@Param("startnum") int startnum, @Param("endnum") int endnum);
				
				
				
			}

