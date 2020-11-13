package com.goncharenko.currencyexchangerate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CurrencyexchangerateApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(CurrencyexchangerateApplication.class, args);
        String nameApp = (String) context.getBean("nameFromRoot");
        System.out.println(nameApp);
        System.out.println(context.getBean("currentTime"));
        System.out.println(context.getBean("nameFromString"));

    }

}
