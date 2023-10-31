package com.abunaw_ose;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.abunaw_ose.abunaw_ose.repository")
@Controller
public class AbunawOseApplication {

    public static void main(String[] args) {
        SpringApplication.run(AbunawOseApplication.class, args);
    }

    @GetMapping("/")
    public String showSignInForm() {
        return "signin"; // Return the "signin" HTML template as the index page
    }
}
