package com.ltp.gradesubmission.service;

import com.ltp.gradesubmission.entity.UserAuthority;
import com.ltp.gradesubmission.entity.UserData;
import com.ltp.gradesubmission.exception.PasswordChangeException;
import com.ltp.gradesubmission.repository.UserAuthorityRepository;
import com.ltp.gradesubmission.repository.UserDataRepository;
import com.ltp.gradesubmission.security.UserDetailsImpl;
import com.ltp.gradesubmission.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDataServiceImpl implements UserDataService {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserDataRepository userDataRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserAuthorityRepository userAuthorityRepository;

    @Override
    public UserData getUserByUsername(String username) {

        UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(username);

        return userDetails.getUserData();
    }

    @Override
    public UserData registerUser(UserData userData) {

        //hash password
        String password = userData.getPassword();
        String hashedPassword = passwordEncoder.encode(password);

        //save the user
        userData.setPassword(hashedPassword);
        UserData savedUserData = userDataRepository.save(userData);

        //save the authorities for this user
        UserAuthority userAuthority = new UserAuthority(savedUserData.getUsername(), "USER");
        userAuthorityRepository.save(userAuthority);

        return userData;
    }

    @Override
    public void updateUserPassword(UserDetailsImpl userDetails, List<String> passwordChange) {
        //get authenticated user data
        UserData authenticatedUserData = userDetails.getUserData();
        String currentHashedPassword = authenticatedUserData.getPassword(); //this is hashed!

        String oldPassword = passwordChange.get(0);
        String oldHashedPassword = passwordEncoder.encode(oldPassword);
        String newPassword = passwordChange.get(1);

        //check if old password match
        if (!oldHashedPassword.equals(currentHashedPassword)) {
            throw new PasswordChangeException();
        }

        //set the new password
        authenticatedUserData.setPassword(passwordEncoder.encode(newPassword));

        //update the user in the DB with the new password
        userDataRepository.save(authenticatedUserData);
    }

    @Override
    public UserData updateUser(UserData userData) {

        //update the user data
        UserData updatedUserData = userDataRepository.save(userData);

        /*A new role is added in the authorities table for the specified user, only if
        * the user does not already contain that role*/
        String role = userData.getRole();

        List<UserAuthority> authorities = updatedUserData.getAuthorities();

        List<String> authorityNames = new ArrayList<>();

        for (UserAuthority authority : authorities) {
            authorityNames.add(authority.getAuthority());
        }

        if (!authorityNames.contains(role)) {
            UserAuthority userAuthority = new UserAuthority(updatedUserData.getUsername(), role);
            userAuthorityRepository.save(userAuthority);
        }

        return updatedUserData;
    }

    @Override
    public void deleteUser(String username) {
        userDataRepository.deleteById(username);
    }
}
