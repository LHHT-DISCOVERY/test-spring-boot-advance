package com.example.demo.service.impl;

import com.example.demo.dto.request.UserCreateRequest;
import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.User;
import com.example.demo.enums.Role;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.IServiceCRUD;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@Service
//DI bằng constructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService implements IServiceCRUD<User, UserCreateRequest, UserResponse> {

    UserRepository userRepository;


    UserMapper userMapper;

    PasswordEncoder passwordEncoder;


    @Override
    public Collection<User> getList() {
        return userRepository.findAll();
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    public UserResponse createEntity(UserCreateRequest usercreateRequest) {

        if (userRepository.existsByUsername(usercreateRequest.getUsername()))
            throw new AppException(ErrorCode.USER_EXIST);
        User user = userMapper.toUser(usercreateRequest);
        user.setPassword(passwordEncoder.encode(usercreateRequest.getPassword()));
        HashSet<String> role = new HashSet<>();
        role.add(Role.USER.name());
        user.setRoles(role);

        return userMapper.toUserResponse(userRepository.save(user));
    }


    @Override
    public String deleteById(String id) {
        JSONObject jsonObject = new JSONObject();
        try {
            userRepository.deleteById(id);
            jsonObject.put("message", "delete successfully");
        } catch (JSONException e) {
            throw new AppException(ErrorCode.JSON_EXCEPTION);
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
