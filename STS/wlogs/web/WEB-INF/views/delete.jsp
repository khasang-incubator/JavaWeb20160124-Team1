<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:directive.page contentType="text/html;charset=UTF-8" language="java"/>
<jsp:directive.include file="part_header.jsp"/>

    <h2>Удаление записей лога</h2>
    <c:if test="${null != process}">
        <h2>${process}</h2>
    </c:if>
    <h2></h2>
    <form action="/delete" method="post" class="">
        <div class="form-group">
            <label for="delete_period">Выбирете период:</label>
            <select name="delete_period" id="delete_period" class="form-control">
                <option value="only_100">Оставить только последних 100 записей</option>
                <option value="month_1">Удалить записи старше одного месяца</option>
                <option value="month_3">Удалить записи старше трех месяцев</option>
                <option value="month_6">Удалить записи старше шести месяцев</option>
                <option value="month_9">Удалить записи старше девяти месяцев</option>
                <option value="year_1">Удалить записи старше одного года</option>
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