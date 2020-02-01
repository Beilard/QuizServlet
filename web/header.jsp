<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="internationalization"/>

<nav class="navbar navbar-expand-lg navbar-light navbar-laravel">
    <div class="container">
        <a class="navbar-brand" href="#">Quiz</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav ml-auto">
               <c:choose>
                   <c:when test="${isLoggedIn==true}">
                       <li class="nav-item">
                           <a class="nav-link" href="/game?command=player-profilePageForm&page=1"><fmt:message key="header.profile"/></a>
                       </li>
                       <li class="nav-item">
                           <a class="nav-link" href="/game?command=logout"><fmt:message key="header.logout"/></a>
                       </li>
                   </c:when>
                   <c:otherwise>
                <li class="nav-item">
                    <a class="nav-link" href="/game?command=loginForm"><fmt:message key="header.login"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/game?command=registrationForm"><fmt:message key="header.register"/></a>
                </li>
                   </c:otherwise>
               </c:choose>
            </ul>

        </div>
        <form method="post">
            <select class="form-control select-size" id="language" name="language"
                    onchange="submit()" style="width: 120px;">
                <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
                <option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>
            </select>
        </form>
    </div>
</nav>