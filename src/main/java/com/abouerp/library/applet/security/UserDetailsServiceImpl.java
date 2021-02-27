package com.abouerp.library.applet.security;

import com.abouerp.library.applet.domain.Administrator;
import com.abouerp.library.applet.exception.UnauthorizedException;
import com.abouerp.library.applet.mapper.AdministratorMapper;
import com.abouerp.library.applet.repository.AdministratorRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/**
 * @author Abouerp
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AdministratorRepository administratorRepository;
    private final PasswordEncoder passwordEncoder;
    private final HttpServletRequest httpServletRequest;

    public UserDetailsServiceImpl(AdministratorRepository administratorRepository,

                                  PasswordEncoder passwordEncoder,
                                  HttpServletRequest httpServletRequest) {
        this.administratorRepository = administratorRepository;
        this.passwordEncoder = passwordEncoder;
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public UserDetails loadUserByUsername(String s) {
        Administrator administrator = administratorRepository.findByMobile(s).orElse(null);
        if (administrator == null) {
            throw new UnauthorizedException();
        }
        String password = httpServletRequest.getParameter("password");
        if (Boolean.FALSE.equals(passwordEncoder.matches(password, administrator.getPassword()))) {
            throw new UnauthorizedException();
        }
        return AdministratorMapper.INSTANCE.toUserPrincipal(administrator);
    }
}
