<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Checkout</title>
		<link rel="stylesheet" href="css/cart.css">
		<link rel="stylesheet" href="css/checkout.css">
		<link href="css/style.css" rel="stylesheet">
	</head>
<body>

<h2>Checkout</h2>	

	<hr>
	<form method='get' action='/LogOutServlet'>
		<input class='button-2' role='button' type="submit" name="selection" value="Log Out" />
	</form>
	<h4>Welcome Back, ${user.getFirstName()} ${user.getLastName()}</h4>
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
	<form method='get' action='/ConfirmationServlet'>
		<label for="creditNumber">Credit Card Number</label>
		<input class="text-input-1" type='text' name='creditNumber' value='' /><br>
		
		<label for="expiry">Expiry Date</label>
		<input class="text-input-1" type='text' name='expiry' value='' /><br>
		
		<label for="security">Security Code</label>
		<input class="text-input-1" type='text' name='security' value='' /><br>
		<br>
		<input class='button-2' role='button' type='submit' value='Confirm Order' />
	</form>
</body>
</html>
