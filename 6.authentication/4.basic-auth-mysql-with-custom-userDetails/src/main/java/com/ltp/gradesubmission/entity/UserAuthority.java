package com.ltp.gradesubmission.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "user_authority")
@RequiredArgsConstructor
@Data
public class UserAuthority implements GrantedAuthority{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    @JsonIgnore
    private UserData userData;

    @NonNull
    @Column(name = "authority", nullable = false)
    private String authority;

    public UserAuthority() { }

}
