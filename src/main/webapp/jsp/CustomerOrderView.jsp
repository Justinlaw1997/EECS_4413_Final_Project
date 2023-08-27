<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Order Summary</title>
			<link rel="stylesheet" href="css/cart.css">
		<link rel="stylesheet" href="css/checkout.css">
		<link href="css/style.css" rel="stylesheet">
	</head>
	
	<body>
		<h2>Your Orders</h2>

		<%-- Display Orders --%>
		 <table border='1' cellpadding='6'>
	        <tr>
	        	<th>ID</th>
		        <th>Customer</th>
		        <th>Items</th>
		        <th>Total</th>
		        <th>Date</th>
		        <th>Cancel Order</th>
	        </tr>

	        <c:forEach items="${requestScope.orders}" var="order">
				 <tr>
					 <td> ${order.getId()} </td>
			         <td> ${order.getCustomer().getFirstName()} ${order.getCustomer().getLastName()}</td>
			         
					 <!-- Fetch individual items from each order -->
			         <td><c:forEach items="${order.getItems()}" var="item">
				      	${item.getKey().getName()} x ${item.getValue()}<br>
				 	 </c:forEach></td>
				 	 
			         <td> $${order.getTotal()} </td>
			         <td> ${order.getDateOfPurchase()} </td>
			         
			         <td><form method='get' action='/EECS4413FinalProjectJLI/CustomerOrderServlet'>
					 	<input class='cart-update' type="submit" value="Cancel" />
					 	<input type="hidden" name="delete" value="${order.getId()}" />
					 </form></td> 
				 </tr>
			 </c:forEach>
		</table>	

        <p><a href='/EECS4413FinalProjectJLI/CatalogServlet'>Continue shopping...</a></p>
	
	</body>
</html>
