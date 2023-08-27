<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta charset="UTF-8">
<title>Welcome</title>
</head>
<body>
	<!-- Only uses this from if this forward was sent from login servlet -->
	<form method='get' action='/EECS4413FinalProjectJLI/LoginServlet'>
		<%if(request.getAttribute("input-error")!=null){ %>
			<%=request.getAttribute("input-error")%>
		<%} %>
		<h3>First Name</h3>
		<input type='text' name='first-name'>Enter First Name
		<h3>Last Name</h3>
		<input type='text' name='last-name'>Enter Last Name
		<h3>Street Address</h3>
		<input type='text' name='street-address'>Enter Street Address
		<h3>Province</h3>
		<input type='text' name='province'>Enter Province
		<h3>Country</h3>
		<input type='text' name='country'>Country
		<h3>Postal Code</h3>
		<input type='text' name='postal-code'>Enter Postal Code
		<h3>Phone</h3>
		<input type='text' name='phone'>Enter Phone
		<h3>Email</h3>
		<input type='text' name='email'>Enter Email
		<h3>Password</h3>
		<input type='password' name='password'>Enter Password
		
		<input type='submit' name='user-action' value='signupReg'>SignUp>	
	</form>
</body>
</html>