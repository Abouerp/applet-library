package com.abouerp.library.applet.security;

import com.abouerp.library.applet.repository.AdministratorRepository;
import com.abouerp.library.applet.security.handler.LogoutHandler;
import com.abouerp.library.applet.security.token.TokenAuthenticationFilter;
import com.abouerp.library.applet.security.token.TokenPreRequestsFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * 利用jwt token的方式配置security
 *
 * @author Abouerp
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final AdministratorRepository administratorRepository;
    private final RedisTemplate<String, String> redisTemplate;


    public SecurityConfig(AuthenticationSuccessHandler authenticationSuccessHandler,
                          AuthenticationFailureHandler authenticationFailureHandler,
                          LogoutHandler logoutHandler,
                          PasswordEncoder passwordEncoder,
                          @Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService,
                          AdministratorRepository administratorRepository,
                          RedisTemplate<String, String> redisTemplate) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.administratorRepository = administratorRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/api/user/me").permitAll()
                .antMatchers("/api/**").authenticated()
                .and()
                //验证token
                .addFilter(new TokenAuthenticationFilter(administratorRepository, redisTemplate, authenticationManager(), authenticationFailureHandler))
                .addFilter(new TokenPreRequestsFilter(redisTemplate, authenticationManager()))
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }


}
