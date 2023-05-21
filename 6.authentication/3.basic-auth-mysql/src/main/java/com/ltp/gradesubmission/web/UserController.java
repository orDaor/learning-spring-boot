package com.ltp.gradesubmission.web;

import com.ltp.gradesubmission.dto.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsManager userDetailsManager;

    @PostMapping(value = "/login")
    public ResponseEntity<HttpStatus> login() {
        /*we enter here only if login request was successfully authenticated by spring security */
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<HttpStatus> signup(@RequestBody UserData userData) {
        System.out.println(userData);

        String username = userData.getUsername();
        String password = userData.getPassword();

        UserDetails newUser = User.builder()
                                .username(username)
                                .password(passwordEncoder.encode(password))
                                .roles("USER")
                                .build();

        userDetailsManager.createUser(newUser);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<HttpStatus> logout() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
