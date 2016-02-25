<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <style type="text/css">
        input {
            padding-left: 5px;
            margin-bottom: 5px;
        }
    </style>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" href="css/style.css"/>
    <title>wlogs project</title>
</head>
<body>
<%@ include file="/WEB-INF/views/header.jsp" %>
<div class="container">
    <div class="content" align="center" style="width: 780px;">
        <h1>Login page</h1>
        <form action="login" method="post">
            Account <br>
            <select size="1">
                ${users}
                <%--<option>smth</option>--%>
            </select>
            <br> Password <br>
            <input type="password" name="password" id="password"/>
            <br>
            <input type="submit" value="Log in"/>
            <br>
            <button>
                Create table with users
            </button>
            <br>
            ${login}
        </form>
        <!-- <a href="registration">Regestration</a> -->
    </div>
</div>
</body>
</html>