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
<h1 align="center">Joined tables '${tableName1}' and  '${tableName2}'</h1>
<table>
    <tr>
        <th>ID</th>
        <th>Date</th>
        <th>Source</th>
        <th>Level</th>
        <th>Description</th>
        <th>Critical</th>
    </tr>
    <c:forEach items="${joinedTbl}" var="tblRow">

        <tr>
            <td><c:out value="${tblRow.id}"/></td>
            <td><c:out value="${tblRow.occurredAt}"/></td>
            <td><c:out value="${tblRow.errorSource}"/></td>
            <td><c:out value="${tblRow.error_level}"/></td>
            <td><c:out value="${tblRow.errorDescription}"/></td>
            <td><c:out value="${tblRow.critical}"/></td>
        </tr>
    </c:forEach>
</table>


<form action="/createtblQuestion">
    <button type="submit">Назад</button>
</form>

</body>
</html>