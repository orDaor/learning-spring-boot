package com.ltp.gradesubmission.service;

import com.ltp.gradesubmission.entity.User;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface UserService {
    User getUserById(Long id);
    User getUserByUsername(String username);
    List<User> getUsers();
    User saveUser(User user);
    void deleteUser(Long id);
}
