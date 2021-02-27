//package com.abouerp.library.applet.security.token;
//
//import com.abouerp.library.applet.domain.Administrator;
//import com.abouerp.library.applet.repository.AdministratorRepository;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.DefaultSecurityFilterChain;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//
///**
// * @author Abouerp
// */
//@Configuration
//public class TokenAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
//
//    private final AuthenticationSuccessHandler authenticationSuccessHandler;
//    private final AuthenticationFailureHandler authenticationFailureHandler;
//    private final AdministratorRepository administratorRepository;
//    private final RedisTemplate<String, Administrator> redisTemplate;
//
//    public TokenAuthenticationSecurityConfig(AuthenticationSuccessHandler authenticationSuccessHandler,
//                                             AuthenticationFailureHandler authenticationFailureHandler,
//                                             AdministratorRepository administratorRepository,
//                                             RedisTemplate<String, Administrator> redisTemplate) {
//        this.authenticationSuccessHandler = authenticationSuccessHandler;
//        this.authenticationFailureHandler = authenticationFailureHandler;
//        this.administratorRepository = administratorRepository;
//        this.redisTemplate = redisTemplate;
//    }
//
//    @Override
//    public void configure(HttpSecurity http) {
//        TokenAuthenticationFilter tokenAuthenticationFilter = new TokenAuthenticationFilter(
//                 administratorRepository, redisTemplate);
//        tokenAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
//        tokenAuthenticationFilter.setAllowSessionCreation(false);
//        tokenAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
//        tokenAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
//
//        http.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//        TokenPreRequestsFilter tokenPreRequestsFilter = new TokenPreRequestsFilter(redisTemplate);
//
//        http.addFilterBefore(tokenPreRequestsFilter, UsernamePasswordAuthenticationFilter.class);
//
//    }
//}
