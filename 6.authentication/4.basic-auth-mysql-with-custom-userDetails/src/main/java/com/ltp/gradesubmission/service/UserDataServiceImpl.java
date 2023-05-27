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
import java.util.Optional;

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
        UserAuthority userAuthority = new UserAuthority(savedUserData, "ROLE_USER");
        userAuthorityRepository.save(userAuthority);

        return userData;
    }

    @Override
    public void updateUserPassword(UserDetailsImpl userDetails, List<String> passwordChange) {
        //get authenticated user data
        UserData authenticatedUserData = userDetails.getUserData();
        String currentHashedPassword = authenticatedUserData.getPassword(); //this is hashed!

        String oldPassword = passwordChange.get(0);
        String newPassword = passwordChange.get(1);

        //check if old password match with the one currently stored in the DB
        if (!passwordEncoder.matches(oldPassword, currentHashedPassword)) {
            throw new PasswordChangeException();
        }

        //set the new password
        authenticatedUserData.setPassword(passwordEncoder.encode(newPassword));

        //update the user in the DB with the new password
        userDataRepository.save(authenticatedUserData);
    }

    @Override
    public UserData updateUser(UserData userData) {

        //update the user data with received hashed password
        String password = userData.getPassword();
        String hashedPassword = passwordEncoder.encode(password);
        userData.setPassword(hashedPassword);
        UserData updatedUserData = userDataRepository.save(userData);

        //fetch the user authorities data from the database
        Optional<List<UserAuthority>> authoritiesOptional
                = userAuthorityRepository.findAllByUserDataUsername(updatedUserData.getUsername());

        List<UserAuthority> authorities = authoritiesOptional.get();

        updatedUserData.setAuthorities(authorities);

        /*A new role is added in the authorities table for the specified user, only if
        * the user does not already contain that role*/
        String role = userData.getRole();

        List<String> authorityNames = new ArrayList<>();

        for (UserAuthority authority : authorities) {
            authorityNames.add(authority.getAuthority());
        }

        if (!authorityNames.contains(role)) {
            UserAuthority userAuthority = new UserAuthority(updatedUserData, role);
            userAuthorityRepository.save(userAuthority);
            updatedUserData.getAuthorities().add(userAuthority);
        }

        return updatedUserData;
    }

    @Override
    public void deleteUser(String username) {
        userDataRepository.deleteById(username);
    }
}
