package com.example.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.project.entity.Pay;

public interface PayRepository extends JpaRepository<Pay, Integer> {
	@Query(value = "SELECT py.pay_seq"
			+ ", py.pay_clientid"
			+ ", py.pay_price, py.pay_date"
			+ ", py.pay_statename "
			+ "FROM reservation re, pay py "
			+ "WHERE  py.pay_clientid = re.client_id "
			+ "and py.pay_seq = re.reserv_seq "
			+ "and py.pay_price = re.reserv_price",nativeQuery = true)
	List<Pay> findAll();

}
