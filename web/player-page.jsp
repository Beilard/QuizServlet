<%--
  Created by IntelliJ IDEA.
  User: Ferox
  Date: 10-Jan-20
  Time: 22:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="internationalization"/>
<html lang="${param.language}">
<head>
    <style>
        /*!
 * Start Bootstrap - Simple Sidebar (https://startbootstrap.com/template-overviews/simple-sidebar)
 * Copyright 2013-2019 Start Bootstrap
 * Licensed under MIT (https://github.com/BlackrockDigital/startbootstrap-simple-sidebar/blob/master/LICENSE)
 */
        body {
            overflow-x: hidden;
        }

        #sidebar-wrapper {
            min-height: 100vh;
            margin-left: -15rem;
            -webkit-transition: margin .25s ease-out;
            -moz-transition: margin .25s ease-out;
            -o-transition: margin .25s ease-out;
            transition: margin .25s ease-out;
        }

        #sidebar-wrapper .sidebar-heading {
            padding: 0.875rem 1.25rem;
            font-size: 1.2rem;
        }

        #sidebar-wrapper .list-group {
            width: 15rem;
        }

        #page-content-wrapper {
            min-width: 100vw;
        }

        #wrapper.toggled #sidebar-wrapper {
            margin-left: 0;
        }

        @media (min-width: 768px) {
            #sidebar-wrapper {
                margin-left: 0;
            }

            #page-content-wrapper {
                min-width: 0;
                width: 100%;
            }

            #wrapper.toggled #sidebar-wrapper {
                margin-left: -15rem;
            }
        }

    </style>
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css"
          integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">
</head>
<body>
<c:import url="header.jsp"/>
<div class="flex" STYLE=" display: flex;">
    <div style="width: 20%;" class="bg-light border-right" id="sidebar-wrapper">
        <div class="sidebar-heading"><fmt:message key="player.profile.header"/></div>
        <div class="list-group list-group-flush">
            <c:if test="${user.getTeamId() == 0}">
                <a href="/game?command=player-createTeamForm"
                   class="list-group-item list-group-item-action bg-light"><fmt:message key="player.createTeam"/></a>
            </c:if>
            <c:if test="${user.getTeamId() != 0}">
                <a href="/game?command=player-checkTeam"
                   class="list-group-item list-group-item-action bg-light"><fmt:message
                        key="player.checkTeam"/></a>
            </c:if>
            <c:if test="${user.getCaptain() == true}">
                <a href="/game?command=player-configureGameForm"
                   class="list-group-item list-group-item-action bg-light"><fmt:message key="player.startGame"/></a>
            </c:if>
            <c:if test="${user.getRole().ordinal() == 1}">
                <a href="/game?command=judge-viewAllGames&page=1"
                   class="list-group-item list-group-item-action bg-light"><fmt:message key="judge.viewAllGames"/></a>
            </c:if>
        </div>
    </div>
    <c:if test="${user.getTeamId() != 0}">
        <div class="flex"
             style="display: flex;flex-direction:column; width:100%;margin-right: 30px;text-align: center;">
            <form action="#" style="display: flex;flex-direction: column;margin-right: 30px;text-align: center;">
                <label for="joinGameId">
                    <p>
                        <fmt:message key="profile.join.game"/>
                    </p>
                </label>
                <input style="width: 20%; margin:  0 auto" name="joinGameId" id="joinGameId" type="text">
                <div style="margin-top:10px;text-align: center;width: 100%; ">
                    <input type="hidden" name="command" value="player-joinGame">
                    <input type="submit" class="btn btn-warning" style="width: 10%;" value=<fmt:message key="player.profile.join.button"/>>
                </div>
                <c:if test="${incorrectId == true}">
                    <div style="background: rgba(251, 213, 217, 0.5);width: 25%;height: 40px;border: 1px solid #00000029;margin: auto;"
                         class="incorrectId">
                        <fmt:message key="profile.wrongId"/>
                    </div>
                </c:if>
            </form>
            <table class="table table-striped table-responsive-md btn-table">
                <thead>
                <tr>
                    <th><fmt:message key="profile.pagination.gameId"/></th>
                    <th><fmt:message key="profile.pagination.currentPhase"/></th>
                    <th><fmt:message key="profile.pagination.status"/></th>

                </tr>
                </thead>

                <tbody>
                <c:forEach items="${allGamesOfTeam}" var="game">

                    <tr>
                        <td> ${game.id}</td>
                        <td> ${game.currentPhase}</td>
                        <td> ${game.status}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <table cellpadding="5" cellspacing="5">
                <tr>
                    <c:forEach begin="1" end="${countOfElements}" var="i">
                        <c:choose>
                            <c:when test="${currentPage eq i}">
                                <td><p class="btn btn-primary">${i}</p></td>
                            </c:when>
                            <c:otherwise>
                                <td><a class="btn btn-outline-success " style="color: black;"
                                       href="/game?command=player-profilePageForm&page=${i}">${i}</a>
                                </td>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </tr>
            </table>
        </div>
    </c:if>
</div>
<c:import url="footer.jsp"/>


<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>
</body>

</html>
