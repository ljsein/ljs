-- DB�� ��ϵ� ��������
select * from all_users;

-- ����� ���� �����
-- 1) ����� ���� �����ϱ�
-- id : C##dbexam pass : m1234
create user C##dbexam identified by m1234;
-- 2) ����� ������ ���� �ο��ϱ�
grant create session, create table, create sequence, create view to C##dbexam;
-- 3) ����� ������ ���̺����̽��� users�� �����ϱ�\
alter user C##dbexam default tablespace users;
-- 4) ����� ������ ���̺� �����̽� ���� �Ҵ��ϱ�
alter user C##dbexam quota unlimited on users;

-- ���� �����ϱ�
1) ������ �����Ͱ� ���� ���
drop user C##dbexam;
2) ������ �����Ͱ� ���� ���
drop user C##dbexam cascade;


