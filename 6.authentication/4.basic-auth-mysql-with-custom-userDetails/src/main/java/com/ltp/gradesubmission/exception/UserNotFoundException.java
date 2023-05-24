package com.ltp.gradesubmission.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super("User <" + username + "> doe not exist!");
    }
}
