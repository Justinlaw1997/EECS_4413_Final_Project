<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="../css/style.css">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta charset="UTF-8">
<title>Welcome</title>
	<link rel="stylesheet" href="css/cart.css">
		<link rel="stylesheet" href="css/checkout.css">
		<link href="css/style.css" rel="stylesheet">
</head>
<body>
	<!-- Only uses this from if this forward was sent from login servlet -->
	<form method='get' action='/EECS4413FinalProjectJLI/LoginServlet'>
		<%if(request.getAttribute("input-error")!=null){ %>
			<%=request.getAttribute("input-error")%>
		<%} %>
		<h3>First Name</h3>

		<input class="text-input-1" type='text' name='first-name' placeholder='Enter First Name'></input>
		<h3>Last Name</h3>
		<input class="text-input-1" type='text' name='last-name' placeholder='Enter Last Name'></input>
		<h3>Street Address</h3>
		<input class="text-input-1" type='text' name='street-address' placeholder='Enter Street Address'></input>
		<h3>Province</h3>
		<input class="text-input-1" type='text' name='province' placeholder='Enter Province'></input>
		<h3>Country</h3>
		<input class="text-input-1" type='text' name='country' placeholder='Country'></input>
		<h3>Postal Code</h3>
		<input class="text-input-1" type='text' name='postal-code' placeholder='Enter Postal Code'></input>
		<h3>Phone</h3>
		<input class="text-input-1" type='text' name='phone' placeholder='Enter Phone'></input>
		<h3>Email</h3>
		<input class="text-input-1" type='text' name='email' placeholder='Enter Email'></input>
		<h3>Password</h3>
		<input class="text-input-1" type='text' name='password' placeholder='Enter Password'></input>
		
		<button class='button-2' role='button' type='submit' name='user-action' value='signupReg'>Sign Up</button>	

	</form>
</body>
</html>