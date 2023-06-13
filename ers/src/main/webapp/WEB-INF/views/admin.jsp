<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>관리자 전용: 전시 등록</title>
</head>
<body>
<% request.setCharacterEncoding("UTF-8"); %>
<P> 전시 등록 </P>
<form:form action="/admin/submit" modelAttribute="exhibit">
    <p> <label> <spring:message code="title" />:<br>
        <form:input path="title" />
        <form:errors path="title" /> </label> </p>
    <p> <label> <spring:message code="contents" />:<br>
        <form:input path="contents" /> <form:errors path="contents" /> </label> </p>
    <p> <label> <spring:message code="img" />:<br>
        <form:input path="img" /> <form:errors path="img" /> </label> </p>
    <p> <label> <spring:message code="start_date" />:<br>
        <form:input path="start_date" /> <form:errors path="start_date" /> </label> </p>
    <p> <label> <spring:message code="end_date" />:<br>
        <form:input path="end_date" /> <form:errors path="end_date" /> </label> </p>
    <p> <label> <spring:message code="price" />:<br>
        <form:input path="price" /> <form:errors path="price" /> </label> </p>
    <p> <label> <spring:message code="owner" />:<br>
        <form:input path="owner" /> <form:errors path="owner" /> </label> </p>
    <button class="btn" type="submit"> 제출 </button> </form:form>
</body>
</html>
