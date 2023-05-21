package com.ltp.gradesubmission.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class UserData {
    private String username;
    private String password;
    private String firstname;
    private String surname;
    private Integer age;
}
