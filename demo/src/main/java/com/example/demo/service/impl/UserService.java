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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    UserRepository userRepository;


    UserMapper userMapper;

    PasswordEncoder passwordEncoder;


    @Override
    @PreAuthorize("hasRole('ADMIN')")
//    only admin author role be able to using getList(), config @EnableMethodSecurity in SecurityConfig class
    public Collection<User> getList() {
        log.info("In method get user");
        return userRepository.findAll();
    }

    @Override
    @PostAuthorize("returnObject.username == authentication.name")
    // only permit get information by id of username being login, not get information by id from other username
    public User findById(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        user.getRoles().forEach(log::info);
        return userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    public UserResponse createEntity(UserCreateRequest usercreateRequest) {

        if (userRepository.existsByUsername(usercreateRequest.getUsername()))
            throw new AppException(ErrorCode.USER_EXIST);
        User user = userMapper.toUser(usercreateRequest);
        user.setPassword(passwordEncoder.encode(usercreateRequest.getPassword()));
        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        user.setRoles(roles);

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


    public Optional<UserResponse> findByKeyword(String username) {
        return Optional.ofNullable(userMapper.toUserResponse(userRepository.findByUsername(username).orElse(null)));
    }

    public User updateEntity(String id, UserUpdateRequest userUpdateRequest) {
        User user = this.findById(id);
        userMapper.updateUser(user, userUpdateRequest);
        return userRepository.save(user);

    }


    public UserResponse getMyInfoByToken() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        return userMapper.toUserResponse(user);
    }
}
