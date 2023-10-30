package com.ose_abunaw.controller;

import com.ose_abunaw.ose_abunaw.controller.ContactController;
import com.ose_abunaw.ose_abunaw.model.Contact;
import com.ose_abunaw.ose_abunaw.model.User;
import com.ose_abunaw.ose_abunaw.service.ContactService;
import com.ose_abunaw.ose_abunaw.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.mockito.ArgumentMatchers.eq;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ContactControllerTests {
    private ContactController contactController;
    private ContactService contactService;
    private UserService userService;

    @BeforeEach
    public void setup() {
        // Initialize mock objects and the controller
        contactService = mock(ContactService.class);
        userService = mock(UserService.class);
        contactController = new ContactController(contactService, userService);
    }

    @Test
    public void testCreateContact() {
        // Arrange
        Long userId = 1L;
        String firstName = "Alice";
        String lastName = "Smith";
        String email = "alice@example.com";
        String phoneNumber = "123-456-7890";

        User mockUser = new User();
        mockUser.setId(userId);

        Contact mockContact = new Contact();
        mockContact.setFirstName(firstName);
        mockContact.setLastName(lastName);
        mockContact.setEmail(email);
        mockContact.setPhoneNumber(phoneNumber);

        // Mock the behavior of userService.getUserById
        when(userService.getUserById(userId)).thenReturn(mockUser);

        // Mock the behavior of contactService.createContact with any Contact instance
        when(contactService.createContact(eq(userId), any(Contact.class))).thenReturn(mockContact);

        // Act
        ResponseEntity<String> response = contactController.createContact(userId, firstName, lastName, email,
                phoneNumber);

        // Assert
        verify(userService).getUserById(userId);
        verify(contactService).createContact(eq(userId), any(Contact.class));

        // Assert the response
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Contact created successfully", response.getBody());
    }

    @Test
    public void testSearchContacts() {
        // Create a mock User
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("john@yahoo.com");
        mockUser.setPassword("password");

        // Create a list of mock Contact objects
        List<Contact> mockContacts = List.of(
                new Contact(null, "John", "Doe", "john.doe@example.com", "123-456-7890", mockUser),
                new Contact(null, "Alice", "Smith", "alice.smith@example.com", "987-654-3210", mockUser));

        // Create a ContactService with mock behavior
        ContactService contactService = mock(ContactService.class);
        when(contactService.searchContacts(anyLong(), anyString(), anyString())).thenReturn(mockContacts);

        // Create a ContactController
        ContactController contactController = new ContactController(contactService, null);

        // Call the controller method with search parameters
        ResponseEntity<List<Contact>> response = contactController.searchContacts(mockUser.getId(), "John", "Doe");

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockContacts, response.getBody());

        // Verify the service method was called
        verify(contactService, times(1)).searchContacts(anyLong(), anyString(), anyString());
    }

    @Test
    public void testUpdateContact() {
        // Create a mock User and Contact object
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("john@yahoo.com");
        mockUser.setPassword("password");

        Contact mockContact = new Contact();
        mockContact.setId(1L);
        mockContact.setFirstName("Alice");
        mockContact.setLastName("Smith");
        mockContact.setEmail("alice@example.com");
        mockContact.setPhoneNumber("123-456-7890");

        // Create a ContactService with mock behavior
        ContactService contactService = mock(ContactService.class);

        // Mock the behavior of contactService.updateContact
        when(contactService.updateContact(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong(),
                ArgumentMatchers.any(Contact.class))).thenReturn(mockContact);

        // Create a ContactController
        ContactController contactController = new ContactController(contactService, null);

        // Call the controller method
        ResponseEntity<String> response = contactController.updateContact(mockUser.getId(), mockContact.getId(),
                mockContact.getFirstName(), mockContact.getLastName(), mockContact.getEmail(),
                mockContact.getPhoneNumber());

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Contact updated successfully", response.getBody());

        // Verify the service method was called
        verify(contactService, times(1)).updateContact(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong(),
                ArgumentMatchers.any(Contact.class));
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

}
