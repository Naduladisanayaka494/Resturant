package com.nadulahotel.nadulahotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })

public class NadulaHotelApplication {

    public static void main(String[] args) {
        SpringApplication.run(NadulaHotelApplication.class, args);
    }

}
