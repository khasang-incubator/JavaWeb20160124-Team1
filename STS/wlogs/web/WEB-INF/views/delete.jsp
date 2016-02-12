<jsp:directive.page contentType="text/html;charset=UTF-8" language="java"/>
<jsp:directive.include file="part_header.jsp"/>

    <h1>Удаление записей лога</h1>
    <form action="/delete" method="post">
        <select name="delete_period">
            <option value="only_100">Оставить только последних 100 записей</option>
            <option value="month_1">Удалить записи старше одного месяца</option>
            <option value="month_3">Удалить записи старше трех месяцев</option>
            <option value="month_6">Удалить записи старше шести месяцев</option>
            <option value="month_9">Удалить записи старше девяти месяцев</option>
            <option value="year_1">Удалить записи старше одного года</option>
        </select>
        <input type="submit" value="Удалить">
    </form>

<jsp:directive.include file="part_footer.jsp"/>