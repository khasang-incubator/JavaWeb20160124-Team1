<jsp:directive.page contentType="text/html;charset=UTF-8" language="java"/>
<jsp:directive.include file="part_header.jsp"/>

    <h2>Импорт логов из CSV</h2>
    <div>
        <form action="" method="post">
            <fieldset>
                <legend>Загрузить тестовый файл</legend>
                <div id="load-fixtures-success" class="alert alert-success alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <strong>Операция прошла успешно!</strong> <p>Тестовые данные полностью загружены в базу данных.</p>
                </div>
                <div id="load-fixtures-error" class="alert alert-danger alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <strong>При загрузке произошла ошибка!</strong> <p class="error-text">Неизвестная ошибка.</p>
                </div>
                <button id="loadFixturesBtn" class="btn btn-primary btn-lg">
                    Загрузить данные <img src="images/ajax-loader.gif"/>
                </button>
            </fieldset>
        </form>
    </div>

<jsp:directive.include file="part_footer.jsp"/>