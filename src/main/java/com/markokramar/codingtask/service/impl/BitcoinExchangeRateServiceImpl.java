package com.markokramar.codingtask.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.markokramar.codingtask.model.BitcoinHistoricalUsdRate;
import com.markokramar.codingtask.model.BitcoinLatestUsdRate;
import com.markokramar.codingtask.service.BitcoinExchangeRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.markokramar.codingtask.util.CodingTaskConstants.COINDESK_HISTORICAL_BITCOIN_USD_RATE_URL;
import static com.markokramar.codingtask.util.CodingTaskConstants.COINDESK_LATEST_BITCOIN_USD_RATE_URL;

@Service
public class BitcoinExchangeRateServiceImpl implements BitcoinExchangeRateService {

    private static final Logger log = LoggerFactory.getLogger(BitcoinExchangeRateServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        RestTemplate restTemplate = builder.build();

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        converter.getObjectMapper().enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
        restTemplate.setMessageConverters(Arrays.asList(converter));

        return restTemplate;
    }


    @Override
    public BitcoinLatestUsdRate fetchLatestRate() {
        BitcoinLatestUsdRate latestUsdRate = restTemplate.getForObject(COINDESK_LATEST_BITCOIN_USD_RATE_URL, BitcoinLatestUsdRate.class);
        log.info(latestUsdRate.toString());
        return latestUsdRate;
    }

    @Override
    public List<BitcoinHistoricalUsdRate> fetchHistoricalRates(LocalDate dateFrom, LocalDate dateTo) throws Exception {
        String formattedUrl = String.format(COINDESK_HISTORICAL_BITCOIN_USD_RATE_URL, dateFrom.toString(), dateTo.toString());

        // Raw fetching and mapping is necessary because origin JSON rates list is not a JSON array
        Map<?, ?> retrievedJsonMap = restTemplate.getForObject(formattedUrl, Map.class);
        List<BitcoinHistoricalUsdRate> historicalUsdRates = new ObjectMapper().convertValue(((Map) retrievedJsonMap.get("bpi")).entrySet(),
                new TypeReference<List<BitcoinHistoricalUsdRate>>(){});

        log.info(historicalUsdRates.toString());
        return historicalUsdRates;
    }
}
