package com.ltp.gradesubmission.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "user")
@Data
@RequiredArgsConstructor
@ToString(callSuper = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NonNull
    @Column(name = "username", nullable = false)
    @NotBlank(message = "Name can not be blank")
    private String username;

    @NonNull
    @Column(name = "password", nullable = false)
    @NotBlank(message = "Password ca not be bland")
    private String password;

    public User() { }

}
