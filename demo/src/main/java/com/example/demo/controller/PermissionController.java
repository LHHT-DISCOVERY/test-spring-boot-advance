package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.request.PermissionRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.PermissionResponse;
import com.example.demo.mapper.PermissionMapper;
import com.example.demo.service.impl.PermissionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v1/permissions")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService permissionService;
    PermissionMapper permissionMapper;

    @PostMapping("/create")
    ApiResponse<PermissionResponse> createPermission(@RequestBody PermissionRequest permissionRequest) {
        ApiResponse<PermissionResponse> permissionResponseApiResponse = new ApiResponse<>();
        permissionResponseApiResponse.setResult(permissionService.createEntity(permissionRequest));
        return permissionResponseApiResponse;
    }

    @GetMapping("/list")
    ApiResponse<List<PermissionResponse>> getAllPermission() {
        ApiResponse<List<PermissionResponse>> apiResponse = new ApiResponse<>();
        List<PermissionResponse> permissionResponses = permissionService.getList().stream()
                .map(s -> permissionMapper.toPermissionResponse(s))
                .toList();
        apiResponse.setResult(permissionResponses);
        return apiResponse;
    }

    @PostMapping("/delete/{namePermission}")
    ApiResponse<String> deletePermission(@PathVariable(value = "namePermission") String namePermission) {
        return ApiResponse.<String>builder()
                .result(permissionService.deleteById(namePermission))
                .build();
    }
}
