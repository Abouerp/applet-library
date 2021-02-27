package com.abouerp.library.applet.security.token;


import com.abouerp.library.applet.domain.Administrator;
import com.abouerp.library.applet.repository.AdministratorRepository;
import com.abouerp.library.applet.security.UserPrincipal;
import com.abouerp.library.applet.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author Abouerp
 */
@Slf4j
public class TokenAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "mobile";
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";
    private final AdministratorRepository administratorRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private String mobileParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
    private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;
    private boolean postOnly = true;
    private final AuthenticationManager authenticationManager;


    public TokenAuthenticationFilter(AdministratorRepository administratorRepository,
                                     RedisTemplate<String,String> redisTemplate,
                                     AuthenticationManager authenticationManager) {
        super.setFilterProcessesUrl("/api/user/login");
        this.authenticationManager = authenticationManager;
        this.administratorRepository = administratorRepository;
        this.redisTemplate =redisTemplate;
    }

    //身份认证
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        String mobile = obtainMobile(request);
        String password = obtainPassword(request);
        if (mobile == null) {
            mobile = "";
        }
        if (password == null) {
            password = "";
        }

        mobile = mobile.trim();

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(mobile, password,new ArrayList<>());
        setDetails(request, authRequest);

        return authenticationManager.authenticate(authRequest);
    }

    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    @Nullable
    private String obtainMobile(HttpServletRequest request) {
        return request.getParameter(mobileParameter);
    }

    @Nullable
    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(passwordParameter);
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        UserPrincipal userPrincipal = (UserPrincipal)authResult.getPrincipal();
        Administrator administrator = administratorRepository.findByUsername(userPrincipal.getUsername()).orElse(null);

        String accessToken = RandomStringUtils.randomAlphanumeric(100);
        //key:token    value:用户对象   三小时有效
        log.info(accessToken);
        redisTemplate.opsForValue().set(accessToken, JsonUtils.writeValueAsString(administrator), 3L, TimeUnit.HOURS);

        log.info("admin = {}",redisTemplate.opsForValue().get(accessToken));
        response.setHeader("token",accessToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        response.getWriter().write("authentication failed, reason: " + failed.getMessage());
    }
}
