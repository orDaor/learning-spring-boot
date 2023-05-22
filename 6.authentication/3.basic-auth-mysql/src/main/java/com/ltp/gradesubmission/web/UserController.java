package com.ltp.gradesubmission.web;

import com.ltp.gradesubmission.entity.UserData;
import com.ltp.gradesubmission.exception.PasswordChangeException;
import com.ltp.gradesubmission.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsManager userDetailsManager;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/login")
    public ResponseEntity<HttpStatus> login() {
        /*we enter here only if login request was successfully authenticated by spring security */
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<HttpStatus> signup(@Valid @RequestBody UserData userData) {
        System.out.println(userData);

        userService.registerUser(userData);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<HttpStatus> logout() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<UserData> getAuthenticatedUser(@AuthenticationPrincipal UserDetails userDetails) {

        UserData userData = userService.getAuthenticatedUser(userDetails);

        return new ResponseEntity<>(userData, HttpStatus.OK);
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<UserData> getUserByUsername(@PathVariable String username) {

        UserData userData = userService.getUserByUsername(username);

        return new ResponseEntity<>(userData, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable String username) {

        userService.deleteUser(username);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(value = "/update")
    public ResponseEntity<HttpStatus> updatePassword(@RequestBody UserData userData) {

        List<String> passwordChange = userData.getPasswordChange();

        if (passwordChange.size() != 2) {
            throw new PasswordChangeException();
        }

        String oldPassword = passwordChange.get(0);
        String newPassword = passwordChange.get(1);

        userService.updateUserPassword(oldPassword, passwordEncoder.encode(newPassword));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody UserData userData) {

        userService.updateUser(userData);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
