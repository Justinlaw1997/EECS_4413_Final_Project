<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Admin Page</title>
		<link href="css/style.css" rel="stylesheet">
	</head>
	
	<body>
		<h2>Welcome Back, ${sessionScope.user.getFirstName()} ${sessionScope.user.getLastName()}</h2>
		
		<!-- Output selections for the Admin -->
		<form method='get' action='/EECS4413FinalProjectJLI/AdminServlet'>
       		<input type="submit" name="selection" value="Manage Items" />
       		<input type="submit" name="selection" value="Manage Orders" />
       		<input type="submit" name="selection" value="Manage Users" />
       		<input type="submit" name="selection" value="Log Out" />
		</form>
	
	</body>
</html>
