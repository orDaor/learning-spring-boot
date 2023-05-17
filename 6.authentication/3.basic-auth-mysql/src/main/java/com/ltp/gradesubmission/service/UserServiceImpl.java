package com.ltp.gradesubmission.service;

import com.ltp.gradesubmission.entity.User;
import com.ltp.gradesubmission.exception.UserNotFoundException;
import com.ltp.gradesubmission.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        //user with this id does not exist
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(id);
        }

        //user was found
        return optionalUser.get();
    }

    @Override
    public User getUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        //not found
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(username);
        }

        //found
        return optionalUser.get();
    }

    @Override
    public List<User> getUsers() {
        List<User> users = (List<User>) userRepository.findAll();
        return users;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
