package com.cas735.finalproject.pointsrv.adapters;

import com.cas735.finalproject.pointsrv.business.UserService;
import com.cas735.finalproject.pointsrv.business.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping(value = "/v1", produces = "application/json")
public class UserController {

    private static final String ENDPOINT = "/user";

    @Autowired
    private UserService userService;

    @GetMapping(ENDPOINT)
    public Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(ENDPOINT + "/{username}")
    public Optional<User> getUserById(@PathVariable String username) {
        return userService.getUserById(username);
    }

    @PostMapping(ENDPOINT)
    public User updateUserPoints(@RequestParam String username, @RequestParam int newPoints) {
        return userService.updateUserPoints(username, newPoints);
    }
}
