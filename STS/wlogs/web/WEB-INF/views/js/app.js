/* Import page, AJAX loading of fixtures */
$(document).ready(function() {
    var loadFixturesBtn = $('#loadFixturesBtn');
    var onErrorMessageEl = $('#load-fixtures-error');
    var onSuccessMessageEl = $('#load-fixtures-success');
    loadFixturesBtn.children('img').hide();
    onErrorMessageEl.hide();
    onSuccessMessageEl.hide();
    loadFixturesBtn.click(function() {
        loadFixturesBtn.attr("disabled", "disabled");
        $(this).children('img').show();
        $.ajax({
            type: "POST",
            url: "/ajax/import-fixtures",
            dataType: "json",
            success: function(data, textStatus, jqXHR) {
                onSuccessMessageEl.show();
            },
            error: function(jqXHR, textStatus, errorThrown) {
                try {
                    if (jqXHR.status === 400) {
                        var data = JSON.parse(jqXHR.responseText);
                        if ('error' in data) {
                            onErrorMessageEl.children('p').html(data.error);
                        }
                    } else {
                        onErrorMessageEl.children('p').html(jqXHR.statusText);
                    }
                } catch (err) {}
                onErrorMessageEl.show();
            },
            complete: function(jqXHR, textStatus) {
                loadFixturesBtn.removeAttr("disabled");
                loadFixturesBtn.children('img').hide();
            }
        });
        return false;
    });
});