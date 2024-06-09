package com.example.demo.service.impl;

import com.example.demo.dto.request.UserCreateRequest;
import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.User;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.IServiceCRUD;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@Service
// DI bằng constructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService implements IServiceCRUD<User, UserCreateRequest, UserResponse> {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    UserRepository userRepository;

    UserMapper userMapper;

    // DI in @Bean in SecurityConfig class
    PasswordEncoder passwordEncoder;

    RoleRepository roleRepository;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    // spring security default mapping with format "ROLE_"; this EX: ROLE_ADMIN // see author line 46-49 UserController
    // Class
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
        //        user.getRoles().forEach(log::info);
        return userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    //    @PreAuthorize("hasAuthority('POST_DATA')")
    // ex: author permission for method //using hasAuthority will get all content of author in contextHolder of Spring
    // security
    public UserResponse createEntity(UserCreateRequest usercreateRequest) {

//        if (userRepository.existsByUsername(usercreateRequest.getUsername()))
//            throw new AppException(ErrorCode.USER_EXIST); // -> because we already unique in User Entity -> no need check user exist in db
        User user = userMapper.toUser(usercreateRequest);
        var roles = roleRepository.findAllById(usercreateRequest.getRoles());
        user.setPassword(passwordEncoder.encode(usercreateRequest.getPassword()));
        user.setRoles(new HashSet<>(roles));
        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.USER_EXIST); // instead of check user exist as above , we using try catch exception, give this check task for dbms
        }
        return userMapper.toUserResponse(user);
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
        return Optional.ofNullable(userMapper.toUserResponse(
                userRepository.findByUsername(username).orElse(null)));
    }

    public UserResponse updateEntity(String id, UserUpdateRequest userUpdateRequest) {
        User user = this.findById(id);
        userMapper.updateUser(user, userUpdateRequest); // implement first when set
        var roles = roleRepository.findAllById(userUpdateRequest.getRoles());
        user.setPassword(passwordEncoder.encode(userUpdateRequest.getPassword()));
        user.setRoles(new HashSet<>(roles));
        user = userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    public UserResponse getMyInfoByToken() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        return userMapper.toUserResponse(user);
    }
}
