package com.ltp.gradesubmission.security;

import com.ltp.gradesubmission.entity.UserData;
import com.ltp.gradesubmission.exception.UserNotFoundException;
import com.ltp.gradesubmission.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDataRepository userDataRepository;

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UserNotFoundException {

        Optional<UserData> userDataOptional = userDataRepository.findById(username);

        //user doe not exists
        if (userDataOptional.isEmpty()) {
            throw new UserNotFoundException(username);
        }

        //user exists
        UserData userData = userDataOptional.get();

        return new UserDetailsImpl(userData);
    }
}
