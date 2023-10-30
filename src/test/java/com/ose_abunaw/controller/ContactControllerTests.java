package com.ose_abunaw.controller;

import com.ose_abunaw.ose_abunaw.controller.ContactController;
import com.ose_abunaw.ose_abunaw.model.Contact;
import com.ose_abunaw.ose_abunaw.model.User;
import com.ose_abunaw.ose_abunaw.service.ContactService;
import com.ose_abunaw.ose_abunaw.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

// tests for the primary user story functionality

public class ContactControllerTests {

    @Mock
    private UserService userService;

    @Mock
    private ContactService contactService;

    @InjectMocks
    private ContactController contactController;

    @BeforeEach
    public void setup() {
        // Initialize mock objects and the controller
        contactService = mock(ContactService.class);
        userService = mock(UserService.class);
        contactController = new ContactController(contactService, userService);

    }

    @Test
    public void testCreateContact() {
        // Create a mock User
        User mockUser = new User();
        mockUser.setId(1L);

        // Mock the behavior of userService.getSignedInUser
        when(userService.getSignedInUser()).thenReturn(mockUser);

        // Create a mock Contact
        Contact mockContact = new Contact();
        mockContact.setId(1L);
        mockContact.setFirstName("Alice");
        mockContact.setLastName("Smith");
        mockContact.setEmail("alice@example.com");
        mockContact.setPhoneNumber("123-456-7890");
        // Mock the behavior of contactService.createContact
        when(contactService.createContact(anyLong(), any(Contact.class))).thenReturn(mockContact);

        // Create a mock RedirectAttributes
        RedirectAttributes redirectAttributes = Mockito.mock(RedirectAttributes.class);

        // Call the controller method
        String result = contactController.createContact("Alice", "Smith", "alice@example.com", "123-456-7890",
                Mockito.mock(Model.class), redirectAttributes);

        // Assertions
        verify(userService).getSignedInUser();
        verify(contactService).createContact(anyLong(), any(Contact.class));

        // Check if the result is the expected redirect
        assertEquals("redirect:/user/profile", result);

        // Check if the success message is added to redirect attributes
        Mockito.verify(redirectAttributes).addFlashAttribute("successMessage", "Contact created successfully");
    }

    @Test
    public void testUpdateContact() {
        // Create a mock User
        User mockUser = new User();
        mockUser.setId(1L);

        // Mock the behavior of userService.getSignedInUser
        when(userService.getSignedInUser()).thenReturn(mockUser);

        // Create a mock Contact
        Contact mockContact = new Contact();
        mockContact.setId(1L);
        mockContact.setFirstName("Alice");
        mockContact.setLastName("Smith");
        mockContact.setEmail("alice@example.com");
        mockContact.setPhoneNumber("123-456-7890");

        // Mock the behavior of contactService.updateContact
        when(contactService.updateContact(anyLong(), anyLong(), any(Contact.class))).thenReturn(mockContact);

        // Create a mock RedirectAttributes
        RedirectAttributes redirectAttributes = Mockito.mock(RedirectAttributes.class);

        // Call the controller method
        String result = contactController.updateContact(1L, "Alice", "Smith", "alice@example.com", "123-456-7890",
                Mockito.mock(Model.class), redirectAttributes);

        // Assertions
        verify(userService).getSignedInUser();
        verify(contactService).updateContact(anyLong(), anyLong(), any(Contact.class));

        // Check if the result is the expected redirect
        assertEquals("redirect:/user/profile", result);

        // Check if the success message is added to redirect attributes
        Mockito.verify(redirectAttributes).addFlashAttribute("successMessage", "Contact updated successfully");
    }

    @Test
    public void testDeleteContact() {
        // Create a mock User
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("john@yahoo.com");
        mockUser.setPassword("password");

        // Create a ContactService with mock behavior
        ContactService contactService = mock(ContactService.class);
        when(contactService.deleteContact(anyLong(), anyLong())).thenReturn(true);

        // Create a ContactController
        ContactController contactController = new ContactController(contactService, null);

        // Call the controller method
        ResponseEntity<String> response = contactController.deleteContact(mockUser.getId(), 1L);

        // Assertions
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Contact deleted successfully", response.getBody());

        // Verify the service method was called
        verify(contactService, times(1)).deleteContact(anyLong(), anyLong());
    }

    @Test
    public void testGetContactDetails() {
        // Create a mock Contact object
        Contact mockContact = new Contact();
        mockContact.setId(1L);
        mockContact.setFirstName("Alice");
        mockContact.setLastName("Smith");
        mockContact.setEmail("alice@example.com");
        mockContact.setPhoneNumber("123-456-7890");

        // Create a ContactService with mock behavior
        ContactService contactService = mock(ContactService.class);
        when(contactService.getContactById(anyLong())).thenReturn(mockContact);

        // Create a ContactController
        ContactController contactController = new ContactController(contactService, null);

        // Call the controller method
        ResponseEntity<Contact> response = contactController.getContactDetails(1L);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockContact, response.getBody());

        // Verify the service method was called
        verify(contactService, times(1)).getContactById(anyLong());
    }

    @Test
    public void testSearchContacts() {
        // Create a mock User
        User mockUser = new User();
        mockUser.setId(1L);

        // Mock the behavior of userService.getSignedInUser
        when(userService.getSignedInUser()).thenReturn(mockUser);

        // Create mock search criteria
        String searchFirstName = "Alice";
        String searchLastName = "Smith";

        // Create a list of mock filtered contacts
        List<Contact> filteredContacts = new ArrayList<>();
        Contact mockContact1 = new Contact();
        mockContact1.setId(1L);
        mockContact1.setFirstName("Alice");
        mockContact1.setLastName("Smith");
        mockContact1.setEmail("alice@example.com");
        mockContact1.setPhoneNumber("123-456-7890");
        filteredContacts.add(mockContact1);

        Contact mockContact2 = new Contact();
        mockContact1.setId(1L);
        mockContact1.setFirstName("James");
        mockContact1.setLastName("Smith");
        mockContact1.setEmail("james@example.com");
        mockContact1.setPhoneNumber("123-456-7860");
        filteredContacts.add(mockContact2);

        // Mock the behavior of contactService.searchContacts
        when(contactService.searchContacts(anyLong(), anyString(), anyString())).thenReturn(filteredContacts);

        // Create a mock Model
        Model model = Mockito.mock(Model.class);

        // Call the controller method
        String result = contactController.searchContacts(searchFirstName, searchLastName, model);

        // Assertions
        verify(userService).getSignedInUser();
        verify(contactService).searchContacts(anyLong(), anyString(), anyString());

        // Check if the model is populated with the expected attributes
        verify(model).addAttribute("user", mockUser);
        verify(model).addAttribute("filteredContacts", filteredContacts);
        verify(model).addAttribute("searchFirstName", searchFirstName);
        verify(model).addAttribute("searchLastName", searchLastName);

        // Check if the result is the expected view name
        assertEquals("user-profile", result);
    }
}
