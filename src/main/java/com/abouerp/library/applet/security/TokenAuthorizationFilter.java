package com.abouerp.library.applet.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * @author Abouerp
 */
public class TokenAuthorizationFilter extends BasicAuthenticationFilter {

    public TokenAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }
}
