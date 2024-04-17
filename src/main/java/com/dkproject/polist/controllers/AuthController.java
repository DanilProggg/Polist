package com.dkproject.polist.controllers;

import com.dkproject.polist.dtos.UserDto;
import com.dkproject.polist.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/reg")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto){
        return userService.createUserService(userDto);
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }
}
