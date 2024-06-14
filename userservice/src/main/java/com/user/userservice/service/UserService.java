package com.user.userservice.service;

import com.user.userservice.model.Users;
import com.user.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<Object> addUser(Users users) {
        try {
            userRepository.save(users);
            return ResponseEntity.status(200).body("User saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    public ResponseEntity<Object> updateUser(Users user) {
        try {
            Optional<Users> getUser = userRepository.findByEmail(user.getEmail());
            if (getUser.isPresent()) {
                Users existingUser = getUser.get();
                existingUser.setName(user.getName());
                existingUser.setPassword(user.getPassword());
                existingUser.setMobileNo(user.getMobileNo());
                userRepository.save(existingUser);
                return ResponseEntity.status(200).body("User saved successfully");
            } else {
                return ResponseEntity.status(404).body("User Not Found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    public ResponseEntity<Object> deleteUser(String email) {
        try {
            Optional<Users> getUser = userRepository.findByEmail(email);
            if (getUser.isPresent()) {
                Users deleteUser = getUser.get();
                userRepository.delete(deleteUser);
                return ResponseEntity.status(200).body("User Deleted successfully");
            } else {
                return ResponseEntity.status(404).body("User Not Found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    public ResponseEntity<Object> getAllUsers() {
        return ResponseEntity.status(200).body(userRepository.findAll());
    }

    public ResponseEntity<Object> findByName(String name) {
        try {
            Optional<Users> getUser = userRepository.findByNameContainingIgnoreCase(name);
            if (getUser.isPresent()) {
                Users users = getUser.get();
                return ResponseEntity.status(200).body(users);
            } else {
                return ResponseEntity.status(404).body("User Not Found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    public ResponseEntity<Object> follow(String following, String loggedInEmail) {
        try {
            Optional<Users> logInEmail = userRepository.findByEmail(loggedInEmail);
            if (logInEmail.isPresent()) {
                Users logInUser = logInEmail.get();
                logInUser.setFollowing(Collections.singletonList(following));
                Optional<Users> followingEmail = userRepository.findByEmail(following);
                if (followingEmail.isPresent()) {
                    Users followingUser = followingEmail.get();
                    followingUser.setFollowers(Collections.singletonList(loggedInEmail));
                    userRepository.save(logInUser);
                    userRepository.save(followingUser);
                    return ResponseEntity.status(200).body("Followed Successfully");
                } else {
                    return ResponseEntity.status(404).body("User to be followed Not Found");
                }
            } else {
                return ResponseEntity.status(404).body("Current User Not Found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

}
