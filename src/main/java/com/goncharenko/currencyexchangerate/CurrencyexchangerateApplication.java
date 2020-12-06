package com.goncharenko.currencyexchangerate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CurrencyexchangerateApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyexchangerateApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CurrencyexchangerateApplication.class, args);
        LOGGER.info("Application run");
    }

}
