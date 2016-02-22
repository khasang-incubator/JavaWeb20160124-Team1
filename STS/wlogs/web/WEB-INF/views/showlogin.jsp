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
    <h2>Поиск по пользователям</h2>
    </legend>
    <fieldset>
    <legend>
    <form method="post" action="">
        <label for="nick_name">Никнейм для поиска:</label>
        <br/>
        <legend>
        <input type="text" name="nick_name" size="30">
        <br/>
        </legend>
    <button id="submit" type="submit" class="btn btn-primary btn-lg">
        Найти
    </button>
    </legend>
    <br/>
    </form>
</div>
</fieldset>
<fieldset>
    <legend>
    <form method="post" action="">
    <button id="submit" type="submit" class="btn btn-primary btn-lg">
        Показать всех пользователей
    </button>
    </legend>
    <br/>
    </form>
</fieldset>
</body>
</html>