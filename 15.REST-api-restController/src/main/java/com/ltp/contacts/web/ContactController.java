package com.ltp.contacts.web;

import com.ltp.contacts.pojo.Contact;
import org.springframework.beans.factory.annotation.Autowired;

import com.ltp.contacts.service.ContactService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {

    /* we can inject here the bean ContactServiceImpl because it is also of the type ContactService
    * because it implements it*/
    @Autowired
    private ContactService contactService;

    @GetMapping("/contact/{id}")
    public Contact getContact(@PathVariable String id) {
        Contact contact = contactService.getContactByIndex(id);
        System.out.println(contact);
        return contact;
    }

}
