<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta charset="UTF-8">
<title>Welcome</title>
</head>
<body>
	<form method='get' action='/EECS4413FinalProjectJLI/LoginServlet'>
		<%if(request.getAttribute("no-user")!=null){ %>
				<script> alert("User does not exist!") </script>
		<%} %>
		<%if(request.getAttribute("incrpass")!=null){ %>
				<script> alert("Incorrect Password!") </script>
		<%} %>
		
		<h3>User Name</h3>
		<input type='text' name='email'>Enter Email</input>
		<h3>Pass Word</h3>
		<input type='text' name='password'>Enter Password</input>
		
		<div id='login-signup'>
		<input type='submit' name='user-action' value='login'>Login</input>
		<input type='submit' name='user-action' value='signup'>SignUp</input>
		
		</div>
		<input type='submit' name='user-action' value='guest'>Continue as guest</input>
		<input type='checkbox' name='adminPage' value='go'>Admin View</input>
	
	</form>
</body>
</html>