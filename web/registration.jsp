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
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Fonts -->
    <link rel="dns-prefetch" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css?family=Raleway:300,400,600" rel="stylesheet" type="text/css">



    <link rel="icon" href="Favicon.png">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">

    <title>Registration</title>
    <style>
        @import url(https://fonts.googleapis.com/css?family=Raleway:300,400,600);


        body{
            margin: 0;
            font-size: .9rem;
            font-weight: 400;
            line-height: 1.6;
            color: #212529;
            text-align: left;
            background-color: #f5f8fa;
        }

        .navbar-laravel
        {
            box-shadow: 0 2px 4px rgba(0,0,0,.04);
        }

        .navbar-brand , .nav-link, .my-form, .login-form
        {
            font-family: Raleway, sans-serif;
        }

        .my-form
        {
            padding-top: 1.5rem;
            padding-bottom: 1.5rem;
        }

        .my-form .row
        {
            margin-left: 0;
            margin-right: 0;
        }

        .login-form
        {
            padding-top: 1.5rem;
            padding-bottom: 1.5rem;
        }

        .login-form .row
        {
            margin-left: 0;
            margin-right: 0;
        }

    </style>
    <script>
        function validform() {

            var a = document.forms["my-form"]["full-name"].value;
            var b = document.forms["my-form"]["email-address"].value;
            var c = document.forms["my-form"]["username"].value;
            var d = document.forms["my-form"]["permanent-address"].value;
            var e = document.forms["my-form"]["nid-number"].value;

            if (a==null || a=="")
            {
                alert("Please Enter Your Full Name");
                return false;
            }else if (b==null || b=="")
            {
                alert("Please Enter Your Email Address");
                return false;
            }else if (c==null || c=="")
            {
                alert("Please Enter Your Username");
                return false;
            }else if (d==null || d=="")
            {
                alert("Please Enter Your Permanent Address");
                return false;
            }else if (e==null || e=="")
            {
                alert("Please Enter Your NID Number");
                return false;
            }

        }
    </script>
</head>
<body>

<c:import url="header.jsp"/>

<main class="my-form">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header"><fmt:message key="register.title"/></div>
                    <div class="card-body">
                        <form name="my-form" onsubmit="" action="/game?command=registration" method="post">
                            <div class="form-group row">
                                <label for="email" class="col-md-4 col-form-label text-md-right"><fmt:message key="register.email"/></label>
                                <div class="col-md-6">
                                    <input type="text" id="email" class="form-control" name="email" required pattern="^(?=.{4,31}$)\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,4}$">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label for="name" class="col-md-4 col-form-label text-md-right"><fmt:message key="register.name"/></label>
                                <div class="col-md-6">
                                    <input type="text" id="name" class="form-control" name="name" required pattern="^[a-zA-Zа-яА-Яієї']{2,31}">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label for="surname" class="col-md-4 col-form-label text-md-right"><fmt:message key="register.surname"/></label>
                                <div class="col-md-6">
                                    <input type="text" id="surname" class="form-control" name="surname" required pattern="^[a-zA-Zа-яА-Яієї']{2,31}">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label for="password" class="col-md-4 col-form-label text-md-right"><fmt:message key="register.password"/></label>
                                <div class="col-md-6">
                                    <input type="password" id="password" class="form-control" name="password" required pattern="((?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})">
                                </div>
                            </div>

                            <div class="form-group row">
                                <label for=confirmPassword class="col-md-4 col-form-label text-md-right">
                                    <fmt:message key="register.confirmPassword"/></label>
                                <div class="col-md-6">
                                    <input type="password" id="confirmPassword" class="form-control" name="confirmPassword" required pattern="((?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})">
                                </div>
                            </div>

                            <div class="col-md-6 offset-md-4">
                                <button type="submit" class="btn btn-primary">
                                    <fmt:message key="register.button"/>
                                </button>
                            </div>
                    </form>
                        <c:if test="${not empty registrationMessage}">
                            <div style="background: rgba(251, 213, 217, 0.5);width: 25%;height: 40px;border: 1px solid #00000029;margin: auto;"
                                 class="registrationMessage">
                                <c:out value="${registrationMessage}"/>
                            </div>
                        </c:if>
                </div>
            </div>
        </div>
    </div>
    </div>


</main>

<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
</body>
</html>
