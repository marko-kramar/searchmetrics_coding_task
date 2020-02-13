package com.markokramar.codingtask.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BitcoinHistoricalUsdRate {
    private LocalDate date;
    private BigDecimal rate;

    @JsonAnySetter
    public void setDateAndRate(String key, BigDecimal value) {
        this.date = LocalDate.parse(key, DateTimeFormatter.ISO_LOCAL_DATE);
        this.rate = value;
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getRate() {
        return rate;
    }

    @Override
    public String toString() {
        return "BitcoinHistoricalUsdRate{" +
                "date=" + date +
                ", rate=" + rate +
                '}';
    }
}
