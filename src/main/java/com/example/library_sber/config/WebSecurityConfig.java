//package com.example.library_sber.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//    final UserDetailsService userDetailsService;
//    final SuccessUserHandler successUserHandler;
//
//    @Autowired
//    public WebSecurityConfig(UserDetailsService userDetailsService, SuccessUserHandler successUserHandler) {
//
//        this.userDetailsService = userDetailsService;
//        this.successUserHandler = successUserHandler;
//    }
//
//    @Override
//    @Autowired
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService);
//    }
//
//    //Авторизация и аутентификация НЕ доделаны
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/librarian/**").hasRole("LIBRARIAN")
//                .anyRequest().hasAnyRole("CLIENT", "LIBRARIAN");
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .loginProcessingUrl("/login")
//                .successHandler(successUserHandler)
//                .permitAll()
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login")
//                .permitAll();
//    }
//}
