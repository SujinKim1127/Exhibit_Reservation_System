<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>전시 등록 완료</title>
</head>
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

<body>
<p>전시 등록 완료</p>
<a href="/"> 메인으로 </a>

</body>
</html>
