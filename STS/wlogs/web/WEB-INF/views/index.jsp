<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:directive.page contentType="text/html;charset=UTF-8" language="java"/>
<jsp:directive.include file="part_header.jsp"/>

<!-- Prepare PAGER -->
<c:choose>
    <c:when test="${recordsPerPage >= recordsTotal}">
        <c:set var="previous" value="0"/>
        <c:set var="next" value="0"/>
    </c:when>
    <c:when test="${0 == currentOffset}">
        <c:set var="previous" value="0"/>
        <c:set var="next" value="${recordsPerPage}"/>
    </c:when>
    <c:when test="${currentOffset + recordsPerPage >= recordsTotal}">
        <c:set var="previous" value="${currentOffset - recordsPerPage}"/>
        <c:set var="next" value="0"/>
    </c:when>
    <c:otherwise>
        <c:set var="previous" value="${currentOffset - recordsPerPage}"/>
        <c:set var="next" value="${currentOffset + recordsPerPage}"/>
    </c:otherwise>
</c:choose>
<c:url var="previousUrl" value="index">
    <c:param name="offset" value="${previous}"/>
</c:url>
<c:url var="nextUrl" value="index">
    <c:param name="offset" value="${next}"/>
</c:url>

    <h2>Просмотр логов <span class="label label-info">всего: ${recordsTotal}</span></h2>
    <div>
        <nav>
            <ul class="pager">
                <li class="previous <c:if test="${0 == currentOffset}">disabled</c:if>">
                    <a href="${previousUrl}"><span aria-hidden="true">&larr;</span> Туда</a>
                </li>
                <li class="next <c:if test="${0 == next}">disabled</c:if>">
                    <a href="${nextUrl}">Сюда <span aria-hidden="true">&rarr;</span></a>
                </li>
            </ul>
        </nav>
    </div>
    <div>
        <table class="table table-striped table-bordered table-hover logs_list">
            <thead>
            <tr>
                <th>№</th>
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
                            <td>${logModel.getId()}</td>
                            <td><fmt:formatDate value="${logModel.getOccurredAt()}" type="both"/></td>
                            <td>${logModel.getErrorSource()}</td>
                            <td>${logModel.getErrorLevel()}</td>
                            <td class="break_all">${logModel.getErrorDescription()}</td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="6">Записей нет</td>
                    </tr>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </div>
    <div>
        <nav>
            <ul class="pager">
                <li class="previous <c:if test="${0 == currentOffset}">disabled</c:if>">
                    <a href="${previousUrl}"><span aria-hidden="true">&larr;</span> Туда</a>
                </li>
                <li class="next <c:if test="${0 == next}">disabled</c:if>">
                    <a href="${nextUrl}">Сюда <span aria-hidden="true">&rarr;</span></a>
                </li>
            </ul>
        </nav>
    </div>
<jsp:directive.include file="part_footer.jsp"/>