package com.example.demo.controller;

import com.example.demo.dto.request.UserCreateRequest;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;
import java.util.List;


@RestController
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);


    @Autowired
    UserService userService;

    @PostMapping("/users")
    User createUser(@RequestBody UserCreateRequest userCreateRequest) {
        LOGGER.info("create user request");
        return userService.createUser(userCreateRequest);
    }

    @GetMapping("/users/list")
    public List<User> getListUser(){
        return userService.getListUser();
    }
}