<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>회원가입</title> </head>
<body>
<% request.setCharacterEncoding("UTF-8"); %>
<a style="margin-left: 300px" href="http://localhost:3000">홈</a>
<%
    if(session.getAttribute("loginID") != null){
    String loginID = session.getAttribute("loginID").toString();
    System.out.println(loginID);
    if(loginID != null) {
%>
<a href="./logout">로그아웃</a>
<%
} else {
%>
<a href="http://localhost:8080/logout">로그아웃</a>
<%} }else {%>
<a href="http://localhost:8080/signin">로그인</a>
<%}%>

<P> <spring:message code="signupmsg" /> </P>
<form:form action="/signup/submit" modelAttribute="user">
    <p> <label> <spring:message code="id" />:<br>
        <form:input path="id" />
        <form:errors path="id" /> </label> </p>
    <p> <label> <spring:message code="password" />:<br>
        <form:input type="password" path="password" /> <form:errors path="password" /> </label> </p>
    <p> <label> <spring:message code="name" />:<br>
        <form:input path="name" /> <form:errors path="name" /> </label> </p>
    <p> <label> <spring:message code="email" />:<br>
        <form:input path="email" /> <form:errors path="email" /> </label> </p>
    <p> <label> <spring:message code="tel" />:<br>
        <form:input path="tel" /> <form:errors path="tel" /> </label> </p>
    <p> <label> <spring:message code="address" />:<br>
        <form:input path="address" /> <form:errors path="address" /> </label> </p>
    <p> <label> <spring:message code="birthdate" />:<br>
        <form:input path="birthdate" /> <form:errors path="birthdate" /> </label> </p>
    <button class="btn" type="submit"> 제출 </button> </form:form>
</body>
</html>