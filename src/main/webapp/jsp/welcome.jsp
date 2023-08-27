<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">

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
		<%if(request.getAttribute("not-logged-in")!=null){ %>
				<script> alert("You must be logged in to make a purchase") </script>
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
		
		<input type='checkbox' name='adminPage' value='go'>Admin View</input>
	
	</form>
</body>
</html>