package com.example.demo.service.impl;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.request.RoleRequest;
import com.example.demo.dto.response.RoleResponse;
import com.example.demo.entity.Role;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.repository.PermissionRepository;
import com.example.demo.repository.RoleRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    public List<RoleResponse> getList() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }

    public RoleResponse createEntity(RoleRequest roleRequest) {
        Role role = roleMapper.toRole(roleRequest);
        //      find permission name from database
        var permissions = permissionRepository.findAllById(roleRequest.getPermissions()); // return List<Permission>
        //      -> Set again datatype Permission in Role class -> can save Role Entity with data is Permission from DB
        role.setPermissions(new HashSet<>(permissions)); // change to HashSet to set datatype Permission -> save
        role = roleRepository.save(role); // auto map data in table role_permission when save Entity Role
        return roleMapper.toRoleResponse(role);
    }

    public void deleteById(String id) {
        roleRepository.deleteById(id);
    }
}
