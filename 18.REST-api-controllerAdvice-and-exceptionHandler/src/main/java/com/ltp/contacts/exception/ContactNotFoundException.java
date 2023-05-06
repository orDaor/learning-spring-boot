package com.ltp.contacts.exception;

public class ContactNotFoundException extends RuntimeException{
    //constructor gets called when exception is thrown
    public ContactNotFoundException(String id) {
        //passing an error message into the parent constructor allows us to access it later...
        super("The id '" + id + "' does not exist in our records");
    }
}
