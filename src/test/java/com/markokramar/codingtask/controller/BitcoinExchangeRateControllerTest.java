package com.markokramar.codingtask.controller;

import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BitcoinExchangeRateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnLatestBitcoinRateAndTimeAsJson() throws Exception {
        this.mockMvc.perform(get("/latest"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.timeUpdated", notNullValue()))
                .andExpect(jsonPath("$.timeUpdated", not(emptyString())))
                .andExpect(jsonPath("$.rate", notNullValue()))
                .andExpect(jsonPath("$.rate", not(emptyString())));
    }

    @Test
    public void shouldReturnHistoricalBitcoinRateAndDateAsJson() throws Exception {
        this.mockMvc.perform(get("/historical?startDate=2020-01-01&endDate=2020-01-31"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", isA(JSONArray.class)))
                .andExpect(jsonPath("$", iterableWithSize(31)));
    }

    @Test
    public void shouldEnforceHistoricalBitcoinRateStartDateParameter() throws Exception {
        this.mockMvc.perform(get("/historical?endDate=2020-01-31"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldEnforceHistoricalBitcoinRateEndDateParameter() throws Exception {
        this.mockMvc.perform(get("/historical?startDate=2020-01-31"))
                .andExpect(status().isBadRequest());
    }
}
