package com.cas735.finalproject.pointsrv.business;

import com.cas735.finalproject.pointsrv.business.entities.User;
import com.cas735.finalproject.pointsrv.ports.UserRepository;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String username) {
        return userRepository.findById(username);
    }

    public User updateUserPoints(String username, int newPoints) {
        Optional<User> optionalUser = userRepository.findById(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPoints(newPoints);
            log.info("Updated: " + user);
            return userRepository.save(user);
        } else {
            User newUser = new User(username, newPoints);
            log.info("Inserted: " + newUser);
            return userRepository.save(newUser);
        }
    }
}