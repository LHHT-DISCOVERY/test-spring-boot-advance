package com.example.demo.dto.request;

import com.example.demo.entity.Role;
import com.example.demo.validator.DobConstraint;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String password;
    String firstName;
    String lastName;
    @DobConstraint(min=18, message = "INVALID_DOB")
    LocalDate dob;
    List<String> roles;
}
