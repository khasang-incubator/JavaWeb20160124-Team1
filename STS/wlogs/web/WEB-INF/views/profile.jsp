<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:directive.page contentType="text/html;charset=UTF-8" language="java"/>
<jsp:directive.include file="part_header.jsp"/>

<h2>
    <span class="glyphicon glyphicon-user"></span>
    Профиль пользователя
</h2>
<div>
    <br/>
    <table class="table table-striped table-hover">
        <tbody>
        <tr>
            <td>Имя пользователя</td>
            <td>${principal.username}</td>
        </tr>
        <tr>
            <td>Аккаунт активирован</td>
            <td><c:out value="${principal.isEnabled()}"/></td>
        </tr>
        <tr>
            <td>Аккаунт просрочен</td>
            <td><c:out value="${!principal.isAccountNonExpired()}"/></td>
        </tr>
        <tr>
            <td>Аккаунт заблокирован</td>
            <td><c:out value="${!principal.isAccountNonLocked()}"/></td>
        </tr>
        <tr>
            <td>Пароль просрочен</td>
            <td><c:out value="${!principal.isCredentialsNonExpired()}"/></td>
        </tr>
        <tr>
            <td>Права пользователя</td>
            <td><c:out value="${principal.getAuthorities()}"/></td>
        </tr>
        </tbody>
    </table>
</div>

<jsp:directive.include file="part_footer.jsp"/>