<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="exhibitlist" /></title>
</head>
<body>
<h2><spring:message code="exhibitlist" /></h2>
<table border="1" cellpadding="10">
    <tr>
        <th><spring:message code="exhibitTitle" /></th>
        <th><spring:message code="start_date" /></th>
        <th><spring:message code="end_date" /></th>
        <th><spring:message code="price" /></th>
        <th><spring:message code="owner" /></th>
        <th><spring:message code="likes" /></th>
    </tr>
    <c:forEach items="${exhibitlist}" var="exhibit">
        <tr>
            <td><a href="/exhibitdetail/${exhibit.exhibit_id}">${exhibit.title}</a></td>
            <td>${exhibit.start_date}</td>
            <td>${exhibit.end_date}</td>
            <td>${exhibit.price}</td>
            <td>${exhibit.owner}</td>
            <td>${exhibit.likes}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
