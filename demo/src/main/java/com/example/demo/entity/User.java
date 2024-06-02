package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String username;
    String password;
    String firstName;
    String lastName;
    LocalDate dob;
//    1 user -> many roles -> many to many
//    Create new table have two columns (1 -> column: pk "id" in entity user , 2 -> column "name" in Entity role)
    @ManyToMany(fetch = FetchType.EAGER)
    Set<Role> roles;

}
