<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>Admin User Page</title>
	</head>
	
	<body>
		<h2>Manage Users</h2>
	 
        <!-- Switch Views or Log Out -->
		<form method='get' action='/EECS4413FinalProjectJLI/AdminServlet'>
       		<input type="submit" name="selection" value="Manage Items" />
       		<input type="submit" name="selection" value="Manage Orders" />
       		<input type="submit" name="selection" value="Log Out" />
		</form>
		
		<!-- Filtering options -->
		<br> Filter Users:<br>
		<form method='get' action='/EECS4413FinalProjectJLI/AdminServlet'>
       		<input type="submit" name="filterUsers" value="View All" />
       		<input type="submit" name="filterUsers" value="View Customers" />
       		<input type="submit" name="filterUsers" value="View Admins" />
       		<input type="hidden" name="selection" value="Manage Users" />
		</form><br>
		
	 	<!-- List the users -->
        <table border='1' cellpadding='6'>
	        <tr>
	        	<th>ID</th>
	        	<th>Email</th>
		        <th>First Name</th>
		        <th>Last Name</th>
		        <th>Address</th>
		        <th>Phone</th>
		        <th>Status</th>
	        </tr>
	      
	        <c:forEach items="${requestScope.users}" var="user">
				 <tr>
					 <td> ${user.getId()} </td>
					 <td> ${user.getEmail()} </td>
					 <td> ${user.getFirstName()} </td>
					 <td> ${user.getLastName()} </td>
					 
					 <!-- Parse the Address -->
					 <td> 
					 	${user.getAddress().getStreetAddress()}, 
					 	${user.getAddress().getProvince()}, 
					 	${user.getAddress().getCountry()}, 
					 	${user.getAddress().getPostalCode()}  
					 </td>
					 <td>${user.getAddress().getPhone()}</td>
					 
					 <!-- Display User Status based on boolean -->
					 <c:choose>
					 	<c:when test="${user.getIsAdmin()=='1'}">
					 		<td>Admin</td>
					 	</c:when>
					 	<c:otherwise>
					 		<td>Customer</td>
					 	</c:otherwise>
					 </c:choose>
				 </tr>
			 </c:forEach>	
        </table>
            		
	</body>
</html>
