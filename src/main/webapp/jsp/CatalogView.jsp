<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/cart.css">
<title>Item Catalog</title>
<link href="css/style.css" rel="stylesheet">
</head>
<body>
	<h2> Item Catalog</h2>
	
		<%-- Output conditional options depending if User or Guest --%>
		<div class="login_options" id="forms">
			<form id="login_form_item" method='get' action='/EECS4413FinalProjectJLI/CartServlet'>
				<input class="button-1" role="button" type="submit" value="Go to Cart" />
				<input class="button-1" role="button" type='hidden' name='todo' value='view' />
			</form>
			<c:choose>
			
				<%-- Display 'View Orders' and 'Log Out' if User --%>
				<c:when test="${sessionScope.user != null}">
					<form id="login_form_item"method='get' action='/EECS4413FinalProjectJLI/CustomerOrderServlet'>
						<input  class="button-1" role="button" type="submit" value="View Orders" />
					</form>
					<form id="login_form_item" method='get' action='/EECS4413FinalProjectJLI/LogOutServlet'>
						<input   class="button-1" role="button" type="submit" value="Log Out" />
					</form>
				</c:when>
				
				<%-- Display 'Log In' if Guest --%>
				<c:otherwise>
					<form id="login_form_item" method='get' action='/EECS4413FinalProjectJLI/jsp/welcome.jsp'>
						<input class="button-1" role="button" type="submit" value='Log In' />
					</form>
				</c:otherwise>
				
			</c:choose>
		</div>
		
		
		<%-- Output sort by buttons --%>
		<div class="sort_bar">
			<div id="sort_bar_item">
				<p>Sort by: </p>
			</div>
			<div id="sort_bar_item">
			<form method='get' action='/EECS4413FinalProjectJLI/CatalogServlet'>
	       		<input class="button-2" role="button" type="submit" name="sort" value="All" />
				<input class="button-2" role="button" type="submit" name="sort" value="Prices: Ascending" />
				<input class="button-2" role="button" type="submit" name="sort" value="Prices: Descending" />
				<input class="button-2" role="button" type="submit" name="sort" value="Names: Alphabetically" />
			</form>
			</div>	
		</div>

		<%-- Output categories drowndown --%>
		<form method='get' action='/EECS4413FinalProjectJLI/CatalogServlet'>
			<label for="categories">Sort by Category: </label>
	     			<select name="categories" id="categories">
   				        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
   						<c:forEach items="${requestScope.categories}" var="category">
   							<option value="${category.getName()}">${category.getName()}</option>
						</c:forEach>
	     			</select>
   					<input  class="button-2" role="button" type="submit" name="sort" value="Submit: Category" />
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
   					<input  class="button-2" role="button" type="submit" name="sort" value="Submit: Brand" />
		</form>


        <form id='table-form' method='get' action='/EECS4413FinalProjectJLI/CartServlet'>
        
	        <table border='1' cellpadding='6'>
		        <tr>
		        	<th></th>
		        	<th>Image</th>
			        <th width="200px">Item name</th>
			        <th>Category</th>
			        <th>Brand</th>
			        <th>Price</th>
			        <th>Quantity</th>
			        <th></th>
		        </tr>
	
		        <c:forEach items="${requestScope.allItemsList}" var="item">
				 <tr>
				 	 <td><input id='big-check' type='checkbox' name='id' value='${item.getItemID()}'  /> </td>
					 <td><img src="${item.getImage()}" alt="${item.getName()}"></td>
					 <td width="200px"> ${item.getName()} </td>
					 <td> ${item.getCategory().getName()}</td>
					 <td> ${item.getBrand().getName()} </td>
			         <td> $ ${item.getPrice()} </td>
			         <td><input class='cart-update' type='text' size='4' value='1' name='qty${item.getItemID()}'></td>
			         <td><a class='link-button' href='/EECS4413FinalProjectJLI/ItemServlet?itemID=${item.getItemID()}'>Details</a></td>
				 </tr>
				 </c:forEach>
		
		        <tr>
		       		<td><input class="button-1" role="button" type="submit" value="Order" /></td>
    		    	<td><input type='hidden' name='todo' value='add' /></td>
		        </tr>
	
	        </table><br /> 

        </form>
</body>
</html>