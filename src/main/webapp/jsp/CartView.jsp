<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Shopping Cart</title>
		<link href="css/style.css" rel="stylesheet">
	</head>
<body>
	<h2>Your Shopping Cart</h2>
	<c:choose>
		<!-- Returns the following text if the cart is empty -->
		<c:when test="${ sessionScope.cart.isEmpty() }">
			<p>Your shopping cart is empty</p>
		</c:when>
		<c:otherwise>
			<!-- Returns a table containing of the user's chosen items -->
			<table border='1' cellpadding='6'>
	            <tr>
		            <th>Image ID</th>
		            <th>Item ID</th>
		            <th>Name</th>
		            <th>Description</th>
		            <th>Category</th>
		            <th>Brand</th>
		            <th>Price</th>
		            <th>Update</th>
		            <th>Remove</th></tr>
		            
		            <c:set var="totalPrice" value="0" />
		            <c:forEach items="${sessionScope.cart.getItems() }" var="item">
						
		            	<tr>
	              			<td> ${ item.getItemID() } </td>
				           	<td>
				           		<image src='${ item.getImage() }'>
				           	</td>
				          	<td> ${ item.getName() }</td>
				          	<td> ${ item.getDescription() }</td>
				          	<td> ${ item.getCategory().getName()}</td>
				          	<td> ${ item.getBrand().getName()}</td>
				          	<td> $ ${ item.getPrice() }</td>
	          	  
	          	  			<td>
	          	  				<!-- Updates the quantity of the chosen item -->
		          	  			<form method='get' action='/EECS4413FinalProjectJLI/CartServlet'>
					                <input type='hidden' size='3' name='todo' value='update' /> 
					                <input type='hidden' size='3' name='id' value='${ item.getItemID() }' /> 
					                <input type='text' size='3' name='qty${ item.getItemID() }' value='${ item.getQuantity() }' />
					                <input type='submit' value='Update' />
				                </form>
			                </td>
		 
			                <td>
			                	<!-- Removes the chosen item -->
				                <form method='get' action='/EECS4413FinalProjectJLI/CartServlet'>
					                <input type='hidden' size='3' name='todo' value='remove' >
					                <input type='hidden' size='3' name='id' value='${ item.getItemID() }'> 
					                <input type='submit' value='Remove'>
				                </form>
			                </td>
			            </tr>
		                <c:set var="totalPrice" value="${ totalPrice + item.getPrice() * item.getQuantity()}" />
					</c:forEach>
				<tr>
					<td colspan='6' align=''>Total Price: $ ${ totalPrice }</td>
				</tr>
			</table>
		</c:otherwise>
	</c:choose>
	
	<!-- Button to return to the catalog page -->
	<p><a href='/EECS4413FinalProjectJLI/CatalogServlet'>Continue shopping...</a></p>
	
	<!-- Outputs a button to checkout all the chosen items -->
	<c:if test="${ !sessionScope.cart.isEmpty() }">
		<form method='get' action='OrderServlet'>
			<input type='submit' value='CHECK OUT'>
		</form>
	</c:if>
</body>
</html>