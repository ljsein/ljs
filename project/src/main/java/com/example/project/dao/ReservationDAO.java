package com.example.project.dao;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.project.dto.PayDTO;
import com.example.project.dto.Reserv_timeDTO;
import com.example.project.dto.ReservationDTO;
import com.example.project.entity.Employee;
import com.example.project.entity.Pay;
import com.example.project.entity.Reserv_time;
import com.example.project.entity.Reservation;
import com.example.project.repository.EmployeeRepository;
import com.example.project.repository.PayRepository;
import com.example.project.repository.Reserv_timeRepository;
import com.example.project.repository.ReservationRepository;

@Repository
public class ReservationDAO {
	@Autowired
	ReservationRepository reservationRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	PayRepository payRepositoy;
	@Autowired
	Reserv_timeRepository reserv_timeRepository;

//	// 글저장하기 : insert
//		public boolean insert(ReservationDTO dto) {
//			// 1. dto를 entity로 변경
//			Reservation reservation = dto.toEntity();
//			// 2. db에 저장
//			Reservation reservation_result = reservationRepository.save(reservation);
//
//			boolean result = false;
//			if (reservation_result != null)
//				result = true;
//			return result;
//		}

	// 종업원 예약 리스트
	public List<Reservation> employeeGetReservationList(String employee_id) {
		// System.out.println("employee_id2 dao :" + employee_id);
		List<Reservation> list = reservationRepository.findByEmployid(employee_id);
		return list;
	}

//	// 종업원이 예약 상태 수정
//	public boolean employeeUpdateResstate(String seq, String reserv_state) {
//		int reserv_seq = Integer.parseInt(seq);
//		Reservation reservation = reservationRepository.findById(reserv_seq).orElse(null);
//		boolean result = false;
//		if (reservation != null) {
//			// int state = Integer.parseInt(client_state);
//			if (reserv_state.equals("0")) {
//				reservation.setReserv_state(false); // 예약중
//			} else {
//				reservation.setReserv_state(true); // 예약진행완료
//			}
//
//			reservationRepository.save(reservation);
//			result = true;
//		}
//		return result;
//	}

//		// 클라이언트 예약목록보기
//		public List<Reservation> clientGetReservationList(int startnum, int endnum, String client_id) {
//			return reservationRepository.findByStartnumandEndnum(startnum, endnum, client_id);
//		}

	// 클라이언트 예약리스트
	public List<Reservation> clientGetReservationList(String client_id) {
		System.out.println("client_id dao :" + client_id);
		List<Reservation> list = reservationRepository.findByClientid(client_id);
		return list;
	}

	// 클라이언트 예약 갯수
	public int clientGetCount(String client_id) {
		return (int) reservationRepository.countByClientid(client_id);
	}

	// 예약정보 상세 보기
	public Reservation reservationView(int reserv_seq) {
		return reservationRepository.findById(reserv_seq).orElse(null);
	}

	// 예약 취소
	   public boolean reservationDelete(int reserv_seq) {
	      // 1. seq 조회하여 삭제할 데이터 값 가져오기
	      boolean result = false;
	      // db
	      Reservation reservation = reservationRepository.findById(reserv_seq).orElse(null);
	      // reservation 값이 있을 때

	      if (reservation != null) {
	         if (reservation.getReserv_state() == 1) {
	            // 예약  -> 예약취소
	            reservation.setReserv_state(0);
	            reservation.setReserv_statename("예약취소");
	            reservation.setDeletable(false);
	            //종업원 hit 감소
	            Employee employee = employeeRepository.findById(reservation.getEmployee_id()).orElse(null);
	            if(employee != null) {
	               employee.setEmployee_hit(employee.getEmployee_hit()-1);
	            }
	         }

	         // 2. 
	         reservationRepository.save(reservation); // 데이터 삭제
	         result = true;
	      } 
	      // 데이터가 있는지 없는지 확인하는 함수 boolean 반환
	      // true : 삭제 실패, false : 삭제 성공
	      return result;
	   }
	   

	// 예약 수정
	public boolean modify(ReservationDTO dto) {
		boolean result = false;
		// 1. 수정할 데이터 값 가져오기
		Reservation reservation = reservationRepository.findById(dto.getReserv_seq()).orElse(null);
		// 수정할 대상이 존재하면
		if (reservation != null) {
			// 2. 대상 수정하기
			reservation.setReserv_date(dto.getReserv_date());
			reservation.setReserv_time1(dto.getReserv_time1());
			// 3. 저장
			Reservation reservation_update = reservationRepository.save(reservation);
			if (reservation_update != null) {
				result = true;
			} else {
				result = false;
			}
		}
		return result;
	}

	// 삭제버튼 유무
	public boolean isDeletable(List<Reservation> list) {
		boolean result = false;
		for (Reservation reservation : list) {
			// 예약 날짜가 현재 날짜보다 2시간 이후라면 true, 아니면 false
			long diffInMilliseconds = reservation.getReserv_date().getTime() - System.currentTimeMillis();
			long diffInHours = TimeUnit.MILLISECONDS.toHours(diffInMilliseconds);
			System.out.println(diffInHours);
			System.out.println(diffInMilliseconds);
			if (diffInHours > 2) { // 삭제버튼 유지
				if (reservation.getReserv_state() == 1) {
					reservation.setReserv_state(1); // 0 예약취소 1예약완료 2예약진행완료
					reservation.setDeletable(true);
				} else if (reservation.getReserv_state() == 2) {
					reservation.setDeletable(false);
					reservation.setReserv_state(2);
				}

				result = true;
			} else {
				reservation.setReserv_state(0);
				reservation.setDeletable(false);
				result = false;
			}
		}
		return result;
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////
	// 시간 조회
	public List<Reserv_time> TimeList(String time_reservdate, String time_employeeid) {

		return reserv_timeRepository.timeselect(time_reservdate, time_employeeid);
	}

	// 결제내역 생성
	public Pay payWrite(PayDTO dto) {

		Pay pay = dto.toEntity();

		System.out.println(pay);
		Pay pay_result = payRepositoy.save(pay);

		return pay_result;
	}

	// 시간선택여부확인
	public Reserv_time getReserv_time(Reserv_timeDTO reserv_timedto) {

		Reserv_time reserv_time = reserv_timedto.toEntity();

		return reserv_timeRepository.save(reserv_time);
	}

	// 날짜별 시간 예약
	public int reserv_count(Date reserv_date, String reserv_statename) {

		return reservationRepository.count(reserv_date, reserv_statename);

	}

	// 예약내역 등록
	public boolean reservationWrite(ReservationDTO dto) {

		Reservation reservation = dto.toEntity();

		Reservation reservation_result = reservationRepository.save(reservation);
		boolean result = false;
		if (reservation_result != null) {

			result = true;
		}

		return result;
	}

	public int reserv_count1(Date reserv_date, String reserv_statename) {
		// TODO Auto-generated method stub
		return reservationRepository.count1(reserv_date, reserv_statename);
	}

	public int reserv_count2(Date reserv_date, String reserv_statename) {
		// TODO Auto-generated method stub
		return reservationRepository.count2(reserv_date, reserv_statename);
	}

	public Reserv_time time_click(int time_seq) {
		Reserv_time time =  reserv_timeRepository.time1(time_seq);
		if(time != null) {
			if(time.isTime_state1() == true) {
				
				time.setTime_state1(false);
			}
			if(time.isTime_state2() == true) {
				
				time.setTime_state2(false);
			}
			if(time.isTime_state3() == true) {
				
				time.setTime_state3(false);
			}
			if(time.isTime_state4() == true) {
				
				time.setTime_state4(false);
			}
			if(time.isTime_state5() == true) {
				
				time.setTime_state5(false);
			}
			if(time.isTime_state6() == true) {
				
				time.setTime_state6(false);
			}
			if(time.isTime_state7() == true) {
				
				time.setTime_state7(false);
			}
			if(time.isTime_state8() == true) {
				
				time.setTime_state8(false);
			}
			
			
		}
		return reserv_timeRepository.save(time);
		
	}

}
