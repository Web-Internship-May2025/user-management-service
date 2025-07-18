package com.example.user_management_service.unit.testconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.boot.test.context.TestConfiguration;
import org.mockito.Mockito;
import com.example.user_management_service.service.CountryService;

@TestConfiguration
public class TestConfig {
    @Bean
    public CountryService countryService() {
        return Mockito.mock(CountryService.class);
    }
}
