package com.example.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.project.entity.Client;

public interface ClientRepository extends JpaRepository<Client, String>{
	// 아이디와 비밀번호로 조회
		@Query(value="select * from client where client_id=:client_id and client_pwd=:client_pwd",
			   nativeQuery = true)
		Client findByIdAndPwd(@Param("client_id") String client_id, @Param("client_pwd") String client_pwd);

}
