<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>Admin User Page</title>
		<link href="css/adminStyle.css" rel="stylesheet">
	</head>
	
	<body>
		<%if(request.getAttribute("delete-current")!=null){ %>
				<script> alert("Can't delete a logged in user") </script>
		<%} %>
		<%if(request.getAttribute("update-current")!=null){ %>
				<script> alert("Can't change status for a logged in use") </script>
		<%} %>
		
		<h2>Manage Users</h2>
	 
        <!-- Switch Views or Log Out -->
        <form method='get' action='/EECS4413FinalProjectJLI/LogOutServlet'>	
       		<input type="submit" class="button-1" name="selection" value="Log Out" />
		</form><br>
		<form method='get' action='/EECS4413FinalProjectJLI/AdminServlet'>
       		<input type="submit" class="button-1" name="selection" value="Manage Items" />
       		<input type="submit" class="button-1" name="selection" value="Manage Orders" />
       	</form><br>
		
		<!-- Filtering options -->
		<br> Filter Users:<br>
		<form method='get' action='/EECS4413FinalProjectJLI/AdminServlet'>
       		<input type="submit" class="button-1" name="filterUsers" value="View All" />
       		<input type="submit" class="button-1" name="filterUsers" value="View Customers" />
       		<input type="submit" class="button-1" name="filterUsers" value="View Admins" />
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
		        <th>Change Status</th>
		        <th>Delete User</th>
	        </tr>
	      
	        <c:forEach items="${requestScope.users}" var="user">
				 <tr>
					 <td><p> ${user.getId()} </p></td>
					 <td><p> ${user.getEmail()} </p></td>
					 <td><p> ${user.getFirstName()} </p></td>
					 <td><p> ${user.getLastName()} </p></td>
					 
					 <!-- Parse the Address -->
					 <td> 
					 	<p>
						 	${user.getAddress().getStreetAddress()}, 
						 	${user.getAddress().getProvince()}, 
						 	${user.getAddress().getCountry()}, 
						 	${user.getAddress().getPostalCode()}  
					 	</p>
					 </td>
					 <td><p>${user.getAddress().getPhone()}</p></td>
					 
					 <!-- Display User Status based on boolean -->
					 <c:choose>
					 	<c:when test="${user.getIsAdmin()=='1'}">
					 		<td><p>Admin</p></td>
					 	</c:when>
					 	<c:otherwise>
					 		<td><p>Customer</p></td>
					 	</c:otherwise>
					 </c:choose>
					 
					 <td><form method='get' action='/EECS4413FinalProjectJLI/AdminServlet'>
					 	<input type="submit" class="button-1" value="Change" />
					 	<input type="hidden" name="selection" value="Manage Users" />
					 	<input type="hidden" name="status" value="${user.getId()}" />
					 </form></td>
					 
					 <td><form method='get' action='/EECS4413FinalProjectJLI/AdminServlet'>
					 	<input type="submit" class="button-1" value="Delete" />
					 	<input type="hidden" name="selection" value="Manage Users" />
					 	<input type="hidden" name="delete" value="${user.getId()}" />
					 </form></td>
					 
				 </tr>
			 </c:forEach>	
        </table>
            		
	</body>
</html>
