package com.ltp.gradesubmission.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    /*when the application starts-up, this bean is registered and will be possible to use it inside the services
    * for querying the "users" table in the DB
    *
    * NOTE --> both users and authorities table mustbe created "by hand" before the application runs*/
    @Bean
    public UserDetailsManager users(DataSource dataSource) {

        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);

        //save these users in the database when application starts up, if they do no already exists

        if (!users.userExists("admin")) {
            //create and save user with username = "user"
            UserDetails admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin-psw"))
                    .roles("USER", "ADMIN")
                    .build();

            users.createUser(admin);
        }

        if (!users.userExists("user")) {
            //create and save user with username = "user"
            UserDetails user = User.builder()
                    .username("user")
                    .password(passwordEncoder.encode("user-psw"))
                    .roles("USER")
                    .build();

            users.createUser(user);
        }

        return users;
    }


    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieMaxAge(3600); //seconds (this is set the same as the sesison duration)
        return serializer;
    }

}
