<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:directive.page contentType="text/html;charset=UTF-8" language="java"/>
<jsp:directive.include file="part_header.jsp"/>

<div id="login-form">
    <div class="login-wrapper">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <h2 class="center-block">Аутентификация</h2>
            <br/>
            <form:form action="/login" id="loginFormId" name="loginForm" method="post">
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
        </div>
    </div>
    </div>
</div>

<jsp:directive.include file="part_footer.jsp"/>