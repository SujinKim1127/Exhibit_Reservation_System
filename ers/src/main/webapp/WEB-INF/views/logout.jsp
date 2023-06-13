<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta charset="UTF-8"> <html>
<head> <title>Logout</title> </head>
<% request.setCharacterEncoding("UTF-8"); %>
<body>
<a style="margin-left: 300px" href="http://localhost:3000">홈</a>
<P> <spring:message code="logout" /> </P>
<a href="./signin"> 뒤로.. </a>
</body>
</html>