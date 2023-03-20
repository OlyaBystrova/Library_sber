//package com.example.library_sber.config;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//import javax.servlet.http.*;
//import java.io.IOException;
//import java.util.Set;
//
//@Component
//public class SuccessUserHandler implements AuthenticationSuccessHandler {
//
//    //Авторизация и аутентификация НЕ доделаны
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
//        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
//        if (roles.contains("ROLE_LIBRARIAN")) {
//            httpServletResponse.sendRedirect("/librarian");
//        } else {
//            httpServletResponse.sendRedirect("/client");
//        }
//    }
//}