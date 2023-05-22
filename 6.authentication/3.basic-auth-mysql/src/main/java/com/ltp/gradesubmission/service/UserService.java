package com.ltp.gradesubmission.service;

import com.ltp.gradesubmission.entity.UserData;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    UserData getAuthenticatedUser(UserDetails userDetails);
    UserData getUserByUsername(String username);
    void registerUser(UserData user);
    void updateUserPassword(String oldPassword, String newPassword);
    void updateUser(UserData user);
    void deleteUser(String username);
}
