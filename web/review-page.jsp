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
    <title>Review Page</title>
</head>
<body>
<div style="display: flex;
  flex-direction: column;
  height: 100vh;
  justify-content: space-between;" class="flex">
    <c:import url="header.jsp"/>

    <div style=" display:  flex; flex-direction: column;" class="container">

        <div class="wrapper"
             style="display: flex;justify-content: space-around; flex-direction:row; width: 100%; justify-items:center;">
            <div class="content" style="width: 100%;  margin: 10px;">
                <p style=" width: 80%; height: 100px; text-align:center;color: black;font-size: 20px;border: 1px solid #dcd8d8;background: #00000014;">
                    ${reviewedQuestion.body}
                <form style=" stext-align: start;/* background: #ffa5001f; */color: #007bff;font-size: 18px;"
                      action="/game">
                    <fmt:message key="review.given.answer"/> - ${reviewedPhase.givenAnswer}
                    <fmt:message key="review.correct.answer"/> - ${reviewedQuestion.correctAnswer}
                </form>
                <div class="flex" style="display: flex;">
                    <form style="text-align: start" action="/game">
                        <input type="hidden" name="command" value="judge-rightAnswer">
                        <input class="btn btn-success " type="submit" value="Right">
                    </form>
                    <form style="text-align: end; margin-left: 20px;" action="/game">
                        <input type="hidden" name="command" value="judge-wrongAnswer">
                        <input class="btn btn-success " type="submit" value="Wrong">
                    </form>

                </div>
            </div>
            <div class="content" style=" display: flex; margin: 10px; flex-direction: column ; width: 100%">
                <c:if test="${hintUsed == true}">
                    <fmt:message key="review.hint.used"/>
                </c:if>
                <div style="background: rgba(58, 146, 255, 0.35);width: 100%; font-style:oblique; border: 1px solid #00000029;"
                     class="hintBody">
                    <p><fmt:message key="review.start"/> - ${reviewedPhase.startTime} </p>
                    <p><fmt:message key="review.end"/> - ${reviewedPhase.endTime}</p>
                    <p><fmt:message key="review.deadline"/> - ${reviewedPhase.deadline}</p>
                </div>
            </div>
            <div style="width: 20%"></div>
        </div>
    </div>

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
