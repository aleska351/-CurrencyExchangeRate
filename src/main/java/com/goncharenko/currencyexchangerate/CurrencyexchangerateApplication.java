package com.goncharenko.currencyexchangerate;

import com.goncharenko.currencyexchangerate.config.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class CurrencyexchangerateApplication {

    public static void main(String[] args) {

        //SpringApplication.run(CurrencyexchangerateApplication.class, args);

        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        String nameApp = (String) context.getBean("nameFromRoot");
        System.out.println(nameApp);
        System.out.println(context.getBean("currentTime"));
        System.out.println(context.getBean("nameFromString"));
    }

}
