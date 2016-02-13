<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:directive.page contentType="text/html;charset=UTF-8" language="java"/>
<jsp:directive.include file="part_header.jsp"/>

    <h2>Просмотр логов</h2>
    <div>
        <table class="table table-striped table-bordered table-hover logs_list">
            <thead>
            <tr>
                <th>#</th>
                <th>Дата события</th>
                <th>Источник</th>
                <th>Уровень</th>
                <th>Описания</th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${!empty logs}">
                    <c:forEach items="${logs}" var="logModel" varStatus="loop">
                        <tr>
                            <td>${loop.index + 1}</td>
                            <td><fmt:formatDate value="${logModel.getOccurredAt()}" type="both"/></td>
                            <td>${logModel.getErrorSource()}</td>
                            <td>${logModel.getErrorLevel()}</td>
                            <td class="break_all">${logModel.getErrorDescription()}</td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="5">Записей нет</td>
                    </tr>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </div>

<jsp:directive.include file="part_footer.jsp"/>