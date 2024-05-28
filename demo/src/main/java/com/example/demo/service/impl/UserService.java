package com.example.demo.service.impl;

import com.example.demo.dto.request.UserCreateRequest;
import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.IServiceCRUD;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserService implements IServiceCRUD<User, UserCreateRequest, UserResponse> {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;


    @Override
    public Collection<User> getList() {
        return userRepository.findAll();
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
    }

    public UserResponse createEntity(UserCreateRequest usercreateRequest) {

        if (userRepository.existsByUsername(usercreateRequest.getUsername()))
            throw new RuntimeException("User Exists !");
        User user = userMapper.toUser(usercreateRequest);
        return userMapper.toUserResponse(userRepository.save(user));
    }


    @Override
    public String deleteById(String id) {
        JSONObject jsonObject = new JSONObject();
        try {
            userRepository.deleteById(id);
            jsonObject.put("message", "delete successfully");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return jsonObject.toString();
    }

    @Override
    public Optional<UserResponse> findByKeyword(String username) {
        return Optional.ofNullable(userMapper.toUserResponse(userRepository.findByUsername(username).orElse(null)));
    }

    public User updateEntity(String id, UserUpdateRequest userUpdateRequest) {
        User user = this.findById(id);
        userMapper.updateUser(user, userUpdateRequest);
        return userRepository.save(user);

    }
}
