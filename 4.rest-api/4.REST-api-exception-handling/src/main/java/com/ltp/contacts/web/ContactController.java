package com.ltp.contacts.web;

import java.util.List;

import com.ltp.contacts.service.ContactServiceImpl;
import exception.NoContactException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ltp.contacts.pojo.Contact;
import com.ltp.contacts.service.ContactService;

@RestController
public class ContactController {
    
    @Autowired
    private ContactService contactService;

    @GetMapping("/contact/all")
    public ResponseEntity<List<Contact>> getContacts() {
        List<Contact> contacts = contactService.getContacts();
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    @GetMapping("/contact/{id}")
    public ResponseEntity<Contact> getContact(@PathVariable String id) {
        /*clients requests to fetch a Contact data with specific id.
        If this contact is not found, an exception is thrown, and a response with error code is sent back

        NOTE --> we can use try/catch to handle checked exceptions (derived from class "Exception")

        */
        Contact contact;
        try {
            contact = contactService.getContactById(id);
        } catch(NoContactException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        /*requested Contact was found, therefore return it back within the response*/
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }
    
    @PostMapping("/contact")
    public ResponseEntity<HttpStatus> createContact(@RequestBody Contact contact) {
        contactService.saveContact(contact);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/contact/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable String id, @RequestBody Contact contact) {
        try {
            contactService.updateContact(id, contact);
        } catch (NoContactException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Contact updatedContact;
        try {
            updatedContact = contactService.getContactById(id);
        } catch (NoContactException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Contact>(updatedContact, HttpStatus.OK);
    }

    @DeleteMapping("/contact/{id}")
    public ResponseEntity<HttpStatus> deleteContact(@PathVariable String id) {
        try {
            contactService.deleteContact(id);
        } catch(NoContactException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}