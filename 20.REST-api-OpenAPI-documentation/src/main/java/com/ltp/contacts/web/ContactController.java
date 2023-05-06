package com.ltp.contacts.web;
import com.ltp.contacts.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;

import javax.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ltp.contacts.pojo.Contact;
import com.ltp.contacts.service.ContactService;

@RestController
@Tag(name = "Contact Controller", description = "Create and retrieve contacts")
public class ContactController {
    
    @Autowired
    private ContactService contactService;

    /* @ApiResponse() --> represents one possible response that this endpoint can send back
    * @ApiResponse(content = ...) --> in this type of response, the content is a JSON Array of "Contact" objects
    * produces = MediaType.APPLICATION_JSON_VALUE --> this endpoint produces JSON data inside the response*/
    @GetMapping(value = "/contact/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Retrieve all contacts", description = "Provides a list of all contacts")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of contacts", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Contact.class))))
    public ResponseEntity<List<Contact>> getContacts() {
        List<Contact> contacts = contactService.getContacts();
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    /* @ApiResponses --> this endpoint can send back 2 different type of responses (OK and not found).
    * Not found case (404) --> response contains JSON object of "ErrorResponse" type
    * OK case (200) --> response contains JSON object of "Contact" type
    * produces = MediaType.APPLICATION_JSON_VALUE --> this endpoint produces JSON data inside the response*/
    @GetMapping(value = "/contact/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get one contact", description = "Returns the contact by it ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Contact doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful retrieval of contact", content = @Content(schema = @Schema(implementation = Contact.class))),
    })
    public ResponseEntity<Contact> getContact(@PathVariable String id) {
        Contact contact = contactService.getContactById(id);
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }

    @PostMapping(value = "/contact", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create one contact", description = "Creates a new contact record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful creation of contact"),
            @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful submission", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Contact> createContact(@Valid @RequestBody Contact contact) {
        contactService.saveContact(contact);
        return new ResponseEntity<>(contact, HttpStatus.CREATED);
    }

}