<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<title>Hello</title>
	</head>
	<body bgcolor="white">
	<img src="${img}">
	<h2>My name is Duke. What is yours?</h2>

	<form method="get" action="ImageServlet">
		<input type="text" name="userName" size="25">
		<input type="submit" value="Submit"> 
		<input type="reset" value="Reset">
	</form>
	<c:if test="not empty userName">
		<%@include file="response.jsp"%>
	</c:if>
</body>
</html>