$(function() {
    $("#datepicker-from").datepicker({
        autoHide: true,
        format: 'dd.mm.yyyy'
    });

    $("#datepicker-to").datepicker({
        autoHide: true,
        format: 'dd.mm.yyyy'
    });

    $("#datepicker-from").datepicker('setDate', new Date(new Date().getFullYear(), 0, 1));
    $("#datepicker-to").datepicker('setDate', new Date());

    $("#btn-fetch-historical").click(function() {
        let dateFrom = $("#datepicker-from").datepicker('getDate');
        let dateTo = $("#datepicker-to").datepicker('getDate');

        if (!dateFrom) {
            alert("Please enter date from!");
            $("#datepicker-from").focus();
            return;
        }

        if (!dateTo) {
            alert("Please enter date to!");
            $("#datepicker-to").focus();
            return;
        }

        $("#historical-table").fadeOut("fast");
        $("#historical-table-body").html("");

        $.ajax({
            url: "/historical",
            data: {
                "startDate" : getIsoShortDate(dateFrom),
                "endDate" : getIsoShortDate(dateTo)
            }
        }).done(function(data) {
            populateTableWithHistoricalBitcoinPrices(data);
        });
    });
});

function populateTableWithHistoricalBitcoinPrices(data) {
    if (!data) {
        return;
    }

    for (let i=0; i<data.length; i++) {
        setTimeout(function() {
            let item = data[i];
            let tr = $(document.createElement("tr"));
            let rowNumber = $(document.createElement("th")).appendTo(tr).addClass("scope");
            rowNumber.text(i+1);
            let dateCell = $(document.createElement("td")).appendTo(tr);
            dateCell.text(item.date);
            let rateCell = $(document.createElement("td")).appendTo(tr);
            rateCell.text(parseFloat(item.rate).toFixed(2));
            tr.appendTo($("#historical-table-body"));
        }, (i+1) * 50)
    }

    $("#historical-table").fadeIn("fast");
};

function getIsoShortDate(jsDate) {
    var year = jsDate.getFullYear();
    var month = jsDate.getMonth() + 1;
    var date = jsDate.getDate();

    if (month < 10) {
        month = "0" + month;
    }

    if (date < 10) {
        date = "0" + date;
    }

    return year + "-" + month + "-" + date;
}