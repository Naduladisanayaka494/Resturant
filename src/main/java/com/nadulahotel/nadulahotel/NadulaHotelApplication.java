package com.nadulahotel.nadulahotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@CrossOrigin(origins = "http://localhost:5173")

public class NadulaHotelApplication {

    public static void main(String[] args) {
        SpringApplication.run(NadulaHotelApplication.class, args);
    }

}
