package com.user.userservice.controller;

import com.user.userservice.model.Users;
import com.user.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class userController {

    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    public ResponseEntity<Object> addUser(@RequestBody Users users)
    {
        return userService.addUser(users);
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<Object> deleteUser(@RequestParam String Email)
    {
        return userService.deleteUser(Email);
    }

    @GetMapping("/viewUser")
    public ResponseEntity<Object> viewUser()
    {
        return userService.getAllUsers();
    }

    @GetMapping("/viewUserByName")
    public ResponseEntity<Object> viewUserByName(@RequestParam String name)
    {
        return userService.findByName(name);
    }

    @PutMapping("/updateUser")
    public ResponseEntity<Object> updateUser(@RequestBody Users users)
    {
        return userService.updateUser(users);
    }

    @PostMapping("/followUser")
    public ResponseEntity<Object> followUser(@RequestParam String following, @RequestParam String LoginEmail)
    {
        return userService.follow(following,LoginEmail);
    }
}
