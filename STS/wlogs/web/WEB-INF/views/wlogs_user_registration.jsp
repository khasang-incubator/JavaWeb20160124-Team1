<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<jsp:directive.page contentType="text/html;charset=UTF-8" language="java"/>
<jsp:directive.include file="part_header.jsp"/>

<div id="login-form">
    <div class="login-wrapper">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <h2 class="align-center">Новый аккаунт</h2>
                <br/>
                <form:form modelAttribute="userRegistrationForm" action="/users" id="userRegistrationFormId" name="userRegistrationForm" method="post">
                    <spring:bind path="userRegistrationForm.*">
                        <c:if test="${not empty status}">
                            <c:forEach items="${status.errors.allErrors}" var="error">
                                <div class="alert alert-danger alert-dismissible" role="alert">
                                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                    <c:catch var="exception">
                                        <strong><c:out value="${error.field}" /></strong>:
                                    </c:catch>
                                    <c:out value="${error.defaultMessage}"/>
                                </div>
                            </c:forEach>
                        </c:if>
                    </spring:bind>
                    <div class="form-group form-group-lg">
                        <label for="usernameTextField" class="control-label input-lg">
                            <span class="glyphicon glyphicon-user"></span>
                            Имя пользователя
                        </label>
                        <form:input path="username" type="text" id="usernameTextField" name="username" class="form-control"/>
                    </div>
                    <div class="form-group form-group-lg">
                        <label for="usernameTextField" class="control-label input-lg">
                            <span class="glyphicon glyphicon-envelope"></span>
                            Адрес email
                        </label>
                        <form:input path="email" type="text" id="usernameTextField" name="username" class="form-control"/>
                    </div>
                    <div class="form-group form-group-lg">
                        <label for="passwordTextField" class="control-label  input-lg">
                            <span class="glyphicon glyphicon-lock"></span>
                            Пароль
                        </label>
                        <form:input path="password" type="password" id="passwordTextField" name="password" class="form-control"/>
                    </div>
                    <div class="form-group form-group-lg">
                        <label for="passwordTextField" class="control-label  input-lg">
                            <span class="glyphicon glyphicon-lock"></span>
                            Подтверждение пароля
                        </label>
                        <form:input path="confirmPassword" type="password" id="passwordTextField" name="password" class="form-control"/>
                    </div>
                    <div class="form-group">
                        <button type="submit" id="submitBtn" name="submit" class="btn btn-primary btn-lg  center-block">
                            <span class="glyphicon glyphicon-check"></span>
                            Создать аккаунт
                        </button>
                    </div>
                </form:form>
                <p class="align-center">
                    <a href="/auth/login">Войти в приложение</a>
                </p>
            </div>
        </div>
    </div>
</div>



<jsp:directive.include file="part_footer.jsp"/>