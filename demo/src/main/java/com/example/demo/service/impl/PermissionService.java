package com.example.demo.service.impl;

import java.util.Collection;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Service;

import com.example.demo.dto.request.PermissionRequest;
import com.example.demo.dto.response.PermissionResponse;
import com.example.demo.entity.Permission;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.mapper.PermissionMapper;
import com.example.demo.repository.PermissionRepository;
import com.example.demo.service.IServiceCRUD;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService implements IServiceCRUD<Permission, PermissionRequest, PermissionResponse> {
    PermissionMapper permissionMapper;
    PermissionRepository permissionRepository;

    @Override
    public Collection<Permission> getList() {
        return permissionRepository.findAll();
    }

    @Override
    public Permission findById(String id) {
        return permissionRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
    }

    @Override
    public PermissionResponse createEntity(PermissionRequest permissionRequest) {
        Permission permission = permissionMapper.toPermission(permissionRequest);
        permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    @Override
    public String deleteById(String id) {
        JSONObject jsonObject = new JSONObject();
        try {
            permissionRepository.deleteById(id);
            jsonObject.put("message", "delete successful");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return jsonObject.toString();
    }
}
