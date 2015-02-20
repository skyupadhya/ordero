<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<%@ include file="main.jsp" %>

<body>
	<div class="success">${success} ${orderid} for ${username}</div>
	
	<form action="/cart/showorder" method="post">
	<input type="hidden" name="orderid" value=${orderid} />
	<input type="hidden" name="username" value=${username} />
	<input type="submit" value="Show Order">
	</form>
	
</body>
</html>