<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags" %>

<html>
<head>
    <title>회원가입 정보</title>
</head>
<p>회원가입 정보</p>
<p> <spring:message code="id" />: ${user.id} </p>
<p> <spring:message code="name" />: ${user.name} </p>
<p> <spring:message code="email" />: ${user.email} </p>
<p> <spring:message code="tel" />: ${user.tel} </p>
<p> <spring:message code="address" />: ${user.address} </p>
<p> <spring:message code="birthdate" />: ${user.birthdate} </p>
<a href="./home"> 뒤로.. </a>
<body>

</body>
</html>
