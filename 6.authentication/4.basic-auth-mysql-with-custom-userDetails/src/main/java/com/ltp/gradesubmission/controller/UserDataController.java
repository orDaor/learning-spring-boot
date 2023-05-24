package com.ltp.gradesubmission.controller;

import com.ltp.gradesubmission.entity.UserData;
import com.ltp.gradesubmission.exception.PasswordChangeException;
import com.ltp.gradesubmission.security.UserDetailsImpl;
import com.ltp.gradesubmission.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserDataController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsManager userDetailsManager;

    @Autowired
    private UserDataService userDataService;

    @PostMapping(value = "/login")
    public ResponseEntity<HttpStatus> login() {
        /*we enter here only if login request was successfully authenticated by spring security */
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<UserData> signup(@Valid @RequestBody UserData userData) {
        System.out.println(userData);

         userDataService.registerUser(userData);

        return new ResponseEntity<>(userData, HttpStatus.OK);
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<HttpStatus> logout() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<UserData> getAuthenticatedUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        UserData userData = userDetails.getUserData();

        return new ResponseEntity<>(userData, HttpStatus.OK);
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<UserData> getUserByUsername(@PathVariable String username) {

        UserData userData = userDataService.getUserByUsername(username);

        return new ResponseEntity<>(userData, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable String username) {

        userDataService.deleteUser(username);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(value = "/update")
    public ResponseEntity<HttpStatus> updatePassword(
            @RequestBody UserData userData,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        //check if received password data is good
        List<String> passwordChange = userData.getPasswordChange();

        if (passwordChange.size() != 2) {
            throw new PasswordChangeException();
        }

        //update the password
        userDataService.updateUserPassword(userDetails, passwordChange);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<UserData> updateUser(@RequestBody UserData userData) {

        UserData updatedUserData = userDataService.updateUser(userData);

        return new ResponseEntity<>(updatedUserData, HttpStatus.OK);
    }

}
