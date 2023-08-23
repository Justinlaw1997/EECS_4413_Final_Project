<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta charset="UTF-8">
<title>Welcome</title>
</head>
<body>
	<form method='get' action='/Login'>
		<h3>User Name</h3>
		<input type='text' name='username' value='Enter UserName'/>
		<h3>Pass Word</h3>
		<input type='text' name='password' value='Enter Password'/>
		
		<div id='login-signup'>
		<input type='submit' name='user-action' value='login'>Login</input>
		<input type='submit' name='user-action' value='signup'>SignUp</input>
		
		</div>
	
		<input type='submit' name='user-action' value='guest'>Continue as guest</input>
		
	</form>
</body>
</html>