<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="exhibitDetail" /></title>
</head>
<body>
<h2><spring:message code="exhibitDetail" /></h2>
<p>
  <img src="${exhibit.img}" alt="전시 대표 이미지" />
</p>
<p>
  <spring:message code="exhibitTitle" /> : ${exhibit.title}
</p>
<p>
  <spring:message code="exhibitContents" /> : ${exhibit.contents}
</p>
<p>
  ${exhibit.start_date} ~ ${exhibit.end_date}
</p>
<p>
  <spring:message code="price" /> : ${exhibit.price}
</p>
<p>
  <spring:message code="owner" /> : ${exhibit.owner}
</p>
<p>
  <spring:message code="likes" /> : ${exhibit.likes}
</p>
</body>
</html>
