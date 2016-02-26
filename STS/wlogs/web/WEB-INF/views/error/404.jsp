<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:directive.page contentType="text/html;charset=UTF-8" language="java"/>
<jsp:directive.include file="../part_header.jsp"/>

<div class="row error">
    <div class="col-md-12">
        <div class="jumbotron">
            <div class="container">
                <h1>404</h1>
                <p>ОЙ! Ресурс не найден. Может стоит проверить @RequestMapping в контроллере?</p>
            </div>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <table class="table table-stripped table-hover">
            <tbody>
                <tr>
                    <td>Код ответа сервера</td>
                    <td>${statusCode}</td>
                </tr>
                <tr>
                    <td>Сообщение сервера</td>
                    <td>${exceptionMessage}</td>
                </tr>
                <tr>
                    <td>Запрошенный ресурс</td>
                    <td>${requestedUri}</td>
                </tr>
            </tbody>
        </table>
    </div>
</div>

<jsp:directive.include file="../part_footer.jsp"/>