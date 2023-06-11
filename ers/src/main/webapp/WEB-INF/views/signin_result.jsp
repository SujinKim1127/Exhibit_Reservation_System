<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title><spring:message code="loginresult" /></title> </head>
<body>

<p> <spring:message code="userid" />: ${loginInfo.userid} </p>

</body>
</html>