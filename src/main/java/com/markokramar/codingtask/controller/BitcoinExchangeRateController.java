package com.markokramar.codingtask.controller;

import com.markokramar.codingtask.model.BitcoinHistoricalUsdRate;
import com.markokramar.codingtask.model.BitcoinLatestUsdRate;
import com.markokramar.codingtask.service.BitcoinExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class BitcoinExchangeRateController {

    @Autowired
    private BitcoinExchangeRateService bitcoinExchangeRateService;

    @GetMapping("/codingtask/latest")
    public BitcoinLatestUsdRate fetchLatestRate() {
        return bitcoinExchangeRateService.fetchLatestRate();
    }

    @GetMapping("/codingtask/historical")
    public List<BitcoinHistoricalUsdRate> fetchHistoricalRates(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) throws Exception {
        if (startDate == null) {
            throw new IllegalArgumentException("Error: The 'startDate' parameter is invalid!");
        }

        if (endDate == null) {
            throw new IllegalArgumentException("Error: The 'endDate' parameter is invalid!");
        }

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Error: The 'endDate' parameter should be chronologically after 'startDate'!");
        }

        return bitcoinExchangeRateService.fetchHistoricalRates(startDate, endDate);
    }

}
