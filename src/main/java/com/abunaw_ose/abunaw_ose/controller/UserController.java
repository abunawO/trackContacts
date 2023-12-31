package com.abunaw_ose.abunaw_ose.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.abunaw_ose.abunaw_ose.model.User;
import com.abunaw_ose.abunaw_ose.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

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
