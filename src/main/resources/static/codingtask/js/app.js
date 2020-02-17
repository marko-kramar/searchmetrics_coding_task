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

        $("#historical-table").css("display", "none");
        $("#historical-table-body").html("");

        $.ajax({
            url: "/codingtask/historical",
            data: {
                "startDate" : getIsoShortDate(dateFrom),
                "endDate" : getIsoShortDate(dateTo)
            }
        }).done(function(data) {
            populateTableWithHistoricalBitcoinPrices(data);
        });
    });

    $("#btn-fetch-latest").click(function() {
        $("#latest-price-paragraph").css("display", "none");
        $("#latest-rate, #latest-date").html("");

        $.ajax({
            url: "/codingtask/latest"
        }).done(function(data) {
            $("#latest-rate").text(parseAndShortenRate(data.rate) + " USD");
            $("#latest-date").text(getDateAndTimeString(new Date(data.timeUpdated)) + " (Central European Time)");
            $("#latest-price-paragraph").fadeIn("fast");
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
            let rowNumber = $(document.createElement("th")).appendTo(tr).addClass("col-row-number");
            rowNumber.text(i+1);
            let dateCell = $(document.createElement("td")).appendTo(tr).addClass("col-date");
            dateCell.text(getLocalDate(new Date(item.date)));
            let rateCell = $(document.createElement("td")).appendTo(tr).addClass("col-rate");
            rateCell.text(parseAndShortenRate(item.rate) + " USD");
            tr.appendTo($("#historical-table-body"));
        }, (i+1) * 50)
    }

    $("#historical-table").fadeIn("fast");
};

function parseAndShortenRate(rate) {
    var rateShortened = parseFloat(rate).toFixed(2);
    rateShortened = "" + rateShortened.replace(".", ",");
    return rateShortened.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
}

function getLocalDate(jsDate) {
    let dateParts = extractDateParts(jsDate);
    return dateParts.day + "." + dateParts.month + "." + dateParts.year;
}

function getIsoShortDate(jsDate) {
    let dateParts = extractDateParts(jsDate);
    return dateParts.year + "-" + dateParts.month + "-" + dateParts.day;
}

function extractDateParts(jsDate) {
    var yr = jsDate.getFullYear();
    var mnth = jsDate.getMonth() + 1;
    var dt = jsDate.getDate();

    if (mnth < 10) {
        mnth = "0" + mnth;
    }

    if (dt < 10) {
        dt = "0" + dt;
    }

    return { year: yr, month: mnth, day: dt };
}

function getDateAndTimeString(jsDateAndTime) {
    let dateParts = extractDateParts(jsDateAndTime);

    var hours = jsDateAndTime.getHours();
    if (hours < 10) {
        hours = "0" + hours;
    }

    var minutes = jsDateAndTime.getMinutes();
    if (minutes < 10) {
        minutes = "0" + minutes;
    }

    var seconds = jsDateAndTime.getSeconds();
    if (seconds < 10) {
        seconds = "0" + seconds;
    }

    return dateParts.day + "." + dateParts.month + "." + dateParts.year + " " + hours + ":" + minutes + ":" + seconds;
}