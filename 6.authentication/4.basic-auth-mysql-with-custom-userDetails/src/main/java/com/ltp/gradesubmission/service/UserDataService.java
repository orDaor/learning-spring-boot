package com.ltp.gradesubmission.service;

import com.ltp.gradesubmission.entity.UserData;
import com.ltp.gradesubmission.security.UserDetailsImpl;

import java.util.List;

public interface UserDataService {
    UserData getUserByUsername(String username);
    UserData registerUser(UserData user);
    void updateUserPassword(UserDetailsImpl userDetails, List<String> passwordChange);
    UserData updateUser(UserData user);
    void deleteUser(String username);
}
