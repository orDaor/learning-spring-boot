package com.ltp.contacts.service;

import com.ltp.contacts.pojo.Contact;

import java.util.List;

public interface ContactService {
    public Contact getContactByIndex(String id);

    public List<Contact> getAllContacts();
}
