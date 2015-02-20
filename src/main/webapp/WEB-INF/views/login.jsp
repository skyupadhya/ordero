<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<%@ include file="main.jsp" %>


<body onload='document.loginForm.username.focus();'>
 
	<div id="login-box">
 
		<h1>Login with Username and Password</h1>
 
		<c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="msg">${msg}</div>
		</c:if>
 
		<form name='loginForm' action="/cart/checkout" method='POST'>
 
		<table>
			<tr>
				<td>User:</td>
				<td><input type='text' name='username' required /></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type='password' name='password' required /></td>
			</tr>
			<tr>
				<td colspan='2'><input name="submit" type="submit"
				  value="submit"/></td>
			</tr>
			<input type="hidden" name="prodType" value=${prodType} />
			<input type="hidden" name="prodId" value=${prodId} />
		  </table> 
		</form>
	</div>
 
</body>
</html>