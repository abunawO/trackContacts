package com.ose_abunaw.ose_abunaw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.ose_abunaw.ose_abunaw.repository")
@Controller
public class OseAbunawApplication {

    public static void main(String[] args) {
        SpringApplication.run(OseAbunawApplication.class, args);
    }

    @GetMapping("/")
    public String showSignInForm() {
        return "signin"; // Return the "signin" HTML template as the index page
    }

    @GetMapping("/signup")
    public String showSignUpForm() {
        return "signup"; // Return the "signup" HTML template
    }
}
