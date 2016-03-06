<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:directive.page contentType="text/html;charset=UTF-8" language="java"/>
<jsp:directive.include file="part_header.jsp"/>

    <h2>
        <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
        Удаление записей из логов
        <span class="label label-info">всего: ${logRecordsTotal}</span>
    </h2>
    <hr/>
    <div>
        <c:if test="${null != success}">
            <div class="alert alert-success alert-dismissible" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <strong>Операция прошла успешно!</strong> ${success}
            </div>
        </c:if>
        <c:if test="${null != error}">
            <div class="alert alert-danger alert-dismissible" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <strong>Произошла ошибка!</strong> ${error}
            </div>
        </c:if>
    </div>
    <h2></h2>
    <form action="/delete" method="post" class="">
        <div class="form-group">
            <label for="period_criteria_id">Выбирете период:</label>
            <select name="period_criteria_id" id="period_criteria_id" class="form-control">
                <c:forEach items="${dateCriteriaHashMap}" var="entry">
                    Key = ${entry.key}, value = ${entry.value}<br>
                    <option value="${entry.key}">${entry.value}</option>
                </c:forEach>
            </select>
        </div>
        <div class="alert alert-warning form-group" role="alert">
            <h4><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span> Внимание!</h4>
            Будет произведено удаление записей логов из базы данных. Данную операцию нельзя отменить и данные будут безвозвратно уничтожены.
            <p>
                <label for="understand_terms">
                    <input type="checkbox" id="understand_terms" name="understand_terms"/>
                    <strong>Я понимаю, продолжить.</strong>
                </label>
            </p>
        </div>
        <div class="form-group">
            <input type="submit" value="Удалить" class="btn btn-danger btn-lg">
        </div>
    </form>

<jsp:directive.include file="part_footer.jsp"/>