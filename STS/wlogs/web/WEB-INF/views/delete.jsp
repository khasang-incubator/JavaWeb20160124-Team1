<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:directive.page contentType="text/html;charset=UTF-8" language="java"/>
<jsp:directive.include file="part_header.jsp"/>

    <h2>Удаление записей лога</h2>
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
                <option value="1">Оставить только последних 1000 записей</option>
                <option value="2">Удалить записи старше одного месяца</option>
                <option value="3">Удалить записи старше трех месяцев</option>
                <option value="4">Удалить записи старше шести месяцев</option>
                <option value="5">Удалить записи старше девяти месяцев</option>
                <option value="6">Удалить записи старше одного года</option>
            </select>
        </div>
        <div class="alert alert-warning form-group" role="alert">
            <h4><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span> Внимание!</h4>
            Будет произведено удаление записей логов из базы данных. Данную операцию нельзя отменить и данные будут безвозвратно уничтожены.
            <p>
                <label for="i_am_understand">
                    <input type="checkbox" id="i_am_understand" name="i_am_understand" required="required"/>
                    <strong>Я понимаю, продолжить.</strong>
                </label>
            </p>
        </div>
        <div class="form-group">
            <input type="submit" value="Удалить" class="btn btn-danger btn-lg">
        </div>
    </form>

<jsp:directive.include file="part_footer.jsp"/>