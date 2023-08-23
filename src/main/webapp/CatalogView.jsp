<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Item Catalog</title>
</head>
<body>
	<h2> Item Catalog</h2>
	
		<!-- Output sort by buttons -->
		<p>Sort by: </p>
		<form method='get' action='/CatalogServlet'>
       		<input type="submit" name="sort" value="All" />
			<input type="submit" name="sort" value="Prices: Ascending" />
			<input type="submit" name="sort" value="Prices: Descending" />
			<input type="submit" name="sort" value="Names: Alphabetically" />
		</form>

		<!-- Output categories drowndown -->
		<form method='get' action='/CatalogServlet'>
			<label for="categories">Sort by Category: </label>
	     			<select name="categories" id="categories">
   				        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
   						<c:forEach items="${requestScope.categories}" var="category">
   							<option value="${category.getName()}">${category.getName()}</option>
						</c:forEach>
	     			</select>
   					<input type="submit" name="sort" value="Submit: Category" />
		</form>
		
		<!-- Output brands drowndown -->
		<form method='get' action='/CatalogServlet'>
			<label for="brands">Sort by Brand: </label>
	     			<select name="brands" id="brands">
   				        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
   						<c:forEach items="${requestScope.brands}" var="brand">
   							<option value="${brand.getName()}">${brand.getName()}</option>
						</c:forEach>
	     			</select>
   					<input type="submit" name="sort" value="Submit: Brand" />
		</form>


        <form method='get' action='/checkout'>
        
	        <table border='1' cellpadding='6'>
		        <tr>
		        	<th></th>
			        <th>Item name</th>
			        <th>Description</th>
			        <th>Category</th>
			        <th>Brand</th>
			        <th>Price</th>
		        </tr>
	
	
		        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		        <c:forEach items="${requestScope.allItemsList}" var="item">
				 <tr>
				 	 <td><input type='checkbox' name='item' value='${item.getItemID()}'  /> </td>
					 <td> ${item.getName()} </td>
			         <td> ${item.getDescription()} </td>
			         <td> ${item.getCategory().getName()} </td>
			         <td> ${item.getBrand().getName()} </td>
			         <td> $ ${item.getPrice()} </td>
				 </tr>
				 </c:forEach>
		
		        <tr>
		       		<td><input type="submit" value="Order" /></td>
		        </tr>
	
	        </table><br /> 

        </form>
</body>
</html>