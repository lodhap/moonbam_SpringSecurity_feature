<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 페이지</title>
</head>
<body>
	<h1>회원가입 페이지</h1>
	<form action="/register" method="post">
		<!-- 아이디:<input type="text" name="id" placeholder="Id"><br/> -->
		이름:<input type="text" name="username" placeholder="Username"><br/>
		비밀번호:<input type="text" name="password" placeholder="Password"><br/>
		이메일:<input type="text" name="email" placeholder="Email"><br/>
		<button>회원가입</button>
	</form>
</body>
</html>