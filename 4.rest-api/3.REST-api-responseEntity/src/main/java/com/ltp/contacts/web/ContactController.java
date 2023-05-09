package com.ltp.contacts.web;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ltp.contacts.pojo.Contact;
import org.springframework.beans.factory.annotation.Autowired;

import com.ltp.contacts.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping("/contact/{id}")
    public ResponseEntity<Contact> getContact(@PathVariable String id) {
        Contact contact = contactService.getContactByIndex(id);
        System.out.println(contact);
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }

    @GetMapping("/contacts")
    public ResponseEntity<List<Contact>> getAllContacts() {
        List<Contact> contacts = contactService.getAllContacts();
        return new ResponseEntity<>(contacts, HttpStatus.OK); //OK 200
    }

    /* here we do NOT sand back any data, but only the HTTP response status
    *
    * @RequestBody --> after this mapping activates and creates the Contact empty object by invoking its empty
    *                   constructor, the annotation @RequestBody will fill the Contact object with the JSON data received.
    *                   And then the Contact object is passed to the handler which is called
    * */
    @PostMapping("/contact")
    public ResponseEntity<HttpStatus> createContact(@RequestBody Contact contact) {
        contactService.saveContact(contact);
        return new ResponseEntity<>(HttpStatus.CREATED); //OK 201
    }

    @PutMapping("/contact/{id}")
    public ResponseEntity<Contact> updateContact(@RequestBody Contact contact, @PathVariable String id) {

        /*System.out.println(contact);
        System.out.println(id);*/

        Contact updatedContact = contactService.updateContact(id, contact);

        return new ResponseEntity<>(updatedContact, HttpStatus.OK);
    }

    @DeleteMapping("/contact/{id}")
    public ResponseEntity<HttpStatus> deleteContact(@PathVariable String id) {
        contactService.deleteContact(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
