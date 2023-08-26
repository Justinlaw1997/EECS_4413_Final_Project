<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title>Admin Order Page</title>
		<link href="css/style.css" rel="stylesheet">
	</head>
	
	<body>
		<h2>Manage Orders</h2>
	 
	    <!-- Switch Views or Log Out -->
	    <form method='get' action='/EECS4413FinalProjectJLI/LogOutServlet'>	
       		<input type="submit" name="selection" value="Log Out" />
		</form><br>
		<form method='get' action='/EECS4413FinalProjectJLI/AdminServlet'>
       		<input type="submit" name="selection" value="Manage Items" />
       		<input type="submit" name="selection" value="Manage Users" />
       	</form><br>
		
		<br> Filter Orders:<br>
		
		<!-- Filter by Customer Dropdown -->
		<form method='get' action='/EECS4413FinalProjectJLI/AdminServlet'>
   			<select class="orderSelect" name="customer" id="customer">
				<c:forEach items="${requestScope.users}" var="customer">
					<option value="${customer.getId()}">${customer.getFirstName()} ${customer.getLastName()}</option>
				</c:forEach>
   			</select>
			<input type="submit" name="filterOrders" value="Filter by Customer" />
			<input type="hidden" name="selection" value="Manage Orders" />
		</form>
		
		<!-- Filter by Item Dropdown -->
		<form method='get' action='/EECS4413FinalProjectJLI/AdminServlet'>
   			<select class="orderSelect" name="item" id="item">
				<c:forEach items="${requestScope.items}" var="item">
					<option value="${item.getItemID()}">${item.getName()}</option>
				</c:forEach>
   			</select>
			<input type="submit" name="filterOrders" value="Filter by Item" />
			<input type="hidden" name="selection" value="Manage Orders" />
		</form>
		
		<!-- Filter by Brand Dropdown -->
		<form method='get' action='/EECS4413FinalProjectJLI/AdminServlet'>
   			<select class="orderSelect" name="brand" id="brand">
				<c:forEach items="${requestScope.brands}" var="brand">
					<option value="${brand.getName()}">${brand.getName()}</option>
				</c:forEach>
   			</select>
			<input type="submit" name="filterOrders" value="Filter by Brand" />
			<input type="hidden" name="selection" value="Manage Orders" />
		</form>
		
		<!-- Filter by Category Dropdown -->
		<form method='get' action='/EECS4413FinalProjectJLI/AdminServlet'>
   			<select class="orderSelect" name="category" id="category">
				<c:forEach items="${requestScope.categories}" var="category">
					<option value="${category.getName()}">${category.getName()}</option>
				</c:forEach>
   			</select>
			<input type="submit" name="filterOrders" value="Filter by Category" />
			<input type="hidden" name="selection" value="Manage Orders" />
		</form>
				
		<!-- Filter by Date Input -->
		<form method='get' action='/EECS4413FinalProjectJLI/AdminServlet'>
   			<input id="orderSelect" name="date" id="date">
			<input type="submit" name="filterOrders" value="Filter by Date" />
			<input type="hidden" name="selection" value="Manage Orders" />
		</form>
		
		<!-- Clear Filters -->
		<form method='get' action='/EECS4413FinalProjectJLI/AdminServlet'>
       		<input type="submit" name="filterOrders" value="Clear Filters" />
       		<input type="hidden" name="selection" value="Manage Orders" />
		</form><br>
		
	 	<!-- List the orders -->
        <table border='1' cellpadding='6'>
	        <tr>
	        	<th>ID</th>
		        <th>Customer</th>
		        <th>Items</th>
		        <th>Total</th>
		        <th>Date</th>
		        <th>Delete Order</th>
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
			         
			         <td><form method='get' action='/EECS4413FinalProjectJLI/AdminServlet'>
					 	<input type="submit" value="Delete" />
					 	<input type="hidden" name="selection" value="Manage Orders" />
					 	<input type="hidden" name="delete" value="${order.getId()}" />
					 </form></td> 
				 </tr>
			 </c:forEach>	
        </table>
       		
	</body>
</html>
