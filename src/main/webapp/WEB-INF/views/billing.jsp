<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<%@ include file="main.jsp" %>

<body>	
	<form action="/cart/placeorder" method='POST'>
		
		<table>
		<tr>
		<td>
		<h1>Enter your payment information</h1>
		</td>
			<table>
			<tr>
				<td>Card Type</td>
				<td><select name="cardType" id="cardType">
						<option>Debit</option>
						<option>Credit</option>
				</select></td>
				
			</tr>
			<tr>
				<td>Credit Card number</td>
				<td><input type="text" name="cardNo" required />
				<td>
				<input type="hidden" name="prodId" value=${prodId} />
					<input type="hidden" name="prodType" value=${prodType} />
					<input type="hidden" name="username" value=${username} />
					<input type="hidden" name="prodRate" value=${prodRate} />
					<input type="hidden" name="quantity" value=${quantity} />
			</tr>		
			</table>
			</tr>
			<tr>

			</tr>
		</table>
		<input type = "submit" value = "Confirm">
</body>
</form>
</html>