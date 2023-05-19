package com.ltp.gradesubmission.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfiguration  {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //each request flows through this filters chain (rules chain)
        http
            .csrf().disable()
            .authorizeHttpRequests()
            .antMatchers(HttpMethod.GET).permitAll()
            .antMatchers(HttpMethod.POST).permitAll()
            .antMatchers(HttpMethod.DELETE).permitAll()
            .anyRequest().authenticated()
            .and().httpBasic();  //we use basic auth strategy

        return http.build();
    }

    /*when the application starts-up, this bean is registered and will possibly contain the below
    defined users:

        - in case users are saved inside this bean, the app will query the DB to save those users inside
        the "users" table --> this table must exist in the database before the app starts-up otherwise
        an error is thrown

        - in case the bean is registered as empty, no query is sent to save any user in the DB. Therefore
        if at application start-up no "users" table exist, no error is thrown*/
    @Bean
    public UserDetailsManager users(DataSource dataSource) {
        //standard user
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("user-psw"))
                .roles("USER")
                .build();

        //admin
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin-psw"))
                .roles("USER", "ADMIN")
                .build();


        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);

        users.createUser(user);
        users.createUser(admin);

        return users;
    }

}
