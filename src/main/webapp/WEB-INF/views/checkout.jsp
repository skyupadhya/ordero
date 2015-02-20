<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<%@ include file="main.jsp" %>

<body>
	<h1> Product details </h1> 

	<table style="width:80%">
		<tr>
			<th>Name</th>
			<td>${product.name}</td>
		</tr>
		<tr>
			<th>Model/Brand</th>
			<td>${product.brand}</td>
		</tr>
		<tr>
			<th>Description</th>
			<td>${product.description}</td>
		</tr>
	    <tr>	
	    	<th>Product Type<b></th>
	    	<td>${prodType}</td>
	    </tr>
	    <tr>
			<th>Rate</th>
			<td>${prodRate}</td>
		</tr>
		<tr>
			<th>Quantity available</th>
		 	<td>${qty}</td>
	    </tr>
	</table>
	
	<form action="/cart/billing" method='POST'>
		<br/>
		<h3> Please select the quantity </h3>
	    <input type="number" name="quantity" min="1" max=${qty} value="1">
	    
	    <br/>	
		<input type="submit" value="Checkout">
		 
		<input type="hidden" name="prodId" value=${prodId} />
		<input type="hidden" name="prodType" value=${prodType} />
		<input type="hidden" name="prodRate" value=${prodRate} />
		<input type="hidden" name="qty" value=${qty} />
		<input type="hidden" name="username" value=${username} />								
	</form>
</body>
</html>