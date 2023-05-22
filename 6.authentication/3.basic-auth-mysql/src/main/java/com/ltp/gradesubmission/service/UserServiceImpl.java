package com.ltp.gradesubmission.service;

import com.ltp.gradesubmission.entity.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserDetailsManager userDetailsManager;

    @Override
    public UserData getAuthenticatedUser(UserDetails userDetails) {
        //authorities
        Collection<GrantedAuthority> authoritiesCollection =
                (Collection<GrantedAuthority>)userDetails.getAuthorities();

        List<GrantedAuthority> authorities = new ArrayList<>(authoritiesCollection);

        //collect authenticated user data
        UserData userData
                = UserData.builder()
                .username(userDetails.getUsername())
                .password(userDetails.getPassword())
                .authorities(authorities)
                .build();

        return userData;
    }

    @Override
    public UserData getUserByUsername(String username) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        //authorities
        Collection<GrantedAuthority> authoritiesCollection =
                (Collection<GrantedAuthority>)userDetails.getAuthorities();

        List<GrantedAuthority> authorities = new ArrayList<>(authoritiesCollection);

        //collect user data
        UserData userData
                = UserData.builder()
                .username(userDetails.getUsername())
                .password(userDetails.getPassword())
                .authorities(authorities)
                .build();

        return userData;
    }

    @Override
    public void registerUser(UserData userData) {
        String username = userData.getUsername();
        String password = userData.getPassword();

        UserDetails newUser = org.springframework.security.core.userdetails.User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roles("USER")
                .build();

        userDetailsManager.createUser(newUser);
    }

    @Override
    public void updateUserPassword(String oldPassword, String newPassword) {
        userDetailsManager.changePassword(oldPassword, newPassword);
    }

    @Override
    public void updateUser(UserData userData) {

        List<String> roles = userData.getRoles();

        String password = userData.getPassword();

        UserDetails userDetails = User.builder()
                .username(userData.getUsername())
                .password(passwordEncoder.encode(password))
                .roles(  roles.stream().toArray(String[]::new)  ) //transform list of authorities in list of arguments
                .build();

        //Update the user with the username received, with the received values
        userDetailsManager.updateUser(userDetails);
    }

    @Override
    public void deleteUser(String username) {
        userDetailsManager.deleteUser(username);
    }
}
