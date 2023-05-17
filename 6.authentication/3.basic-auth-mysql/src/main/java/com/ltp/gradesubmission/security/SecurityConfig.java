package com.ltp.gradesubmission.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    //SecurityFilterChain bean is registered and contains security strategy chain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //each request flows through this filters chain (rules chain)
        http
            .csrf().disable() //disable this otherwise by default spring security expects a csrf token because by default csrf protection is enabled
            .authorizeHttpRequests() //here we tell spring we want to use authorization rules
            .antMatchers(HttpMethod.DELETE, "/contact/*/delete").hasRole("ADMIN") //only users with ADMIN role can DELETE with that PATH
            //NOTE: the delete PATH is the same as /contact/{id}/delete in the controller
            .antMatchers(HttpMethod.POST).hasAnyRole("ADMIN", "USER") //only users with ADMIN or USER role can POST
            .antMatchers(HttpMethod.GET).permitAll() //all users can GET (no need for authentication NOR authorization)
            .anyRequest().authenticated() //we want ANY request to be authenticated (except GET, see above)
            //NOTE: only if the request is authenticated we can handle it by activating the corresponding controller handler
            .and().httpBasic()  //we use basic auth strategy
            //NOTE: by default authentication works by session/cookie strategy
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //we tell to not use sessions/cookies

        //NOTE: since we do not want sessions, then we always need to send credentials to be authenticated
        return http.build();
    }

    /*this UserDetailsService bean will contain users records with their credentials
    *   --> users in the bean need to be assigned a role for authorization purposes */
    @Bean
    public UserDetailsService users() {
        //user with admin authorization
        UserDetails admin
                = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin-psw")) //encode() returns hashed password
                    .roles("ADMIN") //sign this user the role of admin
                    .build();

        //user standard
        UserDetails user
                = User.builder()
                .username("user")
                .password(passwordEncoder.encode("user-psw"))
                .roles("USER")
                .build();

        //user details (username and hashed password) are stored in memory in this returned bean
        return new InMemoryUserDetailsManager(admin, user);
    }

    /*@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }*/

}
