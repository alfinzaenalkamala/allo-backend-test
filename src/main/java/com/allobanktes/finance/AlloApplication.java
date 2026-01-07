package com.allobanktes.finance;

import com.allobanktes.finance.config.FrankfurterProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(FrankfurterProperties.class)
public class AlloApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlloApplication.class, args);
    }

}
