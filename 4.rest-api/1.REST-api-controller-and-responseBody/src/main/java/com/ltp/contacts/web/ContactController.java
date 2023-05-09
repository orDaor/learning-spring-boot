package com.ltp.contacts.web;

import com.ltp.contacts.pojo.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ltp.contacts.service.ContactService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
/*
* NOTE: @ResponseBody  --> we can apply @Response body at a Class level here, and avoid applying it to method handlers
*                           like we did below to getContact
* */
public class ContactController {

    /* we can inject here the bean ContactServiceImpl because it is also of the type ContactService
    * because it implements it*/
    @Autowired
    private ContactService contactService;

    /* @ResponseBody --> serializes the object returned by the handler method into JSON*/
    @GetMapping("/contact/{id}")
    @ResponseBody
    public Contact getContact(@PathVariable String id) {
        Contact contact = contactService.getContactByIndex(id);
        System.out.println(contact);
        return contact;
    }

}
