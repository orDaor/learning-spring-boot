package com.ltp.contacts.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ltp.contacts.pojo.Contact;

@Repository
public class ContactRepository {
    
    private List<Contact> contacts = new ArrayList<>();

    public List<Contact> getContacts() {
        return contacts;
    }

    public Contact getContact(int index) {
        return contacts.get(index);
    }

    public void saveContact(Contact contact) {
        contacts.add(contact);
    }

    public Contact updateContact(int index, Contact contact) {
        Contact updatedContact = getContact(index);
        updatedContact.setName(contact.getName());
        updatedContact.setPhoneNumber(contact.getPhoneNumber());
        return updatedContact;
    }
    
    public void deleteContact(int index) {
        contacts.remove(index);
    }

}
