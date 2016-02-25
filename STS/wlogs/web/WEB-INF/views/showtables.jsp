<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Таблица wlogs</title>
    <link href="css/join.css" rel="stylesheet" type="text/css">
    <style>
        table, td,th {
            border: 0.1px solid brown;
            padding: 0px;
            border-collapse: collapse;
        }
    </style>
</head>
<body>
<h1 align="center">Wlogs table</h1>
<table>
    <c:forEach items="${wlogsContent}" var="tblRow">
        <tr>
            <td><c:out value="${tblRow.id}"/></td>
            <td><c:out value="${tblRow.occurredAt}"/></td>
            <td><c:out value="${tblRow.errorSource}"/></td>
            <td><c:out value="${tblRow.errorLevel}"/></td>
            <td><c:out value="${tblRow.errorDescription}"/></td>
        </tr>
    </c:forEach>
</table>
<h1 align="center">TypeError table</h1>
<table>
    <tr>
        <th>ID</th>
        <th>ERROR_LEVEL</th>
        <th>CRITICAL</th>
    </tr>
    <c:forEach items="${typeErrorContent}" var="tblRow">
        <tr align="center">
            <td><c:out value="${tblRow.id}"/></td>
            <td><c:out value="${tblRow.error_level}"/></td>
            <td><c:out value="${tblRow.critical}"/></td>
        </tr>
    </c:forEach>
</table>

<form action="/createtblQuestion">
    <button type="submit">Назад</button>
</form>

</body>
</html>
