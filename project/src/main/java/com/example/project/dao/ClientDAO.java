package com.example.project.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.project.dto.ClientDTO;
import com.example.project.entity.Client;
import com.example.project.entity.Employee;
import com.example.project.entity.Reservation;
import com.example.project.repository.ClientRepository;
import com.example.project.repository.EmployeeRepository;
import com.example.project.repository.ReservationRepository;

@Repository
public class ClientDAO {
	@Autowired
	ClientRepository clientRepository;
	@Autowired
	ReservationRepository reservationRepository;
	@Autowired
	EmployeeRepository employeeRepository;

	// 로그인
	public Client login(String client_id, String client_pwd) {
		Client client = clientRepository.findByIdAndPwd(client_id, client_pwd);
		return client;
	}

	// 회원가입
	public boolean clientInsert(ClientDTO dto) {
		// 1. dto를 entity
		Client Client = dto.toEntity();
		// 2. id가 존재하는 지 검사
		boolean isId = clientRepository.existsById(dto.getClient_id());
		boolean result = false;
		if (!isId) { // id가 없으면 회원데이터 저장
			Client Client_result = clientRepository.save(Client);
			if (Client_result != null)
				result = true;
		}
		// else { // id가 존재하면 회원데이터를 저장하면 안됨

		// }
		return result;
	}

	// 1명 회원정보 읽어오기(마이페이지)
	public Client getClient(String client_id) {
		return clientRepository.findById(client_id).orElse(null);
	}

	// 회원정보 수정
	public boolean clientUpdate(ClientDTO dto) {
		// 1. 수정할 데이터를 db로부터 얻어오기
		Client Client = clientRepository.findById(dto.getClient_id()).orElse(null);

		boolean result = false;
		if (Client != null) {
			// 2. 수정하기
			Client.setClient_name(dto.getClient_name());
			Client.setClient_pwd(dto.getClient_pwd());
			// Client.setAddr(dto.getAddr());
			Client.setClient_gender(dto.getClient_gender());
			Client.setClient_email(dto.getClient_email());
			Client.setClient_tel(dto.getClient_tel());

			// db에 저장
			Client Client_update = clientRepository.save(Client);
			if (Client_update != null)
				result = true;
		}
		return result;
	}

	   
	   
	   // 회원 정보 삭제
	   public boolean clientDelete(String client_id) {
	      Client client = clientRepository.findById(client_id).orElse(null);
	      boolean result = false;
	      if (client != null) {
	         client.setClient_state(2);
	         result = true;
	         clientRepository.save(client);
	      }
	      return result;
	   }
	
	// 아이디 확인
	public boolean isExistId(String client_id) {
		return clientRepository.existsById(client_id);
	}

	// 클라이언트 목록
	public List<Client> ClientList() {
		return clientRepository.findAll();
	}

	// 회원 상태 수정
	public boolean stateUpdate(String client_id, String client_state) {
		Client client = clientRepository.findById(client_id).orElse(null);
		boolean result = false;
		if (client != null) {
			// int state = Integer.parseInt(client_state);
			if (client_state.equals("0")) {
				client.setClient_state(0); // 계정사용
			} else if (client_state.equals("1")){
				client.setClient_state(1); // 계정정지
			} else client.setClient_state(2); // 계정탈퇴

			clientRepository.save(client);
			result = true;
		}
		return result;
	}
}
