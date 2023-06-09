<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<meta charset="UTF-8"> <html>
<head>
    <title>Login Page</title> </head>
<% request.setCharacterEncoding("UTF-8"); %>
<body>
<c:if test="${empty authInfo}">
    <a style="margin-left: 300px" href="http://localhost:3000">홈</a>
    <P> <spring:message code="loginmesg" /> </P>
    <form:form action="/signin/submit" modelAttribute="loginInfo">
        <p> <label>
            <spring:message code="userid" />:<br>
            <form:input path="userid" />
            <form:errors path="userid" />
        </label>
            <br>
            <spring:message code="rememberid" />
            <form:checkbox path="rememberid" /> </label> </p>
        <p> <label> <spring:message code="pwd" />:<br>
            <form:password path="pwd" />
            <form:errors path="pwd" /> </label> </p>
        <button class="btn" type="submit"> <spring:message code="submit" /> </button> </form:form>
        <a href="./signup">회원가입 하러가기</a>
</c:if>
<c:if test="${! empty authInfo}">
    <a style="margin-left: 300px" href="http://localhost:3000">홈</a>
    <a href="./logout">로그아웃</a>
    <P> ${authInfo.userid}님 환영합니다. </P>
    <a href="./userInfo/${authInfo.userid}">${authInfo.userid}님 정보</a><br>
    <a href="./modifyUserInfo">회원정보 수정</a><br><br>
</c:if>
<%
    if(session.getAttribute("loginID") != null){
if (session.getAttribute("loginID").toString().equals("admin")) {
    %>
<a href="./admin">전시 등록 하기</a>
<%
}}
%>
</body>
</html>