package com.ltp.contacts.service;

import com.ltp.contacts.pojo.Contact;

import java.util.List;

public interface ContactService {
    public Contact getContactByIndex(String id);

    public List<Contact> getAllContacts();

    public void saveContact(Contact contact);

    public Contact updateContact(String id, Contact contact);

    public void deleteContact(String id);
}
