package com.abouerp.library.applet.security.token;

import com.abouerp.library.applet.domain.Administrator;
import com.abouerp.library.applet.domain.Authority;
import com.abouerp.library.applet.domain.Role;
import com.abouerp.library.applet.exception.UnauthorizedException;
import com.abouerp.library.applet.utils.JsonUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Abouerp
 */
public class TokenPreRequestsFilter extends BasicAuthenticationFilter {

    private final RedisTemplate<String, String> redisTemplate;

    public TokenPreRequestsFilter(RedisTemplate<String, String> redisTemplate,AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        String token = obtainToken(request);

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        token = token.trim();
        Administrator administrator = JsonUtils.readValue(redisTemplate.opsForValue().get(token),Administrator.class);
        if (administrator != null) {
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(administrator.getMobile(), null,
                    administrator.getRoles()
                            .parallelStream()
                            .map(Role::getAuthorities)
                            .flatMap(Set::stream)
                            .map(Authority::springAuthority)
                            .collect(Collectors.toList())));
        }
        super.doFilterInternal(request, response, filterChain);
    }

    @Nullable
    protected String obtainToken(HttpServletRequest request) {
        return request.getHeader("token");
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getRequestURI().contains("/api/");
    }
}
