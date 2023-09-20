package com.example.library.controllers;

import com.example.library.dto.request.UserRequest;
import com.example.library.model.User;
import com.example.library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity getUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.userList());
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);

        if (user != null) {
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Not Found");
        }
    }

    @PostMapping("")
    public ResponseEntity addUser(@RequestBody UserRequest request) {
        User newUser = userService.addUser(request);
        if (newUser != null) {
            return ResponseEntity.status(HttpStatus.OK).body( "User Added Successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to Add User.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateUser(@PathVariable long id, @RequestBody UserRequest request) {
        if (userService.updateUser(id, request)) {
            return ResponseEntity.status(HttpStatus.OK).body("User Updated Successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to Update User.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable long id) {
        if (userService.deleteUser(id)) {
            return ResponseEntity.status(HttpStatus.OK).body( "User Deleted Successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to Delete User.");
        }
    }
}
