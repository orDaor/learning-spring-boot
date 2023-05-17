package com.ltp.gradesubmission.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Object id_or_username) {
        super("The user '" + id_or_username + "' does not exist in our records");
    }

}