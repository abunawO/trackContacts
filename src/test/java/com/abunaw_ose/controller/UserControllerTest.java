package com.abunaw_ose.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import com.abunaw_ose.abunaw_ose.controller.UserController;
import com.abunaw_ose.abunaw_ose.model.User;
import com.abunaw_ose.abunaw_ose.service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Test
    void testSignIn() {
        // Mock your userService to return a user when signIn is called
        User user = new User("test@example.com", "password");
        when(userService.signIn("test@example.com", "password")).thenReturn(user);

        // Perform the sign-in
        String viewName = userController.signIn("test@example.com", "password", model);

        // Assert that the viewName is what you expect
        assertEquals("redirect:/user/profile", viewName);
    }

    @Test
    void testSignInFailed() {
        // Mock your userService to return null when signIn is called
        when(userService.signIn(any(String.class), any(String.class))).thenReturn(null);

        // Perform the sign-in with invalid credentials
        String viewName = userController.signIn("test@example.com", "invalidpassword", model);

        // Assert that the viewName is what you expect
        assertEquals("signin", viewName);
    }

    // Add more test methods for other controller methods (e.g., testSignUp, etc.)
}
