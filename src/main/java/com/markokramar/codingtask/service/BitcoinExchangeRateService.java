package com.markokramar.codingtask.service;

import com.markokramar.codingtask.model.BitcoinHistoricalUsdRate;
import com.markokramar.codingtask.model.BitcoinLatestUsdRate;

import java.time.LocalDate;
import java.util.List;

public interface BitcoinExchangeRateService {

    BitcoinLatestUsdRate fetchLatestRate();

    List<BitcoinHistoricalUsdRate> fetchHistoricalRates(LocalDate dateFrom, LocalDate dateTo) throws Exception;

}
