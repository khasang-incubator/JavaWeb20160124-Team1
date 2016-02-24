<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:directive.page contentType="text/html;charset=UTF-8" language="java"/>
<jsp:directive.include file="part_header.jsp"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<DOCTYPE html>
<html>
<head>
    <title>Все пользователи</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="/css/style.css">

</head>
<body>
<div id="body">
    <legend>
    <center>
        <h2>Все пользователи</h2>
    </center>
    </legend>
    <table align="center" class="table table-striped table-bordered table-hover logs_list">
        <tr>
            <th><center>ID</center></th>
            <th><center>Occurred At</center></th>
            <th><center>Error Level</center></th>
            <th><center>Error Source</center></th>
            <th><center>Error Description</center></th>
        </tr>
        <c:forEach items="${allusers}" var="allusers">
            <tr>
                <td><c:out value="${allusers.id}"/></td>
                <td><c:out value="${allusers.occurredAt}"/></td>
                <td><c:out value="${allusers.errorLevel}"/></td>
                <td><c:out value="${allusers.errorSource}"/></td>
                <td class="break_all"><c:out value="${allusers.errorDescription}"/></td>
            </tr>
        </c:forEach>
    </table>
    </br>
    <fieldset>
        <center>
        <legend>
            <form action="/showlogin">
                <button id="submit" type="submit" class="btn btn-primary btn-lg">
                    Вернуться к поиску
                </button>
            </form>
        </legend>
        </center>
    </fieldset>
</div>
</body>
</html>

<jsp:directive.include file="part_footer.jsp"/>