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
        <div class="row">
            <div class="col-md-12">
                <h4>Удалить записи лога старше заданного интервала:</h4>
            </div>
        </div>
        <div class="row">
            <div class="col-md-3">
                <label for="date_interval_size">Размер интервала:</label>
                <select name="date_interval_size" id="date_interval_size" class="form-control">

                </select>
            </div>
            <div class="col-md-4">
                <label for="date_interval_id">Тип интервала:</label>
                <select name="date_interval_id" id="date_interval_id" class="form-control">
                    <c:forEach items="${dateIntervalTypesMap}" var="entry">
                        <option value="${entry.key}">${entry.value}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <br/>
                <div class="alert alert-info form-group" role="alert">
                    <h4><span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span> Пояснение:</h4>
                    Будут удалены все записи логи старше заданного интервала времени, например, если выбрать старше 2-х месяцев, то из БД будут удалены все записи чья дата создания находится раньше 2-х недель, если считать от текущей даты.
                </div>
                <div class="alert alert-warning form-group" role="alert">
                    <h4><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span> Внимание!</h4>
                    На данный момент в базе данных количетсво записей составляет: <strong>${logRecordsTotal}</strong>. Если количество записей большое, то при использовании фильтра по интервалу может привести к переполнению дискового пространства на сервере СУБД. В таком случае рекомендутеся полная очистка.
                </div>
            </div>
        </div>
        <hr/>
        <div class="row">
            <div class="col-md-12">
                <div class="alert alert-danger col-md-12" role="alert">
                    <h4><span class="glyphicon glyphicon-fire" aria-hidden="true"></span> Тотальное уничтожение!</h4>
                    <p>Будет произведена полная очистка таблицы с логами. Фильтр по интервалу не учитывается.</p>
                    <p>
                        <label for="total_annihilation">
                            <input type="checkbox" id="total_annihilation" name="total_annihilation"/>
                            <strong>Снести все!</strong>
                        </label>
                    </p>
                </div>
            </div>
        </div>
        <hr/>
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