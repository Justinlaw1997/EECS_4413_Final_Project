<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Item Catalog</title>
<link href="css/style.css" rel="stylesheet">
</head>
<body>
	<h2> Item Catalog</h2>
	
		<%-- Output sort by buttons --%>
		<p>Sort by: </p>
		<form method='get' action='/EECS4413FinalProjectJLI/CatalogServlet'>
       		<input type="submit" name="sort" value="All" />
			<input type="submit" name="sort" value="Prices: Ascending" />
			<input type="submit" name="sort" value="Prices: Descending" />
			<input type="submit" name="sort" value="Names: Alphabetically" />
		</form>

		<%-- Output categories drowndown --%>
		<form method='get' action='/EECS4413FinalProjectJLI/CatalogServlet'>
			<label for="categories">Sort by Category: </label>
	     			<select name="categories" id="categories">
   				        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
   						<c:forEach items="${requestScope.categories}" var="category">
   							<option value="${category.getName()}">${category.getName()}</option>
						</c:forEach>
	     			</select>
   					<input type="submit" name="sort" value="Submit: Category" />
		</form>
		
		<%-- Output brands drowndown --%>
		<form method='get' action='/EECS4413FinalProjectJLI/CatalogServlet'>
			<label for="brands">Sort by Brand: </label>
	     			<select name="brands" id="brands">
   				        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
   						<c:forEach items="${requestScope.brands}" var="brand">
   							<option value="${brand.getName()}">${brand.getName()}</option>
						</c:forEach>
	     			</select>
   					<input type="submit" name="sort" value="Submit: Brand" />
		</form>


        <form method='get' action='/EECS4413FinalProjectJLI/CartServlet'>
        
	        <table border='1' cellpadding='6'>
		        <tr>
		        	<th></th>
		        	<th>Image</th>
			        <th>Item name</th>
			        <th>Category</th>
			        <th>Brand</th>
			        <th>Price</th>
			        <th>Quantity</th>
			        <th></th>
		        </tr>
	
	
		        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		        <c:forEach items="${requestScope.allItemsList}" var="item">
				 <tr>
				 	 <td><input type='checkbox' name='id' value='${item.getItemID()}'  /> </td>
					 <td><img src="${item.getImage()}" alt="${item.getName()}"></td>
					 <td> ${item.getName()} </td>
					 <td> ${item.getCategory().getName()}</td>
					 <td> ${item.getBrand().getName()} </td>
			         <td> $ ${item.getPrice()} </td>
			         <td><input type='text' size='4' value='1' name='qty${item.getItemID()}'></td>
			         <td><a href='/EECS4413FinalProjectJLI/ItemServlet?itemID=${item.getItemID()}'>Details</a></td>
				 </tr>
				 </c:forEach>
		
		        <tr>
		       		<td><input type="submit" value="Order" /></td>
    		    	<td><input type='hidden' name='todo' value='add' /></td>
		        </tr>
	
	        </table><br /> 

        </form>
</body>
</html>