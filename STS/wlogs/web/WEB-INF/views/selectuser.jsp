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
                <th><center>Login</center></th>
                <th><center>Password</center></th>
                <th><center>E-mail</center></th>
                <th><center>Role</center></th>
                <th><center>Active</center></th>
            </tr>
            <c:forEach items="${selectuser}" var="selectuser">
                <tr>
                    <td class="break_all"><c:out value="${selectuser.id}"/></td>
                    <td class="break_all"><c:out value="${selectuser.login}"/></td>
                    <td class="break_all"><c:out value="${selectuser.passowrd}"/></td>
                    <td class="break_all"><c:out value="${selectuser.email}"/></td>
                    <td class="break_all"><c:out value="${selectuser.role}"/></td>
                    <td class="break_all"><c:out value="${selectuser.active}"/></td>
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