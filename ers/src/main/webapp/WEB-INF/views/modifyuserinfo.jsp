<%@ page import="com.ers.model.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta charset="UTF-8"> <html>
<head> <title>회원정보수정</title> </head>
<% request.setCharacterEncoding("UTF-8"); %>
<body>
<%
  User user = (User) session.getAttribute("loginUser");
%>
<c:if test="${! empty authInfo}">
  <P> 회원정보수정 </P>
  <form:form action="submituserinfo" modelAttribute="user">
    <p> <label> <spring:message code="id" />:<br> <form:input value="<%=user.getId()%>" readonly="true" path="id" />
      <form:errors path="id" /> </label> </p>
    <p> <label> <spring:message code="password" />:<br> <form:input path="password" />
      <form:errors path="password" /> </label> </p>
    <p> <label> <spring:message code="name" />:<br> <form:input path="name" />
      <form:errors path="name" /> </label> </p>
    <p> <label> <spring:message code="email" />:<br>
    <form:input path="email" />
    <form:errors path="email" /> </label> </p>
    <p> <label> <spring:message code="address" />:<br> <form:input path="address" />
      <form:errors path="address" /> </label> </p>
    <p> <label> <spring:message code="tel" />:<br> <form:input path="tel" />
      <form:errors path="tel" /> </label> </p>
    <p> <label> <spring:message code="birthdate" />:<br> <form:input path="birthdate" />
      <form:errors path="birthdate" /> </label> </p>
    <button class="btn" type="submit"> <spring:message code="submit" /> </button>
  </form:form>
</c:if>
</body>
</html>