package com.example.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.project.entity.Reserv_time;




public interface Reserv_timeRepository extends JpaRepository<Reserv_time, Integer> {

	@Query(value ="select times.time_seq as time_seq"
			+ ",times.time_employeeid as time_employeeid"
			+ ",times.time_reservdate as time_reservdate"
			+ ",times.time_state1 as time_state1"
			+ ",times.time_state2 as time_state2"
			+ ",times.time_state3 as time_state3"
			+ ",times.time_state4 as time_state4"
			+ ",times.time_state5 as time_state5"
			+ ",times.time_state6 as time_state6"
			+ ",times.time_state7 as time_state7"
			+ ",times.time_state8 as time_state8 "
			+ "FROM employee em , reservation re, reserv_time times "
			+ "WHERE re.employee_id = em.employee_id "
			+ "AND times.time_employeeid = re.employee_id "
			+ "AND times.time_reservdate = re.reserv_date "
			+ "AND times.time_seq = re.reserv_seq "
			+ "order by time_seq",nativeQuery = true)
	List<Reserv_time> findAll();
	
	@Query(value ="select times.time_seq"
			+ ",times.time_employeeid"
			+ ",times.time_reservdate"
			+ ",times.time_state1"
			+ ",times.time_state2"
			+ ",times.time_state3"
			+ ",times.time_state4"
			+ ",times.time_state5"
			+ ",times.time_state6"
			+ ",times.time_state7"
			+ ",times.time_state8 "
			+ "from reserv_time times,reservation re "
			+ "where times.time_employeeid = re.employee_id "
			+ "and times.time_seq = re.reserv_seq "
			+ "and times.time_reservdate =:time_reservdate "
			+ "and times.time_employeeid =:time_employeeid",nativeQuery = true)
	List<Reserv_time> timeselect(@Param("time_reservdate") String time_reservdate ,@Param("time_employeeid") String time_employeeid);

	@Query(value ="select times.time_seq"
			+ ",times.time_employeeid"
			+ ",times.time_reservdate"
			+ ",times.time_state1"
			+ ",times.time_state2"
			+ ",times.time_state3"
			+ ",times.time_state4"
			+ ",times.time_state5"
			+ ",times.time_state6"
			+ ",times.time_state7"
			+ ",times.time_state8 "
			+ "from reserv_time times,reservation re "
			+ "where times.time_employeeid = re.employee_id "
			+ "and times.time_seq = re.reserv_seq "
			+ "and times.time_seq =:time_seq "
			+ "and times.time_reservdate =:time_reservdate "
			+ "and times.time_employeeid =:time_employeeid",nativeQuery = true)
	Reserv_time timeselect1(@Param("time_seq") int time_seq,@Param("time_reservdate") String time_reservdate ,@Param("time_employeeid") String time_employeeid);

	@Query(value ="select times.time_seq"
			+ ",times.time_employeeid"
			+ ",times.time_reservdate"
			+ ",times.time_state1"
			+ ",times.time_state2"
			+ ",times.time_state3"
			+ ",times.time_state4"
			+ ",times.time_state5"
			+ ",times.time_state6"
			+ ",times.time_state7"
			+ ",times.time_state8 "
			+ "from reserv_time times,reservation re "
			+ "where times.time_employeeid = re.employee_id "
			+ "and times.time_seq = re.reserv_seq "
			+ "and times.time_seq =:time_seq",nativeQuery = true)
	Reserv_time time1(@Param("time_seq") int time_seq);

	


	
	
}
