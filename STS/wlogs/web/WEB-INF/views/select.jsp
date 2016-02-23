<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Select table page</title>
    <link rel="stylesheet" type="text/css" href="css/style.css"/>
    <style>
        table, th, td {
            border: 1px solid black;
        }
    </style>
</head>
<body>
<p style="text-align: center">View data from logs table</p>
<table align="center">
    <tr>
        <th>ID</th>
        <th>user</th>
        <th>url</th>
        <th>timespent</th>
    </tr>
    <c:forEach items="${items}" var="productorder">
        <tr>
            <td><c:out value="${productorder.id}"/></td>
            <td><c:out value="${productorder.user}"/></td>
            <td><c:out value="${productorder.url}"/></td>
            <td><c:out value="${productorder.timespent}"/></td>
        </tr>
    </c:forEach>
</table>
<form>
    <p style="text-align: center">
        <button onclick="window.location.reload();">
    <p>Menu</p>
    <button onclick="window.location.reload();"><p>Select</p></button>
    <button onclick="window.location.reload();"><p>Logoff</p></button>
</form>
</body>
</html>
