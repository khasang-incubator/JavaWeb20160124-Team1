<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>СОздание таблицы</title>
    <link href="css/join.css" rel="stylesheet" type="text/css">
</head>
<body>
<h1>Вы хотите создать таблицу?</h1>
<h2>${result}</h2>

<form action="/createtablesorlov">
    <button type="submit">Да</button>
    <button formaction="/showtables">Показать таблицы</button>
    <button formaction="/">Назад</button>
</form>

</body>
</html>
