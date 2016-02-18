<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:directive.page contentType="text/html;charset=UTF-8" language="java"/>
<jsp:directive.include file="part_header.jsp"/>

    <h2>
        <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
        Удаление записей лога
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
    <div>
        <form:form modelAttribute="deleteDataForm" action="delete" method="post">
            <div class="row">
                <div class="col-md-6">
                    <h5>Удалить записи лога старше:</h5>
                    <div class="row">
                        <div class="col-md-4">
                            <form:select path="dateIntervalSize" cssClass="form-control">
                                <form:option value="" disabled="true" selected="selected">Более чем ...</form:option>
                            </form:select>
                        </div>
                        <div class="col-md-8">
                            <form:select path="dateIntervalType" cssClass="form-control">
                                <form:option value="" disabled="true" selected="selected">День/Неделя/Месяц ...</form:option>
                                <form:options items="${deleteDataForm.getDateIntervalTypes()}"/>
                            </form:select>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <h5>Атрибуты записей лога:</h5>
                    <div class="row">
                        <div class="col-md-6">
                            <div class="form-group">
                            <form:select path="errorSource" cssClass="form-control">
                                <form:option value="" disabled="true" selected="selected">Источник ошибок</form:option>
                                <form:options items="${errorSources}"/>
                            </form:select>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group">
                            <form:select path="errorLevel" cssClass="form-control">
                                <form:option value="" disabled="true" selected="selected">Уровень ошибок</form:option>
                                <form:options items="${errorLevels}"/>
                            </form:select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <p class="text-center font-larger">-------------------- <strong>ИЛИ</strong> --------------------</p>
            <div class="row">
                <div class="col-md-12">
                    <div class="alert alert-danger col-md-12" role="alert">
                        <h4><span class="glyphicon glyphicon-fire" aria-hidden="true"></span> Тотальное уничтожение!</h4>
                        <p>Будет произведена полная очистка таблицы с логами. Другие фильтры игнорируются.</p>
                        <p>
                            <label for="deleteAllCheckbox">
                                <form:checkbox path="deleteAll" id="deleteAllCheckbox"/>
                                <strong>Снести все!</strong>
                            </label>
                        </p>
                    </div>
                </div>
            </div>
            <div class="alert alert-warning form-group" role="alert">
                <h4><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span> Внимание!</h4>
                Будет произведено удаление записей логов из базы данных. Данную операцию нельзя отменить и данные будут безвозвратно уничтожены.
                <p>
                    <label for="agreeTermsCheckbox">
                        <form:checkbox path="agreeTerms" id="agreeTermsCheckbox"/>
                        <strong>Я понимаю, продолжить.</strong>
                    </label>
                </p>
            </div>
            <form:button name="sudmitDeleteDataForm" id="sumitDeleteDataFormButton" class="btn btn-danger btn-lg" type="submit">
                <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                Удалить
            </form:button>
            <form:button name="resetDeleteDataForm" id="resetDeleteDataFormButton" class="btn btn-link" type="reset">
                Я передумал, очистить форму
            </form:button>
        </form:form>
    </div>

<jsp:directive.include file="part_footer.jsp"/>