package com.abunaw_ose.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import com.abunaw_ose.abunaw_ose.controller.ProfileController;
import com.abunaw_ose.abunaw_ose.model.Contact;
import com.abunaw_ose.abunaw_ose.model.User;
import com.abunaw_ose.abunaw_ose.service.ContactService;
import com.abunaw_ose.abunaw_ose.service.UserService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ProfileControllerTest {

    @InjectMocks
    ProfileController profileController;

    @Mock
    UserService userService;

    @Mock
    ContactService contactService;

    @Mock
    Model model;

    @Test
    public void testShowUserProfile() {
        // Arrange
        User user = new User();
        user.setId(1L);

        // Mock user service to return the user
        when(userService.getSignedInUser()).thenReturn(user);

        // Mock contact service to return a list of contacts
        List<Contact> contacts = new ArrayList<>();
        // Add some contacts to the list
        // ...

        when(contactService.getAllContactsByUserId(user.getId())).thenReturn(contacts);

        // Act
        String viewName = profileController.showUserProfile(model);

        // Assert
        assertEquals("user-profile", viewName); // Check the expected view name

        // Verify that the user and contacts were added to the model
        Mockito.verify(model).addAttribute("user", user);
        Mockito.verify(model).addAttribute("allContacts", contacts);
    }

    @Test
    public void testLogout() {
        // Act
        String viewName = profileController.logout();

        // Assert
        assertEquals("redirect:/users/signin", viewName); // Check the expected view name

        // Verify that the UserService's logout method was called
        verify(userService).logout();
    }
}
