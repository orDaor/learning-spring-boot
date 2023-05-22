package com.ltp.gradesubmission.exception;

public class PasswordChangeException extends RuntimeException{
    public PasswordChangeException() {
        super("The passwordChange list must contain both old password and new one");
    }
}
