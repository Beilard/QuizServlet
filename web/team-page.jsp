<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="internationalization"/>

<html lang="${param.language}">
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css"
          integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">
    <style>
        @import url(https://fonts.googleapis.com/css?family=Raleway:300,400,600);
        /* Coded with love by Mutiullah Samim */
        body,
        html {
            margin: 0;
            padding: 0;
            height: 100%;
        }

        .user_card {
            height: 400px;
            width: 350px;
            margin-top: auto;
            margin-bottom: auto;
            background: #f39c12;
            position: relative;
            display: flex;
            justify-content: center;
            flex-direction: column;
            padding: 10px;
            box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
            -webkit-box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
            -moz-box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
            border-radius: 5px;

        }

        .brand_logo_container {
            position: absolute;
            height: 170px;
            width: 170px;
            top: -75px;
            border-radius: 50%;
            background: #60a3bc;
            padding: 10px;
            text-align: center;
        }

        .brand_logo {
            height: 150px;
            width: 150px;
            border-radius: 50%;
            border: 2px solid white;
        }

        .form_container {
            margin-top: 100px;
        }

        .login_btn {
            width: 100%;
            background: #c0392b !important;
            color: white !important;
        }

        .login_btn:focus {
            box-shadow: none !important;
            outline: 0px !important;
        }

        .login_container {
            padding: 0 2rem;
        }

        .input-group-text {
            background: #c0392b !important;
            color: white !important;
            border: 0 !important;
            border-radius: 0.25rem 0 0 0.25rem !important;
        }

        .input_user,
        .input_pass:focus {
            box-shadow: none !important;
            outline: 0px !important;
        }

        .custom-checkbox .custom-control-input:checked ~ .custom-control-label::before {
            background-color: #c0392b !important;
        }


        body {
            margin: 0;
            font-size: .9rem;
            font-weight: 400;
            line-height: 1.6;
            color: #212529;
            text-align: left;
            background-color: #f5f8fa;
        }

        .navbar-laravel {
            box-shadow: 0 2px 4px rgba(0, 0, 0, .04);
        }

        .navbar-brand, .nav-link, .my-form, .login-form {
            font-family: Raleway, sans-serif;
        }

        .my-form {
            padding-top: 1.5rem;
            padding-bottom: 1.5rem;
        }

        .my-form .row {
            margin-left: 0;
            margin-right: 0;
        }

        .login-form {
            padding-top: 1.5rem;
            padding-bottom: 1.5rem;
        }

        .login-form .row {
            margin-left: 0;
            margin-right: 0;
        }

        .flex {


        }
    </style>
    <title>Team Page</title>
</head>
<body>
<div style="display: flex;
  flex-direction: column;
  height: 100vh;
  justify-content: space-between;" class="flex">
    <c:import url="header.jsp"/>

    <c:if test="${user.getCaptain() == true}">
    <form action="#" style="margin-right: 30px;display: flex;flex-direction: column;">
        <label for="newCaptainEmail">
            <p style="text-align: center; font-size: 20px; font-style: oblique;">
                <fmt:message key="team.change.captain"/>
            </p>
        </label>
        <input style="margin: 0 auto; width: 20%" name="newCaptainEmail" id="newCaptainEmail" type="text" placeholder="Email" required pattern="^(?=.{4,31}$)\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,4}$">
        <div style="margin-top:10px;text-align: center;width: 100%; ">
            <input type="hidden" name="command" value="player-changeCaptain">
            <input type="submit" style="width: 10%; align-content: center" class="btn btn-warning" value=<fmt:message key="team.change.button"/>>
        </div>
    </form>
    </c:if>

    <form action="#" style="margin-right: 30px">
        <div style="margin-top: -40px; width:100%; text-align: center ">
            <input type="hidden" name="command" value="player-leaveTeam">
            <input type="submit" style="width: 10%;" class="btn btn-warning" value=<fmt:message key="team.leave.button"/>>
        </div>
    </form>

    <c:if test="${isCaptainText == true}">
    <div>
        <p style="color: darkred; font-size: 20px; text-align: center">
            <fmt:message key="team.captain.leaving"/>
        </p>
    </div>
    </c:if>

    <div style="background: red; " class="container">
    </div>
    <table class="table table-striped table-responsive-md btn-table">
        <thead>
        <tr>
            <th><fmt:message key="team.user.email"/></th>
            <th><fmt:message key="team.user.name"/></th>
            <th><fmt:message key="profile.user.surname"/></th>
            <th><fmt:message key="profile.user.captain"/></th>

        </tr>
        </thead>

        <tbody>
        <c:forEach items="${usersOfTeam}" var="user">

            <tr>
                <td> ${user.email}</td>
                <td> ${user.name}</td>
                <td> ${user.surname}</td>
                <td> ${user.getCaptain()}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div>


        <c:import url="footer.jsp"/>
    </div>

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
