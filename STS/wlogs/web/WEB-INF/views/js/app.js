/* Import page, AJAX loading of fixtures */
$(document).ready(function() {
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");
    var headers = {};
    headers[csrfHeader] = csrfToken;

    var createTableBtn = $('#createTableBtn');
    createTableBtn.children('img').hide();
    createTableBtn.click(function() {
        createTableBtn.attr("disabled", "disabled");
        createTableBtn.children('img').show();
        $.ajax({
            type: "POST",
            url: "/ajax/create-table",
            dataType: "json",
            headers: headers,
            success: function(data, textStatus, jqXHR) {
                alert("Таблица успешно создана!");
            },
            error: function(jqXHR, textStatus, errorThrown) {
                var errorText = "Неизвестная ошибка!";
                try {
                    if (jqXHR.status === 400) {
                        var data = JSON.parse(jqXHR.responseText);
                        if ('error' in data) {
                            errorText = data.error;
                        }
                    } else {
                        errorText = jqXHR.statusText;
                    }
                } catch (err) {}
                alert("Произошла ошибка: " + errorText);
            },
            complete: function(jqXHR, textStatus) {
                createTableBtn.removeAttr("disabled");
                createTableBtn.children('img').hide();
            }
        });
        return false;
    });

    var loadFixturesBtn = $('#loadFixturesBtn');
    loadFixturesBtn.children('img').hide();
    loadFixturesBtn.click(function() {
        loadFixturesBtn.attr("disabled", "disabled");
        $(this).children('img').show();
        $.ajax({
            type: "POST",
            url: "/ajax/import-fixtures",
            dataType: "json",
            headers: headers,
            success: function(data, textStatus, jqXHR) {
                alert("Данные успешно импортированы!");
            },
            error: function(jqXHR, textStatus, errorThrown) {
                var errorText = "Неизвестная ошибка!";
                try {
                    if (jqXHR.status === 400) {
                        var data = JSON.parse(jqXHR.responseText);
                        if ('error' in data) {
                            errorText = data.error;
                        }
                    } else {
                        errorText = jqXHR.statusText;
                    }
                } catch (err) {}
                alert("Произошла ошибка: " + errorText);
            },
            complete: function(jqXHR, textStatus) {
                loadFixturesBtn.removeAttr("disabled");
                loadFixturesBtn.children('img').hide();
            }
        });
        return false;
    });
});