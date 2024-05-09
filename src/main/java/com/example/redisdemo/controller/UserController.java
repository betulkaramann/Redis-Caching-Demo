package com.example.redisdemo.controller;

import com.example.redisdemo.dto.CreateUserDto;
import com.example.redisdemo.dto.UpdateUserDto;
import com.example.redisdemo.model.User;
import com.example.redisdemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto createUserDto) {
        return ResponseEntity.ok(userService.createUser(createUserDto));
    }
    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }
    @GetMapping("/id")
    public User getUsersById(@RequestParam("id") Long id) {
        return userService.getUsersById(id);
    }
    @PutMapping()
    public ResponseEntity<User> updateUser(@RequestBody UpdateUserDto dto) {
        return new ResponseEntity<>(userService.updateUser(dto), HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteUser(@RequestParam Long id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }
}
