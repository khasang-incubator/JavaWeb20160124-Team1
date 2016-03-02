<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:directive.page contentType="text/html;charset=UTF-8" language="java"/>
<jsp:directive.include file="../part_header.jsp"/>

<div class="row error">
    <div class="col-md-12">
        <div class="jumbotron">
            <div class="container">
                <h1>${statusCode}</h1>
                <p><strong>Ответ сервера: </strong>${exceptionMessage}</p>
                <p><strong>Запрошенный ресурс: </strong>${requestedUri}</p>
            </div>
        </div>
    </div>
</div>

<jsp:directive.include file="../part_footer.jsp"/>