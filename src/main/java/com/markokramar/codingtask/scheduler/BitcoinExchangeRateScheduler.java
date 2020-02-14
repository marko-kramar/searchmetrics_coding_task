package com.markokramar.codingtask.scheduler;

import com.markokramar.codingtask.service.BitcoinExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BitcoinExchangeRateScheduler {

    @Autowired
    private BitcoinExchangeRateService bitcoinExchangeRateService;

    @Scheduled(fixedRateString = "${bitcoin.price.check.period.ms}", initialDelay = 1000)
    public void fetchLatestRates() {
        bitcoinExchangeRateService.fetchLatestRate();
    }
}
