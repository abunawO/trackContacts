package com.ose_abunaw.ose_abunaw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.ose_abunaw.ose_abunaw.model.User;
import com.ose_abunaw.ose_abunaw.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signin")
    public String signInForm() {
        return "signin"; // Return the HTML template for the sign-in form
    }

    @PostMapping("/signin")
    public String signIn(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {
        // Check the database to see if the user's email and password exist
        User user = userService.signIn(email, password);

        if (user != null) {
            // User credentials are valid, perform sign-in logic
            // Redirect to the user's profile or home page
            return "redirect:/user/profile";
        } else {
            // Sign-in failed, show an error message
            model.addAttribute("error", "Invalid email or password");
            return "signin"; // Return the HTML template for the signin form with an error message
        }
    }

    @GetMapping("/signup")
    public String signUpForm() {
        return "signup"; // Return the HTML template for the sign-up form
    }

    @PostMapping("/signup")
    public String createUser(@RequestParam("email") String email, @RequestParam("password") String password,
            Model model) {
        User newUser = new User(email, password);
        User createdUser = userService.createUser(newUser);

        if (createdUser != null) {
            // User was successfully created and stored in the database
            return "redirect:/users/signin";
        } else {
            // Handle the case where user creation failed
            model.addAttribute("error", "User creation failed");
            return "signup"; // Return to the sign-up form with an error message
        }
    }

}
