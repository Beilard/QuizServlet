<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="internationalization"/>

<html lang="${param.language}">
  <head>
    <meta charset="utf-8">

    <title>Homepage</title>
  </head>
  <body>
  <c:import url="header.jsp"/>
  </body>
</html>
