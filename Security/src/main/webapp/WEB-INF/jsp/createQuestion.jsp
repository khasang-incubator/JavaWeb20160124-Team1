<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create tables</title>
</head>
<body>

<form action="/create">
    <button type="submit">Create users store</button>
    <button formaction="/">Назад</button>
</form>

<h3>Table users was created with result: ${resultUser}</h3>
<h3>Table authorities was created with result: ${resultAuthorities}</h3>
<h3>Info was inserted with result: ${resultInsert}</h3>

<table>
<tr>
    <th>${name}</th>
    <th>${password}</th>
    <th>${valid}</th><br>
</tr>
<c:forEach items="${userModel}" var="user">
    <tr>
        <td><c:out value="${user.username}"/></td>
        <td><c:out value="${user.password}"/></td>
        <td><c:out value="${user.valid}"/></td>
    </tr>
</c:forEach>
</table>
<h5>${instruction}</h5>
</body>
</html>
