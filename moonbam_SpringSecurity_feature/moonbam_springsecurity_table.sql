drop table member;
create table member(
id VARCHAR2(30) PRIMARY KEY,
username VARCHAR2(30),
password VARCHAR2(200),
email VARCHAR2(50),
role VARCHAR2(12),
createDate date
);

--insert into member values ('member', '123', 'ȸ��', 'ROLE_MEMBER', 'true');
--insert into member values ('manager', '123', '�Ŵ���', 'ROLE_MANAGER', 'true');
--insert into member values ('admin', '123', '����', 'ROLE_ADMIN', 'true');
commit;
-- enable�� Ư������ڸ� �������� �ʰ� ����ڸ� ��Ȱ��ȭ ��ų �� ����.
