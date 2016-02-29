<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:directive.page contentType="text/html;charset=UTF-8" language="java"/>
<jsp:directive.include file="part_header.jsp"/>

<div id="login-form">
    <div class="login-wrapper">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <h2 class="align-center">Аутентификация</h2>
            <br/>
            <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
                <div class="alert alert-danger alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <p>${SPRING_SECURITY_LAST_EXCEPTION.message}</p>
                </div>
            </c:if>
            <c:if test="${param.user_created != null}">
                <div class="alert alert-success alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <p>
                        <span class="glyphicon glyphicon-ok"></span>
                        <strong>Аккаунт успешно создан!</strong> Введите ваш новый логин и пароль для входа в систему.
                    </p>
                </div>
            </c:if>
            <c:if test="${param.logout != null}">
                <div class="alert alert-success alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <p>
                        <span class="glyphicon glyphicon-ok"></span>
                        Вы успешно вышли из системы!
                    </p>
                </div>
            </c:if>
            <form:form commandName="login" id="loginFormId" name="loginForm" method="post">
                <div class="form-group form-group-lg">
                    <label for="usernameTextField" class="control-label input-lg">
                        <span class="glyphicon glyphicon-user"></span>
                        Имя пользователя
                    </label>
                    <input type="text" id="usernameTextField" name="username" class="form-control"/>
                </div>
                <div class="form-group form-group-lg">
                    <label for="passwordTextField" class="control-label  input-lg">
                        <span class="glyphicon glyphicon-lock"></span>
                        Пароль
                    </label>
                    <input type="password" id="passwordTextField" name="password" class="form-control"/>
                </div>
                <div class="form-group">
                    <button type="submit" id="submitBtn" name="submit" class="btn btn-primary btn-lg  center-block">
                        <span class="glyphicon glyphicon-check"></span>
                        Войти
                    </button>
                </div>
            </form:form>
            <p class="align-center">
                <a href="/users">Создать аккаунт</a>
            </p>
        </div>
    </div>
    </div>
</div>

<jsp:directive.include file="part_footer.jsp"/>