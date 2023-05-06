package com.ltp.contacts.service;

import java.util.List;
import java.util.stream.IntStream;

import com.ltp.contacts.pojo.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ltp.contacts.repository.ContactRepository;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;


    private int findIndexById(String id) {
        return IntStream.range(0, contactRepository.getContacts().size())
            .filter(index -> contactRepository.getContacts().get(index).getId().equals(id))
            .findFirst()
            .orElseThrow();
    }

    @Override
    public Contact getContactByIndex(String id) {
        int index = findIndexById(id);
        return contactRepository.getContact(index);
    }

    @Override
    public List<Contact> getAllContacts() {
        return  contactRepository.getContacts();
    }
}
