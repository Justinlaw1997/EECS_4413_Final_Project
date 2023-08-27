<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="../css/style.css">
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
		
		<h3>Email</h3>
		<input class="text-input-1"type='text' name='email' placeholder="Enter Email Here"></input>
		<h3>Password</h3>
		<input class="text-input-1" type='password' name='password' placeholder="Enter Password Here"></input>
		
		<div id='login-signup'>
			<input class="button-1" role="button" type='submit' name='user-action' value='login'></input>
			<input class="button-1" role="button" type='submit' name='user-action' value='signup'></input>
			<input class="button-1" role="button" type='submit' name='user-action' value='guest'></input>
		</div>
	
	</form>
</body>
</html>