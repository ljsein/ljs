create table client (
    client_seq number,
    client_id varchar2(30) primary key,     -- ���̵�
    client_pwd varchar2(15),                -- ��й�ȣ
    client_name varchar2(30) not null,      -- �̸�
    client_birthyear varchar2(10) not null, -- ����
    client_birthmonth varchar2(10) not null,-- �� 
    client_birthday varchar2(10) not null,   --��
    client_gender varchar2(3) ,     -- ����
    client_email varchar2(50),              -- �̸���
    client_tel varchar2(15) not null,        -- ��ȭ��ȣ
    client_state number,                 -- �������� : 0(����Ż��) 1(��������) 2(�������)
    client_statename varchar2(30)
);

-- 2. ���̺� ����
drop table client purge;
drop table purge;

-- 3. ���̺� ��� Ȯ��
select * from tab;

create sequence client_seq nocache nocycle;
drop sequence client_seq;


-- 4. db ����
commit;

-- 6. ������ ����

insert into client values
(client_seq.nextval,'hong', '1234', 'ȫ�浿',  '1998', '10', '28', 'm','1111@naver.com','010-1111-2222', 0,'�������');

insert into client values
(client_seq.nextval,'admin', '1234', '������',  '1922', '09', '08', 'm','administor@naver.com','010-7777-7777', 0,'�������');

-- ������ �˻�
select * from client;
select * from client where client_id='hong';

-- ������ ����
update client set client_name='ȫ����', client_gender='M', client_tel='010-0000-1111'
where client_id='hong';

-- ������ ����
delete client where client_id='hong';

-- 7. �� ������ �� Ȯ��
select count(*) as cnt from client;
-----------------------------------------------------------------------------
create table employee (
    employee_id varchar2(30) primary key,    -- primary key
    employee_name varchar2(40) not null,     -- �̸�
    employee_birth varchar2(30) not null,    -- 19yy/mm/dd
    employee_height varchar2(10) not null,   -- ���� ������
    employee_gender varchar2(3) not null,    -- ���� ����
    employee_price number not null,          -- ����
    employee_content varchar2(4000) not null, -- ���Ӹ�
    employee_hit number default 0,           -- ���� Ƚ��
    employee_image varchar2(200) not null   -- ���� �̹���

);


-- 2. ���̺� ����
drop table employee purge;

-- 3. ���̺� ��� Ȯ��
select * from tab;

-- 4. db ����
commit;


-- 6. ������ ����
insert into employee values
('emp_2','�̶���','1992-02-04', '171', 'W', 50000, '�ȳ��ϼ���2 ', 0, 'EMP_F001.png');
insert into employee values
('emp_3','�ﶯ��','1992-02-04', '171', 'W', 50000, '�ȳ��ϼ���3 ', 0, 'EMP_F002.png');
insert into employee values
('emp_4','�綯��','1992-02-04', '171', 'W', 50000, '�ȳ��ϼ���4 ', 0, 'EMP_F003.png');
insert into employee values
('emp_5','������','1992-02-04', '171', 'W', 50000, '�ȳ��ϼ���5 ', 0, 'EMP_F004.png');
insert into employee values
('emp_6','������','1992-02-04', '171', 'M', 50000, '�ȳ��ϼ���6 ', 0, 'EMP_M001.png');
insert into employee values
('emp_7','ĥ����','1992-02-04', '171', 'M', 50000, '�ȳ��ϼ���7 ', 0, 'EMP_M002.png');
insert into employee values
('emp_8','�ȶ���','1992-02-04', '171', 'M', 50000, '�ȳ��ϼ���8 ', 0, 'EMP_M003.png');
insert into employee values
('emp_9','������','1992-02-04', '171', 'M', 50000, '�ȳ��ϼ���9 ', 0, 'EMP_M004.png');

-- ������ �˻�
select * from employee;
select * from employee where employee_id='hong';



-- ������ ����
delete employee where employee_id='hong';

-- 7. �� ������ �� Ȯ��
select count(*) as cnt from employee;


-------------------------------------------------------------------------

create table reserv_time(
    time_seq number primary key,
    time_employeeid varchar2(30) , 
    time_reservdate Date not null,
    time_state1 number,      -- ���� ���� ����1
    time_state2 number,      -- ���� ���� ����2
    time_state3 number,      -- ���� ���� ����3
    time_state4 number,      -- ���� ���� ����4
    time_state5 number,      -- ���� ���� ����5
    time_state6 number,      -- ���� ���� ����6
    time_state7 number,      -- ���� ���� ����7
    time_state8 number      -- ���� ���� ����8

);



-- 2. ���̺� ����
drop table reserv_time purge;

select * from reserv_time;

-- 3. ���̺� ��� Ȯ��
select * from tab;

-- 4. db ����
commit;

insert into reserv_time values
(time_seq.nextval,'im',TO_DATE(('2024-02-28'), 'YYYY-MM-DD'),0,0,0,0,0,0,0,0);

-- ������ ��ü ����
create sequence time_seq nocache nocycle;
-- ������ ��ü ����
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

-- ������ ����
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

                 
     reserv_seq number primary key,                   -- �����ȣ 
     reserv_date Date not null,                      -- ���೯¥
     reserv_address varchar2(100) not null,           -- �����ּ�
     reserv_price number not null,
     reserv_time1 varchar2(30),       -- ����ð�1
     reserv_time2 varchar2(30),                -- ����ð�1
     reserv_time3 varchar2(30),                -- ����ð�1
     employee_id varchar2(30) not null,               -- ���� ���̵�
     client_id varchar2(30) not null,                  -- �մ� ���̵�
     reserv_state number, --  0 �������/ 1����Ϸ�/ 2��������Ϸ�
     reserv_statename varchar2(255)
);

SELECT MAX(reserv_code) FROM reservation;

select reserv_date, employee_id from reservation;

select count(reserv_time3) from reservation where reserv_date= '2024-02-28' and reserv_statename='����Ϸ�';

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



-- ���̺� Ȯ��
select * from tab;
-- ���̺� ����
drop table reservation purge;

-- ������ ��ü ����
create sequence reserv_seq nocache nocycle;
-- ������ ��ü ����
drop sequence reserv_seq;

-- ������ ����
insert into reservation values 
(reserv_seq.nextval, TO_DATE(('2024-02-28'), 'YYYY-MM-DD'), '�־ȵ�','150000','00:00 ~ 09:00','','', 'im', 'kim',1,'����Ϸ�');
-- ������ �˻�
select * from reservation;
select * from reservation where client_id='yun';

-- ������ ����
delete reservation where reserv_code='reserv_001';
-- db ����
commit;

-- ������ ���� Ȯ��
select count(*) as cnt from reservation;

-- �ε��� ��
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
     pay_clientid varchar2(3000) not null,  -- ȸ�����̵�
     pay_price number not null,            -- �����ݾ�
     pay_date Date not null,       -- ������¥
     pay_statename varchar(3000) not null -- ��������   �����Ϸ� / �������
);


SELECT py.pay_seq, py.pay_clientid, py.pay_price, py.pay_date, py.pay_statename
FROM pay py, reservation re
WHERE  py.pay_clientid = re.client_id
and py.pay_seq = re.reserv_seq
and py.pay_price = re.reserv_price;


drop table pay purge;
-- ������ ��ü ����
create sequence pay_seq nocache nocycle;
-- ������ ��ü ����
drop sequence pay_seq;

-- ������ �˻�
select * from pay;
select * from pay where pay_clientid='kim';

insert into pay values (pay_seq.nextval,'kim', '150000',TO_DATE(('2024-02-29 21:55:55'), 'YYYY-MM-DD HH24:MI:SS'), '�����Ϸ�');

delete pay where pay_seq='1';
commit;

---------------------------------------------------------------------

create table freeBoard (
     board_seq number primary key,                   -- �����Խ��� ��ȣ
     board_author varchar2(30) not null,             -- �����Խ��� �ۼ��� �̸�
     board_title varchar2(30) not null,              -- �����Խ��� ����
     board_content varchar2(3000) not null,          -- �����Խ��� ����
     board_date date default sysdate,                 -- �����Խ��� �ۼ���
     hit number default 0                              -- ��ȸ��
);


-- ���̺� Ȯ��
select * from tab;
-- ���̺� ����
drop table freeBoard purge;


-- ������ ��ü ����
create sequence board_seq nocache nocycle;
-- ������ ��ü ����
drop sequence board_seq;

-- ������ ����
insert into freeBoard values 
(board_seq.nextval, 'ȫ�浿', '������', '�����...', sysdate, 0);

-- ������ �˻�
select * from freeBoard;
select * from freeBoard where board_author='ȫ�浿';
select * from freeBoard where board_seq=1;

-- ������ ����
delete freeBoard where reserv_seq=1;
delete freeBoard where board_author='ȫ�浿';
-- db ����
commit;

-- ������ ���� Ȯ��
select count(*) as cnt from freeBoard;

-- �ε��� ��
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

-- ������ ����
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

-- ���̺� Ȯ��
select * from tab;
-- ���̺� ����
drop table event purge;

select * from user_sequences;

-- ������ �˻�
select * from event;

commit;