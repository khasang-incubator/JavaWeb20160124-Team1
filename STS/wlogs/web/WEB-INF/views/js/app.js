/* Import page, AJAX loading of fixtures */
$(document).ready(function() {
    var dateIntervalSizeSelect = $('#date_interval_size');
    var dateIntervalTypeSelect = $('#date_interval_id');
    if (dateIntervalSizeSelect.length && dateIntervalTypeSelect.length) {
        fillDateIntervalSize(7);
        dateIntervalTypeSelect.change(function() {
            var type = parseInt(dateIntervalTypeSelect.val());
            switch (type) {
                case 0:
                    fillDateIntervalSize(7); //Days
                    break;
                case 1:
                    fillDateIntervalSize(4); //Weeks
                    break;
                case 2:
                    fillDateIntervalSize(12); //Month
                    break;
                case 3:
                    fillDateIntervalSize(10); //Year
                    break;
            }
        });

        function fillDateIntervalSize(maxSize) {
            if (dateIntervalSizeSelect.length) {
                $(dateIntervalSizeSelect).html('');
                for (var i = 1; i <= maxSize; i++) {
                    $('<option/>', {value: i, html: i}).appendTo(dateIntervalSizeSelect);
                }
            }
        }
    }

    var createTableBtn = $('#createTableBtn');
    createTableBtn.children('img').hide();
    createTableBtn.click(function() {
        createTableBtn.attr("disabled", "disabled");
        createTableBtn.children('img').show();
        $.ajax({
            type: "POST",
            url: "/ajax/create-table",
            dataType: "json",
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