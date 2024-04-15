<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>로그인 페이지</h1>
	<form action="/login" method="post">
		아이디:<input type="text" name="username" placeholder="Username"><br/>
		비밀번호:<input type="password"  name="password" placeholder="Password"><br/>
		<button>로그인</button>
	</form>
	
	<a href="/oauth2/authorization/google">구글 로그인</a>
	<a href="/oauth2/authorization/naver">네이버 로그인</a>
	
	<a href="/registerForm">회원가입</a>
</body>
</html>