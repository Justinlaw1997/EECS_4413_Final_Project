<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Order Success</title>
	</head>
<body>
	<h2>Your order was successful!</h2>
	
	<%-- Display the completed Order --%>
	 <table border='1' cellpadding='6'>
        <tr>
        	<th>ID</th>
	        <th>Customer</th>
	        <th>Items</th>
	        <th>Total</th>
	        <th>Date</th>
        </tr>
      
		 <tr>
			 <td> ${order.getId()} </td>
	         <td> ${order.getCustomer().getFirstName()} ${order.getCustomer().getLastName()}</td>
	         
			 <!-- Fetch individual items from each order -->
	         <td><c:forEach items="${order.getItems()}" var="item">
		      	${item.getName()}<br>
		 	 </c:forEach></td>
		 	 
	         <td> $${order.getTotal()} </td>
	         <td> ${order.getDateOfPurchase()} </td>
		 </tr>	
       </table>
	
	<%-- Button to return to the catalog page --%>
	<p><a href='/EECS4413FinalProjectJLI/CatalogServlet'>Continue shopping...</a></p>
</body>
</html>