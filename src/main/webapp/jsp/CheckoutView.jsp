<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Checkout</title>
	</head>
<body>
	<h2>Checkout</h2>
	<hr>
	<h4>Please verify your shipping information and enter your credit card information below!</h4>
	<p> To submit your order, press the "Confirm Order" button.</p>
	<br>
	
	<%-- Outputs the users shipping information --%>
	<c:set var="user" value="${ sessionScope.user }"/>
	<p><b>Street Address:</b> ${ user.getAddress().getStreetAddress() }</p>
	<p><b>Province:</b> ${ user.getAddress().getProvince() }</p>
	<p><b>Country:</b> ${ user.getAddress().getCountry() }</p>
	<p><b>Postal Code:</b> ${ user.getAddress().getPostalCode() }</p>
	<br>
	
	<%-- Form to grab credit card information --%>
	<form method='get' action='/EECS4413FinalProjectJLI/ConfirmationServlet'>
		<label for="creditNumber">Credit Card Number</label>
		<input type='text' name='creditNumber' value='' /><br>
		
		<label for="expiry">Expiry Date</label>
		<input type='text' name='expiry' value='' /><br>
		
		<label for="security">Security Code</label>
		<input type='text' name='security' value='' /><br>
		<br>
		<input type='submit' value='Confirm Order' />
	</form>
</body>
</html>
