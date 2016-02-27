<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:directive.page contentType="text/html;charset=UTF-8" language="java"/>
<jsp:directive.include file="part_header.jsp"/>

<div id="login-form">
    <div class="login-wrapper">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <h2 class="align-center">Новый аккаунт</h2>
                <br/>
                <form:form modelAttribute="userRegistrationForm" action="/users" id="userRegistrationFormId" name="userRegistrationForm" method="post">
                    <div class="form-group form-group-lg">
                        <label for="usernameTextField" class="control-label input-lg">
                            <span class="glyphicon glyphicon-user"></span>
                            Имя пользователя
                        </label>
                        <form:input path="username" type="text" id="usernameTextField" name="username" class="form-control"/>
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