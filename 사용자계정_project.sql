create table client (
    client_seq number,
    client_id varchar2(30) primary key,     -- 아이디
    client_pwd varchar2(15),                -- 비밀번호
    client_name varchar2(30) not null,      -- 이름
    client_birthyear varchar2(10) not null, -- 연도
    client_birthmonth varchar2(10) not null,-- 월 
    client_birthday varchar2(10) not null,   --일
    client_gender varchar2(3) ,     -- 성별
    client_email varchar2(50),              -- 이메일
    client_tel varchar2(15) not null,        -- 전화번호
    client_state number,                 -- 계정여부 : 0(계정탈퇴) 1(계정정지) 2(계정사용)
    client_statename varchar2(30)
);

-- 2. 테이블 삭제
drop table client purge;
drop table purge;

-- 3. 테이블 목록 확인
select * from tab;

create sequence client_seq nocache nocycle;
drop sequence client_seq;


-- 4. db 저장
commit;

-- 6. 데이터 저장

insert into client values
(client_seq.nextval,'hong', '1234', '홍길동',  '1998', '10', '28', 'm','1111@naver.com','010-1111-2222', 0,'계정사용');

insert into client values
(client_seq.nextval,'admin', '1234', '관리자',  '1922', '09', '08', 'm','administor@naver.com','010-7777-7777', 0,'계정사용');

-- 데이터 검색
select * from client;
select * from client where client_id='hong';

-- 데이터 수정
update client set client_name='홍정길', client_gender='M', client_tel='010-0000-1111'
where client_id='hong';

-- 데이터 삭제
delete client where client_id='hong';

-- 7. 총 데이터 수 확인
select count(*) as cnt from client;
-----------------------------------------------------------------------------
create table employee (
    employee_id varchar2(30) primary key,    -- primary key
    employee_name varchar2(40) not null,     -- 이름
    employee_birth varchar2(30) not null,    -- 19yy/mm/dd
    employee_height varchar2(10) not null,   -- 직원 몸무게
    employee_gender varchar2(3) not null,    -- 직원 성별
    employee_price number not null,          -- 가격
    employee_content varchar2(4000) not null, -- 말머리
    employee_hit number default 0,           -- 예약 횟수
    employee_image varchar2(200) not null   -- 직원 이미지

);


-- 2. 테이블 삭제
drop table employee purge;

-- 3. 테이블 목록 확인
select * from tab;

-- 4. db 저장
commit;


-- 6. 데이터 저장
insert into employee values
('emp_2','이땡땡','1992-02-04', '171', 'W', 50000, '안녕하세요2 ', 0, 'EMP_F001.png');
insert into employee values
('emp_3','삼땡땡','1992-02-04', '171', 'W', 50000, '안녕하세요3 ', 0, 'EMP_F002.png');
insert into employee values
('emp_4','사땡땡','1992-02-04', '171', 'W', 50000, '안녕하세요4 ', 0, 'EMP_F003.png');
insert into employee values
('emp_5','오땡땡','1992-02-04', '171', 'W', 50000, '안녕하세요5 ', 0, 'EMP_F004.png');
insert into employee values
('emp_6','육땡땡','1992-02-04', '171', 'M', 50000, '안녕하세요6 ', 0, 'EMP_M001.png');
insert into employee values
('emp_7','칠땡땡','1992-02-04', '171', 'M', 50000, '안녕하세요7 ', 0, 'EMP_M002.png');
insert into employee values
('emp_8','팔땡땡','1992-02-04', '171', 'M', 50000, '안녕하세요8 ', 0, 'EMP_M003.png');
insert into employee values
('emp_9','구땡땡','1992-02-04', '171', 'M', 50000, '안녕하세요9 ', 0, 'EMP_M004.png');

-- 데이터 검색
select * from employee;
select * from employee where employee_id='hong';



-- 데이터 삭제
delete employee where employee_id='hong';

-- 7. 총 데이터 수 확인
select count(*) as cnt from employee;


-------------------------------------------------------------------------

create table reserv_time(
    time_seq number primary key,
    time_employeeid varchar2(30) , 
    time_reservdate Date not null,
    time_state1 number,      -- 예약 상태 유무1
    time_state2 number,      -- 예약 상태 유무2
    time_state3 number,      -- 예약 상태 유무3
    time_state4 number,      -- 예약 상태 유무4
    time_state5 number,      -- 예약 상태 유무5
    time_state6 number,      -- 예약 상태 유무6
    time_state7 number,      -- 예약 상태 유무7
    time_state8 number      -- 예약 상태 유무8

);



-- 2. 테이블 삭제
drop table reserv_time purge;

select * from reserv_time;

-- 3. 테이블 목록 확인
select * from tab;

-- 4. db 저장
commit;

insert into reserv_time values
(time_seq.nextval,'im',TO_DATE(('2024-02-28'), 'YYYY-MM-DD'),0,0,0,0,0,0,0,0);

-- 시퀀스 객체 생성
create sequence time_seq nocache nocycle;
-- 시퀀스 객체 삭제
drop sequence time_seq;

drop table reserv_time purge;



select times.time_seq as time_seq
,times.time_employeeid as time_employeeid 
,times.time_reservdate as time_reservdate
,times.time_state1 as time_state1
,times.time_state2 as time_state2
,times.time_state3 as time_state3
,times.time_state4 as time_state4
,times.time_state5 as time_state5
,times.time_state6 as time_state6
,times.time_state7 as time_state7
,times.time_state8 as time_state8
FROM employee em , reservation re, reserv_time times 
WHERE re.employee_id = em.employee_id
AND times.time_employeeid = re.employee_id
AND times.time_reservdate = re.reserv_date
AND times.time_seq = re.reserv_seq
AND time_seq = 1;

select times.time_reservdate
,times.time_employeeid
,times.time_state1
,times.time_state2,times.time_state3
,times.time_state4
,times.time_state5
,times.time_state6
,times.time_state7
,times.time_state8  
from reserv_time times,reservation re 
where times.time_reservdate = re.reserv_date
and times.time_seq = re.reserv_seq
and times.time_seq= 8;

-- 데이터 수정
update (select times.time_reservdate
,times.time_state1
,times.time_state2,times.time_state3
,times.time_state4
,times.time_state5
,times.time_state6
,times.time_state7
,times.time_state8  
from reserv_time times,reservation re 
where times.time_employeeid = re.employee_id
and times.time_reservdate = re.reserv_date
and times.time_seq = re.reserv_seq
and times.time_seq = 8) 
set time_state6 = 1;

select times.time_reservdate
,times.time_state1
,times.time_state2,times.time_state3
,times.time_state4
,times.time_state5
,times.time_state6
,times.time_state7
,times.time_state8  
from reserv_time times,reservation re 
where times.time_employeeid = re.employee_id
and times.time_reservdate = re.reserv_date
and times.time_seq = re.reserv_seq;

select times.time_seq
,times.time_employeeid
,times.time_reservdate
,times.time_state1
,times.time_state2,times.time_state3
,times.time_state4
,times.time_state5
,times.time_state6
,times.time_state7
,times.time_state8  
from reserv_time times,reservation re 
where times.time_reservdate = re.reserv_date 
and times.time_employeeid = 'im' 
and times.time_reservdate = TO_DATE('2024/02/28', 'YYYY-MM-DD')
and times.time_seq = re.reserv_seq;




-----------------------------------------------------------------------------
create table reservation (

                 
     reserv_seq number primary key,                   -- 예약번호 
     reserv_date Date not null,                      -- 예약날짜
     reserv_address varchar2(100) not null,           -- 예약주소
     reserv_price number not null,
     reserv_time1 varchar2(30),       -- 예약시간1
     reserv_time2 varchar2(30),                -- 예약시간1
     reserv_time3 varchar2(30),                -- 예약시간1
     employee_id varchar2(30) not null,               -- 직원 아이디
     client_id varchar2(30) not null,                  -- 손님 아이디
     reserv_state number, --  0 예약취소/ 1예약완료/ 2예약진행완료
     reserv_statename varchar2(255)
);

SELECT MAX(reserv_code) FROM reservation;

select reserv_date, employee_id from reservation;

select count(reserv_time3) from reservation where reserv_date= '2024-02-28' and reserv_statename='예약완료';

SELECT re.reserv_seq as reserv_seq, 
re.employee_id as employee_id,
re.client_id as client_id,
re.reserv_date as reserv_date,
re.reserv_address as reserv_address,
re.reserv_time1 as reserv_time1,
re.reserv_time2 as reserv_time2,
re.reserv_time3 as reserv_time3, 
re.reserv_price as reserv_price,
re.reserv_statename as reserv_statename,
re.reserv_state as reserv_state
,times.time_state1 as time_state1
,times.time_state2 as time_state2
,times.time_state3 as time_state3
,times.time_state4 as time_state4
,times.time_state5 as time_state5
,times.time_state6 as time_state6
,times.time_state7 as time_state7
,times.time_state8 as time_state8
FROM employee em , reservation re , pay py, client cl, reserv_time times
WHERE re.employee_id = em.employee_id
AND re.client_id = py.pay_clientid
AND re.client_id = cl.client_id
AND re.reserv_price = py.pay_price
AND re.reserv_seq = py.pay_seq
AND re.reserv_seq = times.time_seq
AND re.reserv_date = times.time_reservdate
AND re.employee_id = time_employeeid;






select * from reservation;

select time_seq.nextval from dual;



-- 테이블 확인
select * from tab;
-- 테이블 삭제
drop table reservation purge;

-- 시퀀스 객체 생성
create sequence reserv_seq nocache nocycle;
-- 시퀀스 객체 삭제
drop sequence reserv_seq;

-- 데이터 저장
insert into reservation values 
(reserv_seq.nextval, TO_DATE(('2024-02-28'), 'YYYY-MM-DD'), '주안동','150000','00:00 ~ 09:00','','', 'im', 'kim',1,'예약완료');
-- 데이터 검색
select * from reservation;
select * from reservation where client_id='yun';

-- 데이터 삭제
delete reservation where reserv_code='reserv_001';
-- db 저장
commit;

-- 데이터 갯수 확인
select count(*) as cnt from reservation;

-- 인덱스 뷰
select * from 
(select rownum rn, tt.* from
(select * from reservation order by reserv_seq desc) tt)
where rn >=1 and rn<=5;


--select * from board order by seq desc,
--select rownum rn, tt.* from
--(select * from board order by desc) tt;

select * from 
(select rownum rn, tt.* from
(select * from reservation order by reserv_seq asc) tt)
where rn >=1 and rn<=5;

SELECT re.employee_id,re.reserv_date,re.reserv_time1,re.reserv_time2,re.reserv_time3
FROM employee em , reservation re
WHERE re.employee_id = em.employee_id 
AND re.employee_id ='im'
AND re.reserv_date='24/02/28';


--------------------------------------------
create table pay (
     pay_seq number primary key,
     pay_clientid varchar2(3000) not null,  -- 회원아이디
     pay_price number not null,            -- 결제금액
     pay_date Date not null,       -- 결제날짜
     pay_statename varchar(3000) not null -- 결제여부   결제완료 / 결제취소
);


SELECT py.pay_seq, py.pay_clientid, py.pay_price, py.pay_date, py.pay_statename
FROM pay py, reservation re
WHERE  py.pay_clientid = re.client_id
and py.pay_seq = re.reserv_seq
and py.pay_price = re.reserv_price;


drop table pay purge;
-- 시퀀스 객체 생성
create sequence pay_seq nocache nocycle;
-- 시퀀스 객체 삭제
drop sequence pay_seq;

-- 데이터 검색
select * from pay;
select * from pay where pay_clientid='kim';

insert into pay values (pay_seq.nextval,'kim', '150000',TO_DATE(('2024-02-29 21:55:55'), 'YYYY-MM-DD HH24:MI:SS'), '결제완료');

delete pay where pay_seq='1';
commit;

---------------------------------------------------------------------

create table freeBoard (
     board_seq number primary key,                   -- 자유게시판 번호
     board_author varchar2(30) not null,             -- 자유게시판 작성자 이름
     board_title varchar2(30) not null,              -- 자유게시판 제목
     board_content varchar2(3000) not null,          -- 자유게시판 내용
     board_date date default sysdate,                 -- 자유게시판 작성일
     hit number default 0                              -- 조회수
);


-- 테이블 확인
select * from tab;
-- 테이블 삭제
drop table freeBoard purge;


-- 시퀀스 객체 생성
create sequence board_seq nocache nocycle;
-- 시퀀스 객체 삭제
drop sequence board_seq;

-- 데이터 저장
insert into freeBoard values 
(board_seq.nextval, '홍길동', '내일은', '목요일...', sysdate, 0);

-- 데이터 검색
select * from freeBoard;
select * from freeBoard where board_author='홍길동';
select * from freeBoard where board_seq=1;

-- 데이터 삭제
delete freeBoard where reserv_seq=1;
delete freeBoard where board_author='홍길동';
-- db 저장
commit;

-- 데이터 갯수 확인
select count(*) as cnt from freeBoard;

-- 인덱스 뷰
select * from 
(select rownum rn, tt.* from
(select * from freeBoard order by board_seq desc) tt)
where rn >=1 and rn<=5;

----------------------------------------------------------------------------------
create table event (       
     event_imagecode varchar2(100) primary key,
     event_image varchar2(50),     
     event_startdate varchar2(50)  not null, 
     event_enddate varchar2(50)  not null                
);

-- 데이터 저장
insert into event values 
('event1.jpg','event1.jpg', '2024-03-21', '2024-03-28');
insert into event values 
('event2.jpg','event2.jpg', '2024-03-21', '2024-03-28');
insert into event values 
('event3.jpg','event3.jpg', '2024-03-21', '2024-03-28');
insert into event values 
('event4.jpg','event4.jpg', '2024-03-21', '2024-03-28');
insert into event values 
('event5.jpg','event5.jpg', '2024-03-21', '2024-03-28');
insert into event values 
('event6.jpg','event6.jpg', '2024-03-21', '2024-03-28');

-- 테이블 확인
select * from tab;
-- 테이블 삭제
drop table event purge;

select * from user_sequences;

-- 데이터 검색
select * from event;

commit;