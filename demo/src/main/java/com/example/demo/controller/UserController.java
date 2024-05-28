package com.example.demo.controller;

import com.example.demo.dto.request.UserCreateRequest;
import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.impl.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
@RequestMapping("/v1/users")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/create")
    UserResponse createUser(@RequestBody UserCreateRequest usercreateRequest) {
        LOGGER.info("create user request");
        return userService.createEntity(usercreateRequest);
    }

    @GetMapping("/list")
    public Collection<User> getListUser() {
        return userService.getList();
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable("id") String id) {
        return userMapper.toUserResponse(userService.findById(id));
    }

    @PostMapping("/delete")
    public String deleteUserById(@RequestParam String id) {
        return userService.deleteById(id);
    }

    @PostMapping("/update")
    public UserResponse updateUser(@RequestParam String id, @RequestBody UserUpdateRequest userUpdateRequest) {
        String password = userService.updateEntity(id, userUpdateRequest).getPassword();
       return userMapper.toUserResponse(userService.updateEntity(id,userUpdateRequest));
    }
}