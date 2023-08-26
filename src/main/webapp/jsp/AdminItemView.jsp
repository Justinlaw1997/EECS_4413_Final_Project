<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<!DOCTYPE html>

<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Admin Item View</title>
		<link href="css/style.css" rel="stylesheet">
	</head>
	
	<body>
		<h2>Manage Items</h2>
	 
        <!-- Switch Views or Log Out -->
		<form method='get' action='/EECS4413FinalProjectJLI/AdminServlet'>
       		<input type="submit" name="selection" value="Manage Orders" />
       		<input type="submit" name="selection" value="Manage Users" />
       		<input type="submit" name="selection" value="Log Out" />
		</form><br>
        
        <table border='1' cellpadding='6'>
	        <tr>
	        	<th>ID</th>
		        <th>Name</th>
		        <th>Description</th>
		        <th>Category</th>
		        <th>Brand</th>
		        <th>Price</th>
		        <th>Quantity</th>
	        </tr>

	        <c:forEach items="${requestScope.items}" var="item">
			 <tr>
				 <td> ${item.getItemID()} </td>
				 <td> ${item.getName()} </td>
		         <td> ${item.getDescription()} </td>
		         <td> ${item.getCategory().getName()} </td>
		         <td> ${item.getBrand().getName()} </td>
		         <td> $ ${item.getPrice()} </td>
		         <td> ${item.getQuantity()} </td>
			 </tr>
			 </c:forEach>
        </table><br /> 
        
	</body>
</html>
