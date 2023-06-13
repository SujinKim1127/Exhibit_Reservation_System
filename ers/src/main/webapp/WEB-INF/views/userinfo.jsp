<%@ page import="com.ers.model.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta charset="UTF-8"> <html>
<head> <title>UserInfo</title> </head>
<% request.setCharacterEncoding("UTF-8"); %>
<%
    User user = (User) session.getAttribute("loginUser");
%>
<a style="margin-left: 300px" href="http://localhost:3000">홈</a>
<%
    String loginID = session.getAttribute("loginID").toString();
    System.out.println(loginID);
    if(loginID != null) {
%>
<a href="http://localhost:8080/logout">로그아웃</a>
<%} else {%>
<a href="http://localhost:8080/signin">로그인</a>
<%}%>

<body>
<c:if test="${! empty authInfo}">
    <P> ${authInfo.userid}님 정보 </P>
    <p> <spring:message code="name" />: <%=user.getName()%> </p>
    <p> <spring:message code="email" />: <%=user.getEmail()%> </p>
    <p> <spring:message code="address" />: <%=user.getAddress()%> </p>
    <p> <spring:message code="tel" />: <%=user.getTel()%> </p>
    <p> <spring:message code="birthdate" />: <%=user.getBirthdate()%> </p>
    <a href="/signin"> 뒤로 </a>
</c:if>
</body>
</html>