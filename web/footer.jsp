<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="internationalization"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="${param.language}">
<head>
    <title>Footer</title>
</head>
<body>
<footer style=" color: white; text-align:center; background: black; height: 4%">
    <fmt:message key="index.footer"/>
</footer>
</body>
</html>
