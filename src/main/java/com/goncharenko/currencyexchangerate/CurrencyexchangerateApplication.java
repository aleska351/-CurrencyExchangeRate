package com.goncharenko.currencyexchangerate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CurrencyexchangerateApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyexchangerateApplication.class);

    public static void main(String[] args) {
       SpringApplication.run(CurrencyexchangerateApplication.class, args);
       LOGGER.info("Application run");
//        String nameApp = (String) context.getBean("nameFromRoot");
//        System.out.println(nameApp);
//        System.out.println(context.getBean("currentTime"));
//        System.out.println(context.getBean("nameFromString"));

    }

}
