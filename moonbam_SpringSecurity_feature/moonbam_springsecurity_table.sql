drop table member;
create table member(
id VARCHAR2(30) PRIMARY KEY,
username VARCHAR2(30),
password VARCHAR2(200),
email VARCHAR2(50),
role VARCHAR2(12),
createDate date
);

--insert into member values ('member', '123', '회원', 'ROLE_MEMBER', 'true');
--insert into member values ('manager', '123', '매니저', 'ROLE_MANAGER', 'true');
--insert into member values ('admin', '123', '어드민', 'ROLE_ADMIN', 'true');
commit;
-- enable은 특정사용자를 삭제하지 않고 사용자를 비활성화 시킬 수 있음.
