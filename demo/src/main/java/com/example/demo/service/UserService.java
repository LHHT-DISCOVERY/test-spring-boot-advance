package com.example.demo.service;

import com.example.demo.dto.request.UserCreateRequest;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User createUser(UserCreateRequest request){
        User  user = User.builder().
                username(request.getUsername()).dob(request.getDob()).password(request.getPassword()).
                firstName(request.getFirstName()).lastName(request.getLastName())
                .build();
        return userRepository.save(user);
    }

    public List<User> getListUser(){
        return userRepository.findAll();
    }

}
