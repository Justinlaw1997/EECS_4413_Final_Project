<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Admin Page</title>
		<link href="css/adminStyle.css" rel="stylesheet">
	</head>
	
	<body>
		<h2>Welcome Back, ${sessionScope.user.getFirstName()} ${sessionScope.user.getLastName()}</h2>
		
		<!-- Log Out Button -->
		<form method='get' action='/LogOutServlet'>	
       		<input type="submit" class="button-1" name="selection" value="Log Out" />
		</form><br>
		
		<!-- Output selections for the Admin -->
		<form method='get' action='/AdminServlet'>
       		<input type="submit" class="button-1" name="selection" value="Manage Items" />
       		<input type="submit" class="button-1" name="selection" value="Manage Orders" />
       		<input type="submit" class="button-1" name="selection" value="Manage Users" />
		</form>
	
	</body>
</html>
