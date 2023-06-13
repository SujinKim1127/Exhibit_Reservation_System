<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title><spring:message code="loginresult" /></title> </head>
<body>

<a style="margin-left: 300px" href="http://localhost:3000">홈</a>
<%
    String loginID = session.getAttribute("loginID").toString();
    System.out.println(loginID);
    if(loginID != null) {
%>

<a href="./logout">로그아웃</a>
<%
} else {
%>
<a href="./signin">로그인</a>
<%}%>


<p>${loginInfo.userid}님 로그인 성공!!</p>
<a href="/signin"> 뒤로 </a>

</body>
</html>