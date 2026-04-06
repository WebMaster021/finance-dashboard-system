package com.finance.financedashboard.controller;

import com.finance.financedashboard.model.User;
import com.finance.financedashboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

//    Creates a user.
//    Right now anyone create a user irrespective of role.
    @PostMapping
    public User create(@RequestBody User user) {
        return service.create(user);
    }

//    Gets all the existing users.
//    It takes userId to verify role(ADMIN, VIEWER, ANALYST).
    @GetMapping
    public List<User> getAll(@RequestParam UUID userId) {
        return service.getAll(userId);
    }

//    Gets the intended user.
    @GetMapping("/{id}")
    public User get(@PathVariable UUID id) {
        return service.getById(id);
    }
}
