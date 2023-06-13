<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags" %>

<html>
<head>
    <title>회원가입 정보</title>
</head>
<a style="margin-left: 300px" href="http://localhost:3000">홈</a>
<%
    if(session.getAttribute("loginID") != null) {

        String loginID = session.getAttribute("loginID").toString();
    System.out.println(loginID);
%>
<a href="http://localhost:8080/logout">로그아웃</a>
<%} else {%>
<a href="http://localhost:8080/signin">로그인</a>
<%}%>

<p>회원가입 정보</p>
<p> <spring:message code="id" />: ${user.id} </p>
<p> <spring:message code="name" />: ${user.name} </p>
<p> <spring:message code="email" />: ${user.email} </p>
<p> <spring:message code="tel" />: ${user.tel} </p>
<p> <spring:message code="address" />: ${user.address} </p>
<p> <spring:message code="birthdate" />: ${user.birthdate} </p>
<body>

</body>
</html>
