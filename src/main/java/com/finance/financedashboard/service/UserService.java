package com.finance.financedashboard.service;

import com.finance.financedashboard.exception.ResourceNotFoundException;
import com.finance.financedashboard.model.User;
import com.finance.financedashboard.model.enums.Role;
import com.finance.financedashboard.model.enums.UserStatus;
import com.finance.financedashboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private void checkAccess(User user, Role... allowedRoles) {

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new ResourceNotFoundException("User is inactive");
        }

        for (Role role : allowedRoles) {
            if (user.getRole() == role) return;
        }

        throw new ResourceNotFoundException("Access Denied");
    }

    public User create(User user){
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ResourceNotFoundException("Email already exists");
        }

        if (user.getRole() == null) user.setRole(Role.VIEWER);
        if (user.getStatus() == null) user.setStatus(UserStatus.ACTIVE);
        return userRepository.save(user);
    }

    public List<User> getAll(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        checkAccess(user, Role.ADMIN, Role.ANALYST);
        return userRepository.findAll();
    }

    public User getById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        checkAccess(user, Role.ADMIN, Role.ANALYST);
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
