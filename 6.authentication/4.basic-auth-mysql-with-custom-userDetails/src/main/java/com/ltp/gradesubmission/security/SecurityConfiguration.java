package com.ltp.gradesubmission.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

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

                //student req authorization
                .antMatchers(HttpMethod.POST, "/student").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/student/*").hasRole("ADMIN")

                //course req authorization
                .antMatchers(HttpMethod.POST, "/course").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/course/*").hasRole("ADMIN")

                //grade req authorization
                .antMatchers(HttpMethod.POST, "/grade/student/*/course/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/grade/student/*/course/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/grade/student/*/course/*").hasRole("ADMIN")

                //user req authorization
                .antMatchers(HttpMethod.POST, "/user/signup").permitAll() //any user should be able to signup, even if it is not authenticated
                .antMatchers(HttpMethod.GET, "/user/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/user/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/user/update").hasRole("ADMIN")

                //any other request needs just authentication
                .anyRequest().authenticated()

                //we use basic auth strategy
                .and().httpBasic()

                //log out is triggered when a request to this special custom URL arrives --> the user session is deleted from the DB
                .and().logout().logoutUrl("/user/logout");


        return http.build();
    }

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieMaxAge(3600); //seconds (this is set the same as the sesison duration)
        return serializer;
    }

}
