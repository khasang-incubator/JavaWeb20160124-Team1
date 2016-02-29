<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:directive.page contentType="text/html;charset=UTF-8" language="java"/>
<jsp:directive.include file="part_header.jsp"/>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <title>Show Login</title>
</head>
<body>
<div id="body">
    <legend>
        <center>
    <h2>Поиск по пользователям</h2>
        </center>
    </legend>
    <fieldset>
    <legend>
        <form role="form" class="form-inline" action="/showlogin/selectuser">
            <div class="form-group">
                <input name="login" type="text" value="${selectuser}" class="form-control" placeholder="Поиск"/>
            </div>
            <div class="form-group">
                <input type="submit" class="btn btn-primary btn-lg" value="Найти" />
            </div>
        </form>
    </legend>
    <br/>
</fieldset>
</div>
<fieldset>
    <legend>
    <form action="/showlogin/allusers">
    <button id="submit" type="submit" class="btn btn-primary btn-lg">
        Показать всех пользователей
    </button>
    </form>
    </legend>
    <br/>
</fieldset>
</body>
</html>