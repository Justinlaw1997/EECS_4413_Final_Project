<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
        <form method='get' action='/EECS4413FinalProjectJLI/LogOutServlet'>	
       		<input type="submit" name="selection" value="Log Out" />
		</form><br>
		<form method='get' action='/EECS4413FinalProjectJLI/AdminServlet'>
       		<input type="submit" name="selection" value="Manage Orders" />
       		<input type="submit" name="selection" value="Manage Users" />
       	</form><br>
        
        <table border='1' cellpadding='6'>
	        <tr>
	        	<th>ID</th>
	        	<th>Image</th>
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
				 <td><img src="${item.getImage()}" alt="${item.getName()}"></td>
				 <td> ${item.getName()} </td>
		         <td> ${item.getDescription()} </td>
		         <td> ${item.getCategory().getName()} </td>
		         <td> ${item.getBrand().getName()} </td>
		         <td> $ ${item.getPrice()} </td>
		         
   	  			<td><form method='get' action='/EECS4413FinalProjectJLI/AdminServlet'>
   	  			    <input type="hidden" name="selection" value="Manage Items" /> 
	                <input type='hidden' name='update' value='${ item.getItemID() }' /> 
	                <input type='text' size='3' name='qty${ item.getItemID() }' value='${ item.getQuantityStocked() }' />
	                <input type='submit' value='Update' />
			    </form></td>
				 
			 </tr>
			 </c:forEach>
        </table><br /> 
        
	</body>
</html>
