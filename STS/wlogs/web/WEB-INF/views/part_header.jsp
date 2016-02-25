<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:directive.page contentType="text/html;charset=UTF-8" language="java"/>
<html>
    <head>
        <title>Title</title>
        <meta charset="utf-8" lang="ru" content="text/html"/>
        <link
                rel="stylesheet"
                href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
                integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7"
                crossorigin="anonymous"/>
        <link rel="stylesheet" href="/css/style.css"/>
    </head>

    <body>
        <div id="main_menu">
            <div id="menu_wrapper">
                <div id="logo"><span class="glyphicon glyphicon-tasks" aria-hidden="true"></span></div>
                <ul id="menu_items">
                    <li>
                        <a href="/" title="Просмотр">
                            <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                            Просмотр
                        </a>
                    </li>
                    <li>
                        <a href="/delete" title="Удаление">
                            <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                            Удаление
                        </a>
                    </li>
                    <li>
                        <a href="/import" title="Импорт">
                            <span class="glyphicon glyphicon-import" aria-hidden="true"></span>
                            Импорт
                        </a>
                    </li>
                    <li>
                        <a href="/export" title="Экспорт">
                            <span class="glyphicon glyphicon-export" aria-hidden="true"></span>
                            Экспорт
                        </a>
                    </li>
                    <li>
                        <!-- Разлогиниться можно POST запросом. Это рекомендация из оф доки. Подробнее тут:
                         http://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#csrf-logout
                         -->
                        <form:form action="logout" method="post" id="logoutFormId" cssStyle="display:none;"></form:form>
                        <a href="javascript:document.getElementById('logoutFormId').submit()" title="Выход">
                            <span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>
                            Выход
                        </a>
                    </li>
                </ul>
                </div>
        </div>

        <div id="content">
