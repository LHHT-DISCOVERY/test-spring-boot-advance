package com.example.demo.controller;

import com.example.demo.dto.request.UserCreateRequest;
import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.impl.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
@RequestMapping("/v1/users")
//DI bằng constructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    UserService userService;


    UserMapper userMapper;

    @PostMapping("/create")
    ApiResponse<UserResponse> createUser(@Valid @RequestBody UserCreateRequest usercreateRequest) {
        LOGGER.info("create user request");
        ApiResponse<UserResponse> userApiResponse = new ApiResponse<>();
        userApiResponse.setResult(userService.createEntity(usercreateRequest));
        return userApiResponse;
    }

    @GetMapping("/list")
    public ApiResponse<Collection<User>> getListUser() {
        ApiResponse<Collection<User>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.getList());
        return apiResponse;
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable("id") String id) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userMapper.toUserResponse(userService.findById(id)));
        return apiResponse;
    }

    @PostMapping("/delete")
    public ApiResponse<String> deleteUserById(@RequestParam String id) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.deleteById(id));
        return apiResponse;

    }

    @PostMapping("/update")
    public ApiResponse<UserResponse> updateUser(@RequestParam String id, @RequestBody UserUpdateRequest userUpdateRequest) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userMapper.toUserResponse(userService.updateEntity(id, userUpdateRequest)));
        return apiResponse;
    }
}