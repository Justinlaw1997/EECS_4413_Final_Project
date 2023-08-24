<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Insert title here</title>
		<link href="css/style.css" rel="stylesheet">
	</head>
	
	<body>
		
		 <h1>${item.getName()}</h1>
		 <img src="${item.getImage()}" alt="${item.getName()}" id="singleImage">
		 <h3>${item.getDescription()}</h3>
		 
         <p><b>Price:</b> $${item.getPrice()}</p> 
         <p><b>Category:</b> ${item.getCategory().getName()}</p> 
         <p><b>Brand:</b> ${item.getBrand().getName()}</p> 
         <p><b>Quantity In Stock:</b> ${item.getQuantity()}</p> 
		
		<form method='get' action='/EECS4413FinalProjectJLI/CartServlet'>
  			<input type='text' size='4' value='1' name='qty${item.getItemID()}'>
        	<input type="submit" value="Add to Cart" />
        	<input type='hidden' name='todo' value='add' />
        	<input type='hidden' name='singleId' value='${item.getItemID()}' />
        </form><br>
		<form method='get' action='/EECS4413FinalProjectJLI/CatalogServlet'>
		     <input type="submit" value="Return to Item Catalog" />
        </form>

	</body>
</html>
