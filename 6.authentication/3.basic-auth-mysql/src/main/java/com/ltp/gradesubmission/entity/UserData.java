package com.ltp.gradesubmission.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@ToString(callSuper = true)
public class UserData {
    @Size(min = 4, message = "Username length must be at least 4")
    private String username;
    @Size(min = 4, message = "Password length must be at least ")
    private String password;
    private List<GrantedAuthority> authorities;
    //index = 0 --> old password;  index = 1 new password
    private List<String> passwordChange;
    private List<String> roles;
}
