package com.example.demo.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NonNull
    private String name;
    @NonNull
    private String password;
    @NonNull
    private String mobile;
    @NonNull
    private String Email;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
