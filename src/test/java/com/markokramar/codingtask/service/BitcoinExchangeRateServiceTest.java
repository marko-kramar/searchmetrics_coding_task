package com.markokramar.codingtask.service;

import com.markokramar.codingtask.model.BitcoinHistoricalUsdRate;
import com.markokramar.codingtask.model.BitcoinLatestUsdRate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BitcoinExchangeRateServiceTest {

    @Autowired
    private BitcoinExchangeRateService bitcoinExchangeRateService;

    @Test
    public void shouldFetchLatestRate() {
        BitcoinLatestUsdRate bitcoinLatestUsdRate = bitcoinExchangeRateService.fetchLatestRate();

        assertThat(bitcoinLatestUsdRate).isNotNull();
        assertThat(bitcoinLatestUsdRate.getRate()).isNotNull();
        assertThat(bitcoinLatestUsdRate.getTimeUpdated()).isNotNull();
    }

    @Test
    public void shouldFetchHistoricalRate() throws Exception {
        LocalDate startOf2020 = LocalDate.of(2020, 1, 1);
        LocalDate endOfJanuary2020 = LocalDate.of(2020, 1, 31);
        List<BitcoinHistoricalUsdRate> bitcoinHistoricalUsdRates = bitcoinExchangeRateService
                .fetchHistoricalRates(startOf2020, endOfJanuary2020);

        assertThat(bitcoinHistoricalUsdRates).isNotNull();
        assertThat(bitcoinHistoricalUsdRates).isNotEmpty();
        assertThat(bitcoinHistoricalUsdRates).hasSize(31);

    }


}
