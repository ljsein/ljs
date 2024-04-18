package com.example.project.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.project.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Integer>{
	@Query(value ="SELECT re.reserv_seq as reserv_seq,"
			+ "re.employee_id as employee_id,"
			+ "re.client_id as client_id,"
			+ "re.reserv_date as reserv_date,"
			+ "re.reserv_address as reserv_address,"
			+ "re.reserv_time1 as reserv_time1,"
			+ "re.reserv_time2 as reserv_time2,"
			+ "re.reserv_time3 as reserv_time3,"
			+ "re.reserv_price as reserv_price,"
			+ "re.reserv_statename as reserv_statename,"
			+ "re.reserv_state as reserv_state "
			+ "FROM employee em , reservation re , pay py, client cl, reserv_time times "
			+ "WHERE re.employee_id = em.employee_id "
			+ "AND re.client_id = py.pay_clientid "
			+ "AND re.client_id = cl.client_id "
			+ "AND re.reserv_price = py.pay_price "
			+ "AND re.reserv_seq = py.pay_seq "
			+ "AND re.reserv_seq = times.time_seq "
			+ "AND re.reserv_date = times.time_reservdate "
			+ "AND re.employee_id = time_employeeid",nativeQuery = true)
	 List<Reservation> findAll();

	
	@Query(value ="select count(reserv_time1) from reservation "
			+ "where reserv_date=:reserv_date "
			+ "and reserv_statename=:reserv_statename",nativeQuery = true)
	 int count(@Param("reserv_date") Date reserv_date, @Param("reserv_statename") String reserv_statename);
	
	@Query(value ="select count(reserv_time2) from reservation "
			+ "where reserv_date=:reserv_date "
			+ "and reserv_statename=:reserv_statename",nativeQuery = true)
	 int count1(@Param("reserv_date") Date reserv_date, @Param("reserv_statename") String reserv_statename);

	@Query(value ="select count(reserv_time3) from reservation "
			+ "where reserv_date=:reserv_date "
			+ "and reserv_statename=:reserv_statename",nativeQuery = true)
	 int count2(@Param("reserv_date") Date reserv_date, @Param("reserv_statename") String reserv_statename);
	


	
	
	
	@Query(value = "select * from "
			+ "(select rownum rn, tt.* from "
			+ "(select * from reservation order by seq desc) tt) "
			+ "where rn>=:startnum and rn<=:endnum and client_id=:client_id",
			nativeQuery = true)
	List<Reservation> findByStartnumandEndnum(@Param("startnum") int startNum, 
					@Param("endnum") int endNum, @Param("client_id") String client_id);
	//employee list 불러오기
	@Query(value = "select * from reservation where employee_id=:employee_id" , nativeQuery = true)
	List<Reservation> findByEmployid(@Param("employee_id") String employee_id);
	//client list 불러오기
	@Query(value = "select * from reservation where client_id=:client_id" , nativeQuery = true)
	List<Reservation> findByClientid(@Param("client_id") String client_id);
	//client 예약 총 갯수
	@Query(value = "select count(*) as cnt from reservation where client_id = :client_id", nativeQuery = true)
	int countByClientid(@Param("client_id") String client_id);
	
}
	

