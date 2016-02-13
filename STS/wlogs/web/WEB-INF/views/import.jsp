<jsp:directive.page contentType="text/html;charset=UTF-8" language="java"/>
<jsp:directive.include file="part_header.jsp"/>

    <h2>Импорт логов из CSV</h2>
    <div>
        <fieldset>
            <legend>Создать таблицу WLOGS</legend>
            <button id="createTableBtn" class="btn btn-primary btn-lg">
                Создать таблицу <img src="images/ajax-loader.gif"/>
            </button>
        </fieldset>
    </div>
    <br/>
    <br/>
    <div>
        <fieldset>
            <legend>Загрузить тестовый файл</legend>
            <button id="loadFixturesBtn" class="btn btn-primary btn-lg">
                Загрузить данные <img src="images/ajax-loader.gif"/>
            </button>
        </fieldset>
    </div>

    <hr/>
<jsp:directive.include file="part_footer.jsp"/>