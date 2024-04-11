<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>dept 행 수정 화면</h1>
<form action="update" method="post">
부서번호:<input type="text" name="deptno" value="${dept.deptno}" readonly="readonly"><br>
부서명:<input type="text" name="dname" value="${dept.dname}"><br>
부서위치:<input type="text" name="loc" value="${dept.loc}"><br>
<input type="submit" value="수정">
</form>
</body>
</html>