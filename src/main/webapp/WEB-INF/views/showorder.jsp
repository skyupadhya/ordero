<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<%@ include file="main.jsp" %>

<body>	
<h1> Orders for user : ${username} </h1>
 <form action="/cart/showorder">
  <input type="hidden" name="username" value=${username} />
  <input type="hidden" name="orderid" value=${orderid} />
 </form>
 <tr>
			<td>
				<table>
					<th>Order Id</th>
					<th>Item Type</th>
					<th>Item Id</th>
					<th>Status</th>
					<th>Delivery Date</th>
					<th>Order Date</th>

					<c:if test="${not empty orders}">
						<c:forEach var="order" items="${orders}">
							<tr>
								<td>${order.orderId}</td>
								<td>${order.itemType}</td>
								<td>${order.itemTypeId}</td>
								<td>${order.status}</td>
								<td>${order.deliveryDate}</td>
								<td>${order.orderDate}</td>
								<td><form action="/cart/cancel" method='POST'>
								       <input type="hidden" name="orderid" value=${order.orderId} />
								       <input type="hidden" name="username" value=${username} />
								       <input type="submit" value="Cancel">
								    </form>
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</table>
			</td>
		</tr>
		
		<form action="http://localhost:8090/store">
			<input type="submit" value="Back to store">
		</form>
 
 </body>
 </html>