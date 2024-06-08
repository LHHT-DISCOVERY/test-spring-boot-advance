package com.example.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.demo.dto.request.RoleRequest;
import com.example.demo.dto.response.RoleResponse;
import com.example.demo.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    //    ignore permissions (notice that: target same the properties name in a Role class)
    //   because Spring not mapping properties "Set<String> permissions" in RoleRequest to " Set<Permission>
    // permissions" in Role class , unlike dataType
    //    -> ignore properties Set<Permission> permissions in Role class -> map this properties by hand
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest roleRequest); // create

    RoleResponse toRoleResponse(Role role);
}
