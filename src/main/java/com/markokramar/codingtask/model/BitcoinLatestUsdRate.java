package com.markokramar.codingtask.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;

public class BitcoinLatestUsdRate implements Serializable {
    private LocalDateTime timeUpdated;
    private BigDecimal rate;

    @JsonProperty("time")
    private void flattenNestedTimeUpdated(Map<String, Object> time) {
        LocalDateTime localDateTime = LocalDateTime.parse(time.get("updatedISO").toString(), ISO_OFFSET_DATE_TIME);

        // Convert zone from UTC to CET
        ZonedDateTime utcTimeZoned = ZonedDateTime.of(localDateTime, ZoneId.of("UTC"));
        this.timeUpdated = utcTimeZoned.withZoneSameInstant(ZoneId.of("CET")).toLocalDateTime();
    }

    @JsonProperty("bpi")
    private void flattenNestedRate(Map<String, Object> bpi) {
        Map<String, Object> usd = (Map<String, Object>) bpi.get("USD");
        this.rate = (BigDecimal) usd.get("rate_float");
    }

    public LocalDateTime getTimeUpdated() {
        return timeUpdated;
    }

    public BigDecimal getRate() {
        return rate;
    }

    @Override
    public String toString() {
        return "BitcoinUsdRate{" +
                "timeUpdated='" + timeUpdated + '\'' +
                ", rate=" + rate +
                '}';
    }
}
