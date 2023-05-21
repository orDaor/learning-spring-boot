package com.ltp.gradesubmission.security;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

import javax.sql.DataSource;

@Configuration
public class SecurityConfiguration  {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //each request flows through this filters chain (rules chain)
        http
            .csrf().disable()
            .authorizeHttpRequests()
            .antMatchers(HttpMethod.POST, "/user/signup").permitAll()
            .anyRequest().authenticated()
            .and().httpBasic()  //we use basic auth strategy
            .and().logout().logoutUrl("/user/logout");


        return http.build();
    }

    /*when the application starts-up, this bean is registered and will possibly contain the below
    defined users*/
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


    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieMaxAge(3600); //seconds
        return serializer;
    }

}
